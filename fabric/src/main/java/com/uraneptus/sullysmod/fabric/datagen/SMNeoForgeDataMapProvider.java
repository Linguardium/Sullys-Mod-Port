package com.uraneptus.sullysmod.fabric.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

public class SMNeoForgeDataMapProvider extends NeoForgeDataMapProvider {

    public SMNeoForgeDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(DataMapEntries entries, HolderLookup.Provider registries) {
        addAll(MOD_ID, entries.getOrThrow(OXIDIZABLES_DATAMAP), BlockBlockMap2HolderHolderMap(WeatheringCopper.NEXT_BY_BLOCK.get()));
        addAll(MOD_ID, entries.getOrThrow(WAXABLES_DATAMAP), BlockBlockMap2HolderHolderMap(HoneycombItem.WAXABLES.get()));
    }

    private <T> void addAll(String namespace, DataMapEntryConsumer<T> consumer, Map<Holder.Reference<T>, Holder.Reference<T>> map) {
        for (Map.Entry<Holder.Reference<T>, Holder.Reference<T>> entry : map.entrySet()) {
            ResourceLocation fromId = entry.getKey().key().location();
            if (! fromId.getNamespace().equals(namespace)) continue;
            consumer.addValueEntry(entry.getKey().key().location().toString(), entry.getValue().key().location().toString());
        }
    }

    private static Map<Holder.Reference<Block>, Holder.Reference<Block>> BlockBlockMap2HolderHolderMap(Map<Block, Block> map) {
        Map<Holder.Reference<Block>,Holder.Reference<Block>> holderMap = Maps.newHashMap();
        map.forEach((fromBlock, toBlock) -> {
            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(fromBlock);
            Holder.Reference<Block> keyRef = BuiltInRegistries.BLOCK.get(blockId).orElse(null);
            blockId = BuiltInRegistries.BLOCK.getKey(toBlock);
            Holder.Reference<Block> toRef = BuiltInRegistries.BLOCK.get(blockId).orElse(null);
            if (keyRef != null && toRef != null) holderMap.put(keyRef, toRef);
        });
        return holderMap;
    }
}
