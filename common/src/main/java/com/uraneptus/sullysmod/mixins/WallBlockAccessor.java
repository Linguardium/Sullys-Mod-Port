package com.uraneptus.sullysmod.mixins;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(WallBlock.class)
public interface WallBlockAccessor {
    @Accessor
    Map<BlockState, VoxelShape> getShapeByIndex();
    @Accessor
    Map<BlockState, VoxelShape> getCollisionShapeByIndex();
    @Accessor
    @Mutable
    @Final
    void setShapeByIndex(Map<BlockState, VoxelShape> newMap);
    @Accessor
    @Mutable
    @Final
    void setCollisionShapeByIndex(Map<BlockState, VoxelShape> newMap);

}
