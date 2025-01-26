package com.uraneptus.sullysmod.common.blocks;

import com.uraneptus.sullysmod.core.registry.SMEntityTypes;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TortoiseEggBlock extends EggBlock {
    public TortoiseEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHatchOn(BlockState state) {
        return state.is(BlockTags.DIRT);
    }

    @Override
    public SoundEvent getHatchSound() {
        return SMSounds.TORTOISE_EGG_HATCH.get();
    }

    @Override
    public SoundEvent getBreakSound() {
        return SMSounds.TORTOISE_EGG_BREAK.get();
    }

    @Override
    public SoundEvent getCrackSound() {
        return SMSounds.TORTOISE_EGG_CRACK.get();
    }

    @Override
    public Boolean canIncreaseHatchLevel(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        return null; // defaults to turtle egg logic
    }

    @Override
    public Boolean onHatch(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Animal tortoise = SMEntityTypes.TORTOISE.get().create(pLevel, EntitySpawnReason.BREEDING);
        if (tortoise != null) {
            tortoise.setAge(-24000);
            tortoise.moveTo((double)pPos.getX() + 0.3D + (double)1 * 0.2D, pPos.getY(), (double)pPos.getZ() + 0.3D, 0.0F, 0.0F);
            pLevel.addFreshEntity(tortoise);
        }
        return true;
    }

}
