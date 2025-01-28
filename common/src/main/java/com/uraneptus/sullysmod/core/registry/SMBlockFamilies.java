package com.uraneptus.sullysmod.core.registry;

import net.minecraft.data.BlockFamily;

import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.registry.SMBlocks.*;

public class SMBlockFamilies {
    public static final Supplier<BlockFamily> AMBER_BRICK_BLOCK_FAMILY = ()->{
        return new BlockFamily.Builder(AMBER_BRICKS.get())
                .stairs(AMBER_BRICK_STAIRS.get())
                .chiseled(CHISELED_AMBER.get())
                .slab(AMBER_BRICK_SLAB.get())
                .wall(AMBER_BRICK_WALL.get())
                .getFamily();
    };
    public static final Supplier<BlockFamily> ROUGH_JADE_BRICK_BLOCK_FAMILY = ()->{
        return new BlockFamily.Builder(ROUGH_JADE_BRICKS.get())
                .stairs(ROUGH_JADE_BRICK_STAIRS.get())
                .slab(ROUGH_JADE_BRICK_SLAB.get())
                .wall(ROUGH_JADE_BRICK_WALL.get())
                .getFamily();
    };
    public static final Supplier<BlockFamily> JADE_BRICK_BLOCK_FAMILY = ()->{
        return new BlockFamily.Builder(JADE_BRICKS.get())
                .stairs(JADE_BRICK_STAIRS.get())
                .slab(JADE_BRICK_SLAB.get())
                .wall(JADE_BRICK_WALL.get())
                .getFamily();
    };
    public static final Supplier<BlockFamily> PETRIFIED_WOOD_BLOCK_FAMILY = ()->{
        return new BlockFamily.Builder(PETRIFIED_PLANKS.get())
                .button(PETRIFIED_BUTTON.get())
                .stairs(PETRIFIED_STAIRS.get())
                .slab(PETRIFIED_SLAB.get())
                .door(PETRIFIED_DOOR.get())
                .fence(PETRIFIED_FENCE.get())
                .fenceGate(PETRIFIED_FENCE_GATE.get())
                .pressurePlate(PETRIFIED_PRESSURE_PLATE.get())
                .sign(PETRIFIED_SIGN.getFirst().get(), PETRIFIED_SIGN.getSecond().get())
                .trapdoor(PETRIFIED_TRAPDOOR.get())
                .getFamily();
    };
}
