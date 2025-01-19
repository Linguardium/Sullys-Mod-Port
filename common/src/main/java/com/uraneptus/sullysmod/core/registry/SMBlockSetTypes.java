package com.uraneptus.sullysmod.core.registry;

import com.google.common.base.Suppliers;
import com.uraneptus.sullysmod.mixins.BlockSetTypeHelper;
import com.uraneptus.sullysmod.mixins.WoodTypeHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMBlocksetTypes {
    public static final Supplier<BlockSetType> PETRIFIED_BLOCKSET = Suppliers.memoize(() -> createBlocksetType("petrified", true, false, false, BlockSetType.PressurePlateSensitivity.MOBS, SMSounds.PETRIFIED_WOOD, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    // Implemented by vanilla
    // Change in behavior. Vanilla copper buttons cannot be pressed by arrows
    // public static final Supplier<BlockSetType> COPPER_BLOCKSET = Suppliers.memoize(() -> createBlocksetType("copper", true, SoundType.COPPER, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SMSounds.COPPER_BUTTON_CLICK_OFF.get(), SMSounds.COPPER_BUTTON_CLICK_ON.get()));

    public static final Supplier<WoodType> PETRIFIED_WOOD_TYPE = Suppliers.memoize(() -> createDefaultWoodType(PETRIFIED_BLOCKSET));

    public static BlockSetType createBlocksetType(String name, boolean canOpenByHand, boolean canOpenByWindCharge, boolean canButtonBeActivatedByArrows, BlockSetType.PressurePlateSensitivity pressurePlateSensitivity, SoundType soundType, SoundEvent doorClose, SoundEvent doorOpen, SoundEvent trapdoorClose, SoundEvent trapdoorOpen, SoundEvent pressurePlateClickOff, SoundEvent pressurePlateClickOn, SoundEvent buttonClickOff, SoundEvent buttonClickOn) {
        return BlockSetTypeHelper.registerType(new BlockSetType(
                location(name).toString(),
                canOpenByHand,
                canOpenByWindCharge,
                canButtonBeActivatedByArrows,
                pressurePlateSensitivity,
                soundType,
                doorClose,
                doorOpen,
                trapdoorClose,
                trapdoorOpen,
                pressurePlateClickOff,
                pressurePlateClickOn,
                buttonClickOff,
                buttonClickOn
        ));
    }

    public static WoodType createDefaultWoodType(Supplier<BlockSetType> blockSetType) {
        return createWoodType(blockSetType, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
    }
    public static WoodType createWoodType(Supplier<BlockSetType> blockSetType, SoundType hangingSignSoundType, SoundEvent fenceGateClose, SoundEvent fenceGateOpen) {
        return WoodTypeHelper.registerWoodType(new WoodType(blockSetType.get().name(), blockSetType.get(), blockSetType.get().soundType(), hangingSignSoundType, fenceGateClose, fenceGateOpen));
    }
    public static void init() { }
}
