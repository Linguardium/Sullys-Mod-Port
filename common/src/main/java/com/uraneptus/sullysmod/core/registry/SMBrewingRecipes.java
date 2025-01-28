package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.core.SMFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class SMBrewingRecipes {

    public static void register(PotionBrewing.Builder builder) {
        registerRecipe(builder, Potions.AWKWARD, SMItems.JADE.get(), Potions.LUCK, SMFeatures.JADE);
        registerRecipe(builder, Potions.LUCK, Items.FERMENTED_SPIDER_EYE, SMPotions.UNLUCK, SMFeatures.UNLUCK_POTION);
        registerRecipe(builder, Potions.AWKWARD, SMItems.TORTOISE_SCUTE.get(), SMPotions.RESISTANCE, SMFeatures.RESISTANCE_POTION);
        registerRecipe(builder, SMPotions.RESISTANCE, Items.REDSTONE, SMPotions.LONG_RESISTANCE, SMFeatures.RESISTANCE_POTION);
        registerRecipe(builder, SMPotions.RESISTANCE, Items.GLOWSTONE_DUST, SMPotions.STRONG_RESISTANCE, SMFeatures.RESISTANCE_POTION);
    }

    //Note: It's not possible yet to prevent potion variant recipes (splash, lingering etc), but as long as the base potion can't be made, it's fine
    public static void registerRecipe(PotionBrewing.Builder builder, Holder<Potion> input, Item ingredient, Holder<Potion> result, SMFeatures feature) {
        if (SMFeatures.isEnabled(feature)) {
            builder.addMix(input, ingredient, result);
        }
    }
}
