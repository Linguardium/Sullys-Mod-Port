package com.uraneptus.sullysmod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public interface PointedDripstoneDrippingLogic {
    @Invoker("canDripThrough")
    static boolean canDripThrough(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        throw new UnsupportedOperationException("Implemented in Mixin");
    }

    @Invoker("findBlockVertical")
    static Optional<BlockPos> findBlockVertical(LevelAccessor levelAccessor, BlockPos startPosExcluded, Direction.AxisDirection yDirectionUpDown, BiPredicate<BlockPos, BlockState> stopSearchingValidator, Predicate<BlockState> blockStateValidator, int maxDistance) {
        throw new UnsupportedOperationException("Implemented in Mixin");
    }

    @Invoker("findFillableCauldronBelowStalactiteTip")
    static BlockPos findFillableCauldronBelow(Level level, BlockPos blockPos, Fluid fluid) {
        throw new UnsupportedOperationException("Implemented in Mixin");
    }

}
