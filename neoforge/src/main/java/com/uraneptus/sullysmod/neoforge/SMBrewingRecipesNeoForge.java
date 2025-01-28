package com.uraneptus.sullysmod.neoforge;

import com.uraneptus.sullysmod.core.registry.SMBrewingRecipes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class SMBrewingRecipesNeoForge {
    @SubscribeEvent
    public void registerBrewingRecipe(RegisterBrewingRecipesEvent event) {
        SMBrewingRecipes.register(event.getBuilder());
    }
}
