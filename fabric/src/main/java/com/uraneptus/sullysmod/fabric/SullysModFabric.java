package com.uraneptus.sullysmod.fabric;

import net.fabricmc.api.ModInitializer;

import com.uraneptus.sullysmod.SullysMod;

public final class SullysModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        SullysMod.init();
    }
}
