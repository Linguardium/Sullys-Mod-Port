package com.uraneptus.sullysmod.core.other.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.uraneptus.sullysmod.core.SMFeatures;
import com.uraneptus.sullysmod.core.registry.SMLootItemConditions;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMFeatureLootItemCondition implements LootItemCondition {
    public static final MapCodec<SMFeatureLootItemCondition> CODEC = RecordCodecBuilder.mapCodec(instance->instance.group(
            StringRepresentable.fromEnum(SMFeatures::values).listOf().fieldOf("values").forGetter(condition->condition.condition)
    ).apply(instance, SMFeatureLootItemCondition::new));

    private final List<SMFeatures> condition;

    public SMFeatureLootItemCondition(List<SMFeatures> condition) {
        this.condition = condition;
    }

    public static LootItemCondition.Builder modFeatureCondition(List<SMFeatures> features) {
        return () -> new SMFeatureLootItemCondition(features);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return SMLootItemConditions.MOD_FEATURE_CONDITION_TYPE.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return condition.stream().allMatch(SMFeatures::isEnabled);
    }

}
