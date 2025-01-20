package com.uraneptus.sullysmod.core.other;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Optional;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

public class SMLocationUtil {
    public static ResourceLocation location(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }
    public static <T> ResourceKey<T> key(ResourceKey<Registry<T>> registry, String id) {
        return ResourceKey.create(registry,location(id));
    }
    public static ResourceKey<LootTable> lootTableFromBlockKey(ResourceKey<? extends Block> key) {
        String path = key.location().withPrefix("block/").getPath();
        return key(Registries.LOOT_TABLE, path);
    }
    public static Optional<ResourceKey<LootTable>> optionalBlockLoot(RegistrySupplier<? extends Block> block) {
        String path = block.getKey().location().withPrefix("block/").getPath();
        return Optional.of(key(Registries.LOOT_TABLE, path));
    }

}
