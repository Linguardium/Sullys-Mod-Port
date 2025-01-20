package com.uraneptus.sullysmod.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import static com.uraneptus.sullysmod.common.blocks.utilities.AmberUtil.AMBER_CAULDRON_INTERACTION;

public class AmberLayeredCauldronBlock extends LayeredCauldronBlock {

    public AmberLayeredCauldronBlock(Properties pProperties) {
        super(Biome.Precipitation.NONE, AMBER_CAULDRON_INTERACTION.get(), pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1));
    }


    @Override
    protected boolean canReceiveStalactiteDrip(Fluid pFluid) {
        return false;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {

    }

    @Override
    public void handlePrecipitation(BlockState pState, Level pLevel, BlockPos pPos, Biome.Precipitation pPrecipitation) {

    }
}
