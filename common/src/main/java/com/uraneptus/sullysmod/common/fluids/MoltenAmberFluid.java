package com.uraneptus.sullysmod.common.fluids;

import com.uraneptus.sullysmod.core.other.tags.SMBlockTags;
import com.uraneptus.sullysmod.core.registry.SMBlocks;
import com.uraneptus.sullysmod.core.registry.SMFluids;
import com.uraneptus.sullysmod.core.registry.SMItems;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;

//TODO clean up more. I only did the bare minimum for now
public class MoltenAmberFluid extends ArchitecturyFlowingFluid.Flowing {
    public static final float MIN_LEVEL_CUTOFF = 0.44444445F;
    public static final Predicate<BlockState> MELTS_AMBER = state->state.is(SMBlockTags.MELTS_AMBER);
    public static final Predicate<BlockState> NOT_MOLTEN_AMBER = (state->state.is(SMBlocks.MOLTEN_AMBER_BLOCK.get())).negate();
    public static final Predicate<BlockState> MELTS_AMBER_WITHOUT_AMBER = MELTS_AMBER.and(NOT_MOLTEN_AMBER);
    public MoltenAmberFluid(ArchitecturyFluidAttributes attributes) {
        super(attributes);
    }

    public Fluid getFlowing() {
        return SMFluids.FLOWING_MOLTEN_AMBER.get();
    }

    public Fluid getSource() {
        return SMFluids.SOURCE_MOLTEN_AMBER.get();
    }

    public Item getBucket() {
        return SMItems.MOLTEN_AMBER_BUCKET.get();
    }

    public boolean allowedToHarden(Level level, BlockPos blockPos, FluidState fluidState, RandomSource random) {
        if (level.dimension() == Level.NETHER) return false;
        if (level.dimensionType().ultraWarm()) return false;
        return BlockPos.betweenClosedStream(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))
                .map(level::getBlockState)
                .noneMatch(MELTS_AMBER_WITHOUT_AMBER);
    }

    // TODO: configurable hardening chance
    public void randomTick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource pRandom) {
        int chanceToHarden = 2;
        if (fluidState.isSource()) {
            chanceToHarden = 15;
        }
        if (pRandom.nextInt(chanceToHarden) == 0 && level.getBlockState(blockPos).is(SMBlocks.MOLTEN_AMBER_BLOCK.get())) {
            if (!allowedToHarden(level, blockPos, fluidState, pRandom)) return;
            level.setBlockAndUpdate(blockPos, SMBlocks.AMBER.get().defaultBlockState());
        }
    }

    @Override
    public @NotNull BlockState createLegacyBlock(FluidState pState) {
        return SMBlocks.MOLTEN_AMBER_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(pState));
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid otherFluid, Direction direction) {
        return fluidState.getAmount() >= 4 && otherFluid.defaultFluidState().is(FluidTags.WATER);
    }

    @Override
    public int getTickDelay(LevelReader pLevel) {
        return pLevel.dimensionType().ultraWarm() ? 10 : 30;
    }

    @Override
    public int getSpreadDelay(Level pLevel, BlockPos pPos, FluidState pCurrentState, FluidState pNewState) {
        int i = this.getTickDelay(pLevel);
        if (!pCurrentState.isEmpty() && !pNewState.isEmpty() && !(Boolean)pCurrentState.getValue(FALLING) && !(Boolean)pNewState.getValue(FALLING) && pNewState.getHeight(pLevel, pPos) > pCurrentState.getHeight(pLevel, pPos) && pLevel.getRandom().nextInt(4) != 0) {
            i *= 4;
        }
        return i;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return fluidState.getValue(LEVEL);
    }

    protected boolean canConvertToSource(Level pLevel) {
        return pLevel.getGameRules().getBoolean(GameRules.RULE_LAVA_SOURCE_CONVERSION);
    }

    @Override
    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
        if (pDirection == Direction.DOWN) {
            FluidState fluidstate = pLevel.getFluidState(pPos);
            if (fluidstate.is(FluidTags.WATER)) {
                if (pBlockState.getBlock() instanceof LiquidBlock) {
                    pLevel.setBlock(pPos, ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, pPos, pPos, SMBlocks.AMBER.get().defaultBlockState()), 3);
                }

                return;
            }
        }

        super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
    }

    protected boolean isRandomlyTicking() {
        return true;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    public static class Source extends MoltenAmberFluid {

        public Source(ArchitecturyFluidAttributes properties) {
            super(properties);
        }

        public int getAmount(FluidState pState) {
            return 8;
        }

        public boolean isSource(FluidState pState) {
            return true;
        }
    }

    public static class Flowing extends MoltenAmberFluid {

        public Flowing(ArchitecturyFluidAttributes properties) {
            super(properties);
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }

        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

    }
}
