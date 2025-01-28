package com.uraneptus.sullysmod.fabric;

import com.uraneptus.sullysmod.core.registry.SMBrewingRecipes;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;

public class SMBrewingRecipesFabric {
    public static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(SMBrewingRecipes::register);
    }
}
