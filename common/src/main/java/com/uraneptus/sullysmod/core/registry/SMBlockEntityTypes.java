package com.uraneptus.sullysmod.core.registry;

import com.google.common.collect.Sets;
import com.uraneptus.sullysmod.common.blockentities.AmberBE;
import com.uraneptus.sullysmod.common.blockentities.AncientSkullBE;
import com.uraneptus.sullysmod.common.blockentities.FlingerTotemBE;
import com.uraneptus.sullysmod.common.blockentities.ItemStandBE;
import com.uraneptus.sullysmod.common.blocks.AmberBlock;
import com.uraneptus.sullysmod.common.blocks.FlingerTotem;
import com.uraneptus.sullysmod.common.blocks.ItemStandBlock;
import com.uraneptus.sullysmod.mixins.BlockEntityTypeHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.BLOCKS;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.BLOCK_ENTITY_TYPES;

public class SMBlockEntityTypes {

    public static final RegistrySupplier<BlockEntityType<FlingerTotemBE>> FLINGER_TOTEM = registerBE("flinger_totem", FlingerTotemBE::new, FlingerTotem.class);
    public static final RegistrySupplier<BlockEntityType<AmberBE>> AMBER = registerBE("amber", AmberBE::new, AmberBlock.class);
    public static final RegistrySupplier<BlockEntityType<ItemStandBE>> ITEM_STAND = registerBE("item_stand", ItemStandBE::new, ItemStandBlock.class);
    public static final RegistrySupplier<BlockEntityType<AncientSkullBE>> ANCIENT_SKULL = registerBE("ancient_skull", AncientSkullBE::new, () -> AncientSkullBE.SKULLS);

    public static Block[] collectBlocks(Class<?> blockClass) {
        return BLOCKS.entrySet().stream().map(Map.Entry::getValue).filter(blockClass::isInstance).toArray(Block[]::new);
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> registerBE(String name, BlockEntityType.BlockEntitySupplier<? extends T> blockEntity, Supplier<Set<Block>> validBlocks) {
        
        return BLOCK_ENTITY_TYPES.register(location(name), () -> BlockEntityTypeHelper.createBlockEntityType(blockEntity, validBlocks.get()));
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> registerBE(String name, BlockEntityType.BlockEntitySupplier<? extends T> blockEntity, Class<? extends Block> blockClass) {
        return BLOCK_ENTITY_TYPES.register(location(name), () -> BlockEntityTypeHelper.createBlockEntityType(blockEntity, Sets.newHashSet(collectBlocks(blockClass))));
    }
}