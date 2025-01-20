package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.recipes.GrindstonePolishingRecipe;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import static com.uraneptus.sullysmod.core.registry.SMRegistries.RECIPE_TYPES;

public class SMRecipeTypes {

    public static void init() { }

    public static final RegistrySupplier<RecipeType<GrindstonePolishingRecipe>> GRINDSTONE_POLISHING = registerType(GrindstonePolishingRecipe.ID);

    /**
     * Modified version of {@link RecipeType#register(String)}
     */
    public static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> registerType(final ResourceLocation identifier) {
        return RECIPE_TYPES.register(identifier, () ->
         new RecipeType<>() {
            public String toString() {
                return identifier.toString();
            }
        });
    }
}
