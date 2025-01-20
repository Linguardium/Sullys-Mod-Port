package com.uraneptus.sullysmod.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.uraneptus.sullysmod.common.blocks.EggBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(TurtleEggBlock.class)
public class EggBlockExtension extends Block  {
    public EggBlockExtension(Properties properties) {
        super(properties);
    }

    @ModifyExpressionValue(method="randomTick", at=@At(value="FIELD", target="Lnet/minecraft/sounds/SoundEvents;TURTLE_EGG_CRACK:Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent crackSound(SoundEvent original) {
        if ((Object)this instanceof EggBlock eggBlock) {
            return eggBlock.getCrackSound();
        }
        return original;
    }
    @ModifyExpressionValue(method="randomTick", at=@At(value="FIELD", target="Lnet/minecraft/sounds/SoundEvents;TURTLE_EGG_HATCH:Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent hatchSound(SoundEvent original) {
        if ((Object)this instanceof EggBlock eggBlock) {
            return eggBlock.getHatchSound();
        }
        return original;
    }
    @ModifyExpressionValue(method="decreaseEggs", at=@At(value="FIELD", target="Lnet/minecraft/sounds/SoundEvents;TURTLE_EGG_BREAK:Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent breakSound(SoundEvent original) {
        if ((Object)this instanceof EggBlock eggBlock) {
            return eggBlock.getBreakSound();
        }
        return original;
    }
    @ModifyExpressionValue(method={"randomTick", "onPlace"}, at=@At(value="INVOKE", target = "Lnet/minecraft/world/level/block/TurtleEggBlock;onSand(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean onSupportedBlock(boolean original, BlockState blockState, @Coerce Level level, BlockPos blockPos) {
        if ((Object)this instanceof EggBlock eggBlock) {
            return eggBlock.canHatchOn(level.getBlockState(blockPos.below()));
        }
        return original;
    }


    @ModifyExpressionValue(method={"randomTick"}, at=@At(value="INVOKE", target = "Lnet/minecraft/world/level/block/TurtleEggBlock;shouldUpdateHatchLevel(Lnet/minecraft/world/level/Level;)Z"))
    private boolean hatchLevelIncreaseLogic(boolean original, BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if ((Object)this instanceof EggBlock eggBlock) {
            Boolean increaseable = eggBlock.canIncreaseHatchLevel(blockState, serverLevel, blockPos, randomSource);
            if (increaseable != null) return increaseable;
        }
        return original;
    }


    @ModifyExpressionValue(method="destroyEgg", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean isEgg(boolean original, Level level, BlockState blockState) {
        return original || blockState.getBlock() instanceof EggBlock;
    }

    @WrapOperation(method="randomTick",at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Lnet/minecraft/world/entity/Entity;"))
    private Entity onSpawn(EntityType instance, Level level, EntitySpawnReason spawnReason, Operation<Entity> original, BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if ((Object)this instanceof EggBlock eggBlock) {
            if (eggBlock.onHatch(blockState, serverLevel, blockPos, randomSource)) return null;
        }
        return original.call(instance, level, spawnReason);
    }
}
