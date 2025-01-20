package com.uraneptus.sullysmod.common.blocks;

import com.uraneptus.sullysmod.core.other.SMFeatureDefinitions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class PetrifiedSapling extends SaplingBlock {

    public PetrifiedSapling(Properties pProperties) {
        super(new TreeGrower(location("petrified").toString(), Optional.empty(),Optional.of(SMFeatureDefinitions.CONFIGURED_PETRIFIED_TREE_SMALL),Optional.empty()), pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.GRAVEL);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return levelReader.getBlockState(blockPos.below()).is(Blocks.GRAVEL);
    }
}
