package com.uraneptus.sullysmod.common.levelgen;

import com.mojang.serialization.Codec;
import com.uraneptus.sullysmod.common.blockentities.AmberBE;
import com.uraneptus.sullysmod.core.other.tags.SMEntityTags;
import com.uraneptus.sullysmod.core.registry.SMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Optional;


public class AmberBlobFeature extends Feature<NoneFeatureConfiguration> {

    public AmberBlobFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos.MutableBlockPos blockpos = context.origin().mutable();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource randomsource = context.random();
        BlockState amber = SMBlocks.AMBER.get().defaultBlockState();
        HolderSet<EntityType<?>> possibleEntities = BuiltInRegistries.ENTITY_TYPE.get(SMEntityTags.SPAWN_IN_AMBER).map(e->(HolderSet<EntityType<?>>)e).orElse(HolderSet.empty());
        if (possibleEntities.size() < 1) return false;
        int placedEntities = 0;

        if (blockpos.getY() <= worldgenlevel.getMinY() + 3) {
            return false;
        } else {
            for (int l = 0; l < 3; ++l) {
                int i = randomsource.nextInt(3);
                int j = randomsource.nextInt(3);
                int k = randomsource.nextInt(3);
                double f = (float) (i + j + k) * 0.333F + 0.5F;

                for (BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-i, -j, -k), blockpos.offset(i, j, k))) {
                    if (blockpos1.distSqr(blockpos) > (f * f)) continue;
                    worldgenlevel.setBlock(blockpos1, amber, Block.UPDATE_ALL);
                    if (placedEntities >= 2 || randomsource.nextInt(10) != 0) continue;
                    if (worldgenlevel.getBlockEntity(blockpos1) instanceof AmberBE amberBE) {
                        Optional<Holder<EntityType<?>>> entityType = possibleEntities.getRandomElement(randomsource);
                        if (entityType.isPresent() && amberBE.storeTypeForGeneration(entityType.get().value())) {
                            placedEntities++;
                        }
                    }
                }
                blockpos.move(-1 + randomsource.nextInt(2), -randomsource.nextInt(2), -1 + randomsource.nextInt(2));
            }

            return true;
        }
    }
}
