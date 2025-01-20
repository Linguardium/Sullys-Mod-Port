package com.uraneptus.sullysmod.core.registry;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

public class SMRegistries {
    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    public static final Registrar<Item> ITEMS = MANAGER.get().get(Registries.ITEM);
    public static final Registrar<Block> BLOCKS = MANAGER.get().get(Registries.BLOCK);
    public static final Registrar<SoundEvent> SOUNDS = MANAGER.get().get(Registries.SOUND_EVENT);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = MANAGER.get().get(Registries.BLOCK_ENTITY_TYPE);
    public static void init() { }

}
