package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.recipes.GrindstonePolishingRecipe;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static com.uraneptus.sullysmod.core.registry.SMRegistries.RECIPE_SERIALIZERS;

public class SMRecipeSerializer {
    public static final RegistrySupplier<RecipeSerializer<GrindstonePolishingRecipe>> GRINDSTONE_POLISHING_SERIALIZER = RECIPE_SERIALIZERS.register(GrindstonePolishingRecipe.ID, GrindstonePolishingRecipe.Serializer::new);

    public static void init() { }
}
