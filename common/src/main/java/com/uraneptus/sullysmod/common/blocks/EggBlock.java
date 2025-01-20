package com.uraneptus.sullysmod.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class EggBlock extends TurtleEggBlock {
    public EggBlock(Properties properties) {
        super(properties);
    }
    abstract public boolean canHatchOn(BlockState state);
    abstract public SoundEvent getHatchSound();
    abstract public SoundEvent getBreakSound();
    abstract public SoundEvent getCrackSound();
    @Nullable
    abstract public Boolean canIncreaseHatchLevel(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom);
    abstract public Boolean onHatch(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom);
}
