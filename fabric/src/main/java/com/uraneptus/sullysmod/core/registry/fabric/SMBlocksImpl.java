package com.uraneptus.sullysmod.core.registry.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.world.level.block.Block;

public class SMBlocksImpl {
    static void registerWeatheringBlockPair(RegistrySupplier<Block> from, RegistrySupplier<Block> to) {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(from.get(), to.get());
    }
    static void registerWaxableBlockPair(RegistrySupplier<Block> from, RegistrySupplier<Block> to) {
        OxidizableBlocksRegistry.registerWaxableBlockPair(from.get(), to.get());
    }
}
