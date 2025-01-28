package com.uraneptus.sullysmod.fabric.datagen;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.apache.commons.compress.utils.Lists;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.PackOutput.Target.DATA_PACK;

public abstract class NeoForgeDataMapProvider implements DataProvider {
    protected final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;
    private final PackOutput.PathProvider pathResolver;

    public static ResourceKey<Block> OXIDIZABLES_DATAMAP = getNeoForgeDataMapId(Registries.BLOCK, "oxidizables");
    public static ResourceKey<Block> WAXABLES_DATAMAP = getNeoForgeDataMapId(Registries.BLOCK, "waxables");


    public static <T> ResourceKey<T> getNeoForgeDataMapId(ResourceKey<Registry<T>> registry, String dataMap) {
        return ResourceKey.create(registry, ResourceLocation.fromNamespaceAndPath("neoforge",dataMap));
    }
    public NeoForgeDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.output = output;
        this.registriesFuture = registriesFuture;
        this.pathResolver = output.createPathProvider(DATA_PACK, "data_maps");
    }

    protected abstract void configure(DataMapEntries entries, HolderLookup.Provider registries);



    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        return this.registriesFuture.thenCompose(lookup -> {
            Map<ResourceKey<?>, DataMap> maps = new HashMap<>();
            RegistryOps<JsonElement> ops = lookup.createSerializationContext(JsonOps.INSTANCE);
            DataMapEntries entries = new DataMapEntries() {
                private DataMap getDataMap(ResourceKey<?> datamap, String fieldname) {
                    DataMap map = maps.computeIfAbsent(datamap, key -> new DataMap(false, fieldname, Maps.newHashMap(), Lists.newArrayList()));
                    if (! map.fieldName.equals(fieldname))
                        throw new UnsupportedOperationException("DataMapProvider does not support multiple fields. Expected: " + map.fieldName + ". Found: " + fieldname);
                    return map;
                }

                @Override
                public <T> DataMapEntryConsumer<T> getOrThrow(ResourceKey<T> dataMapKey) {
                    DataMap map = maps.get(dataMapKey);
                    if (map == null) throw new RuntimeException("DataMap " + dataMapKey + " not found");
                    return createConsumer(dataMapKey, map);
                }

                @Override
                public <T> DataMapEntryConsumer<T> getOrCreate(ResourceKey<T> dataMapKey, String fieldName) {
                    DataMap dataMap = getDataMap(dataMapKey, fieldName);
                    return createConsumer(dataMapKey, dataMap);
                }
                public <T> DataMapEntryConsumer<T> createConsumer(ResourceKey<T> dataMapKey, DataMap dataMapLookup) {

                    return new DataMapEntryConsumer<T>(dataMapLookup, lookup.lookupOrThrow(dataMapKey.registryKey())) {

                        @Override
                        public DataMapEntryConsumer<T> addValueEntry(T entryId, String data) {
                            return addValueEntry(lookupEntryId(entryId), data);
                        }

                        @Override
                        public DataMapEntryConsumer<T> addValueEntry(TagKey<T> entryId, String data) {
                            return addValueEntry("#" + entryId.location(), data);
                        }

                        @Override
                        public DataMapEntryConsumer<T> addValueEntry(String entryId, String data) {
                            this.dataMap.values.put(entryId, data);
                            return this;
                        }

                        @Override
                        public DataMapEntryConsumer<T> addRemoveEntry(T entryId) {
                            return addRemoveEntry(lookupEntryId(entryId));
                        }

                        @Override
                        public DataMapEntryConsumer<T> addRemoveEntry(TagKey<T> entryId) {
                            return addRemoveEntry("#" + entryId.location());
                        }

                        @Override
                        public DataMapEntryConsumer<T> addRemoveEntry(String entryId) {
                            this.dataMap.remove.add(entryId);
                            return this;
                        }



                        @Override
                        public DataMapEntryConsumer<T> setReplace(boolean replace) {
                            this.dataMap.replace = replace;
                            return this;
                        }



                        private String lookupEntryId(T value) {
                            ResourceLocation id = lookup.listElements().filter(h->h.value().equals(value)).findFirst().map(h->h.key().location()).orElse(null);
                            if (id == null)
                                throw new IllegalArgumentException("entry not found in registry: " + value.toString());
                            return id.toString();
                        }
                    };
                }
            };
            entries.getOrCreate(OXIDIZABLES_DATAMAP, "next_oxidized_stage");
            entries.getOrCreate(WAXABLES_DATAMAP, "waxed");

            this.configure(entries, lookup);
            return this.write(writer, maps);
        });
    }

    private CompletableFuture<?> write(CachedOutput writer, Map<ResourceKey<?>, DataMap> entries) {
        return CompletableFuture.allOf(entries.entrySet().stream().map(entry -> {
            Path path = this.pathResolver.json(locationFromKey(entry.getKey()));
            DataMap map = entry.getValue();
            if (map.remove.isEmpty() && map.values.isEmpty() && !map.replace) return CompletableFuture.completedFuture((Void)null);
            Encoder<DataMap> encoder = getEncoder(map);
            JsonElement json = encoder.encodeStart(JsonOps.INSTANCE, map).getOrThrow();
            return DataProvider.saveStable(writer, json, path);
        }).toArray(CompletableFuture[]::new));
    }

    private ResourceLocation locationFromKey(ResourceKey<?> key) {
        String pathString = key.registry().getPath();
        if (!key.registry().getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE)) {
            pathString = key.registry().getNamespace() + "/" + pathString;
        }
        return key.location().withPrefix(pathString);
    }

    @Override
    public String getName() {
        return "NeoForge DataMaps";
    }

    protected static class DataMap {
        boolean replace;
        String fieldName;
        HashMap<String, String> values;
        ArrayList<String> remove;

        DataMap(boolean replace, String fieldName, HashMap<String, String> values, ArrayList<String> remove) {
            this.replace = replace;
            this.fieldName = fieldName;
            this.values = values;
            this.remove = remove;
        }
    }
    protected interface DataMapEntries {
        <T> DataMapEntryConsumer<T> getOrCreate(ResourceKey<T> dataMap, String fieldName);
        <T> DataMapEntryConsumer<T> getOrThrow(ResourceKey<T> dataMapKey);
    }

    protected abstract class DataMapEntryConsumer<T> {
        protected DataMap dataMap;
        protected HolderLookup.RegistryLookup<T> lookup;
        DataMapEntryConsumer(DataMap map, HolderLookup.RegistryLookup<T> lookup) {
            this.dataMap = map;
            this.lookup = lookup;
        }
        abstract DataMapEntryConsumer<T> addValueEntry(T entryId, String data);
        abstract DataMapEntryConsumer<T> addValueEntry(TagKey<T> entryId,String data);
        abstract DataMapEntryConsumer<T> addValueEntry(String entryId, String data);

        abstract DataMapEntryConsumer<T> addRemoveEntry(T entryId);
        abstract DataMapEntryConsumer<T> addRemoveEntry(TagKey<T> entryId);
        abstract DataMapEntryConsumer<T> addRemoveEntry(String entryId);
        abstract DataMapEntryConsumer<T> setReplace(boolean replace);
    }
    private static Codec<Map<String, String>> STRINGMAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING);
    private static Codec<Map<String, Map<String, String>>> MAPSTRINGMAP_CODEC = Codec.unboundedMap(Codec.STRING,Codec.unboundedMap(Codec.STRING, Codec.STRING));

    private Encoder<DataMap> getEncoder(DataMap map) {
        return RecordCodecBuilder.<DataMap>mapCodec(instance->instance.group(
                Codec.BOOL.fieldOf("replace").forGetter(m->m.replace),
                MAPSTRINGMAP_CODEC
                        .xmap(
                            objectMap->Maps.transformValues(objectMap,m->m.values().stream().findAny().get()),
                            valueMap->Maps.transformValues(valueMap, str->Map.of(map.fieldName,str))
                        ).fieldOf("values")
                        .forGetter(m->m.values),
                Codec.STRING.listOf().optionalFieldOf("remove",List.of()).forGetter(m->m.remove)
        ).apply(instance,(a,b,c)->new DataMap(a,map.fieldName,Maps.newHashMap(b),new ArrayList<>(c)))).encoder();
    }
}
