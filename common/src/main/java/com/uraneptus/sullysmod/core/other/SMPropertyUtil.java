package com.uraneptus.sullysmod.core.other;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class SMPropertyUtil {
    public static <A> boolean never(BlockState arg, BlockGetter arg2, BlockPos arg3, A object) {
        return false;
    }
    public static <A> boolean always(BlockState arg, BlockGetter arg2, BlockPos arg3, A object) {
        return true;
    }
}
