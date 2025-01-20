package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.levelgen.AmberBlobFeature;
import com.uraneptus.sullysmod.common.levelgen.ArtifactGravelFeature;
import com.uraneptus.sullysmod.common.levelgen.PetrifiedTreeFeature;
import com.uraneptus.sullysmod.common.levelgen.configs.PetrifiedTreeConfig;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.FEATURES;


public class SMFeatures {

    public static final RegistrySupplier<Feature<PetrifiedTreeConfig>> PETRIFIED_TREE = FEATURES.register(location("petrified_tree"), () -> new PetrifiedTreeFeature(PetrifiedTreeConfig.CODEC));
    public static final RegistrySupplier<ArtifactGravelFeature> ARTIFACT_GRAVEL = FEATURES.register(location("artifact_gravel"), () -> new ArtifactGravelFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistrySupplier<AmberBlobFeature> AMBER_BLOB = FEATURES.register(location("amber_blob"), () -> new AmberBlobFeature(NoneFeatureConfiguration.CODEC));

    public static void init() { }
}
