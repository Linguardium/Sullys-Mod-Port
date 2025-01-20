package com.uraneptus.sullysmod.core.other;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMFeatureDefinitions {
    //CONFIGURED
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_JADE_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, location("jade_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_PETRIFIED_TREE_SMALL = ResourceKey.create(Registries.CONFIGURED_FEATURE, location("petrified_tree_small"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_PETRIFIED_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, location("petrified_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ARTIFACT_GRAVEL = ResourceKey.create(Registries.CONFIGURED_FEATURE, location("artifact_gravel"));

    //PLACED
    public static final ResourceKey<PlacedFeature> PLACED_JADE_ORE = ResourceKey.create(Registries.PLACED_FEATURE, location("jade_ore"));
    public static final ResourceKey<PlacedFeature> PLACED_PETRIFIED_TREE_SMALL = ResourceKey.create(Registries.PLACED_FEATURE, location("petrified_tree_small"));
    public static final ResourceKey<PlacedFeature> PLACED_PETRIFIED_TREE = ResourceKey.create(Registries.PLACED_FEATURE, location("petrified_tree"));
    public static final ResourceKey<PlacedFeature> PLACED_ARTIFACT_GRAVEL = ResourceKey.create(Registries.PLACED_FEATURE, location("artifact_gravel"));
}
