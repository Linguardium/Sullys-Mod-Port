package com.uraneptus.sullysmod;

import com.uraneptus.sullysmod.core.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SullysMod {
    public static final String MOD_ID = "sullysmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        SMRegistries.init();

        SMSounds.init();

        // Blocks
        SMBlocksetTypes.init();
        SMBlockEntityTypes.init();
        SMFluids.init();
        SMBlocks.init();
        //SMFluidTypes.init();
        SMBlocks.registerCauldronBlocks();

        // Platform Specific
        // SMBrewingRecipes.init();
        // Items
        SMItemDataComponentTypes.init();
        SMPotions.init();
        SMToolMaterials.init();
        SMItems.init();
        SMCreativeModeTabs.init();
        SMDispenseBehaviors.register();

        // Recipes
        SMRecipeTypes.init();
        SMRecipeSerializer.init();

        // Entities
        SMEntityTypes.init();
        SMWorkstationTypes.init();

        // Loot
        SMLootItemConditions.init();

        // Particles
        SMParticleTypes.init();

        // Worldgen
        SMTreeDecoratorTypes.init();
        SMFeatures.init();
        // Write common init code here.
    }
}
