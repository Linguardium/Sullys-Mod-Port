package com.uraneptus.sullysmod.core.other.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class SMLootUtil {
    public static LootParams buildEntityLootParams(ServerLevel level, Entity thisEntity) {
        return new LootParams.Builder(level)
            .withParameter(LootContextParams.DAMAGE_SOURCE, level.damageSources().cramming())
            .withParameter(LootContextParams.THIS_ENTITY, thisEntity)
            .withParameter(LootContextParams.ORIGIN, thisEntity.position())
            .create(LootContextParamSets.ENTITY);
    }
    public static ResourceKey<LootTable> createLootTableKey(ResourceLocation id) {
        return ResourceKey.create(Registries.LOOT_TABLE, id);
    }
}
