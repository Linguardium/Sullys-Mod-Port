package com.uraneptus.sullysmod.common.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WeatheringCopperButtonBlock extends CopperButtonBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState weatherState;

    public static final Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    public WeatheringCopperButtonBlock(Properties properties, BlockSetType pType, int pTicksToStayPressed, WeatherState weatherState) {
        super(properties, pType, pTicksToStayPressed);
        this.weatherState = weatherState;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState blockState) {
        return this.getNext(blockState).isPresent();
    }

    @Override
    public @NotNull WeatherState getAge() {
        return weatherState;
    }
}
