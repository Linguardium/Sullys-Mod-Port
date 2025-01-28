package com.uraneptus.sullysmod.common.fluids;

import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class WarmingFluidAttributes extends SimpleArchitecturyFluidAttributes {
    private Integer warmDropoff;
    private Integer warmSlope;
    private Integer warmTickDelay;

    public static WarmingFluidAttributes ofSupplier(Supplier<? extends Supplier<? extends Fluid>> flowingFluid, Supplier<? extends Supplier<? extends Fluid>> sourceFluid) {
        return WarmingFluidAttributes.of(() -> flowingFluid.get().get(), () -> sourceFluid.get().get());
    }


    public static WarmingFluidAttributes of(Supplier<? extends Fluid> flowingFluid, Supplier<? extends Fluid> sourceFluid) {
        return new WarmingFluidAttributes(flowingFluid, sourceFluid);
    }


    private WarmingFluidAttributes(Supplier<? extends Fluid> flowingFluid, Supplier<? extends Fluid> sourceFluid) {
        super(flowingFluid, sourceFluid);
    }

    @Override
    public int getSlopeFindDistance(@Nullable LevelReader level) {
        if (level != null && level.dimensionType().ultraWarm()) {
            return warmSlope;
        }
        return super.getSlopeFindDistance(level);
    }

    @Override
    public int getDropOff(@Nullable LevelReader level) {
        if (level != null && level.dimensionType().ultraWarm()) {
            return warmDropoff;
        }
        return super.getDropOff(level);
    }

    @Override
    public int getTickDelay(@Nullable LevelReader level) {
        if (level != null && level.dimensionType().ultraWarm()) {
            return warmTickDelay;
        }
        return super.getTickDelay(level);
    }

    public WarmingFluidAttributes dropOff(int coldDropoff, int warmDropoff) {
        this.dropOff(coldDropoff);
        this.warmDropoff = warmDropoff;
        return this;
    }
    public WarmingFluidAttributes tickDelay(int coldTickDelay, int warmTickDelay) {
        this.tickDelay(coldTickDelay);
        this.warmTickDelay = warmTickDelay;
        return this;
    }

    public WarmingFluidAttributes slopeFindDistance(int coldSlopeFindDistance, int warmSlopeFindDistance) {
        this.slopeFindDistance(coldSlopeFindDistance);
        this.warmSlope = warmSlopeFindDistance;
        return this;
    }

    // NOT HANDLED IN ATTRIBUTES?
//    @Override
//    public void setItemMovement(ItemEntity entity) {
//        Vec3 vec3 = entity.getDeltaMovement();
//        entity.setDeltaMovement(vec3.x * (double)0.95F, vec3.y + (double)(vec3.y < (double)0.06F ? 5.0E-4F : 0.0F), vec3.z * (double)0.95F);
//    }

}
