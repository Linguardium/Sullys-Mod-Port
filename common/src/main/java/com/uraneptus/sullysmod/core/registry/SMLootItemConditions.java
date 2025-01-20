package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.core.other.loot.SMFeatureLootItemCondition;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.LOOT_CONDITION_TYPES;

public class SMLootItemConditions {
    public static final RegistrySupplier<LootItemConditionType> MOD_FEATURE_CONDITION_TYPE = LOOT_CONDITION_TYPES.register(location("mod_feature_flag"), () -> new LootItemConditionType(SMFeatureLootItemCondition.CODEC));

    public static void init() { }
}
