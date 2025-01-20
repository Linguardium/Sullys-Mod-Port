package com.uraneptus.sullysmod.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uraneptus.sullysmod.core.registry.SMRecipeSerializer;
import com.uraneptus.sullysmod.core.registry.SMRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;


public class GrindstonePolishingRecipe implements Recipe<CraftingInput> {
    private static final String NAME = "grindstone_polishing";
    public static final ResourceLocation ID = location(NAME);
//    private final ResourceLocation id;
    private final String recipeGroup;
    private final NonNullList<Ingredient> ingredients;
    public final ItemStack result;
    private final int experience;
    @Nullable
    private PlacementInfo placementInfo;

    public GrindstonePolishingRecipe(String recipeGroup, NonNullList<Ingredient> pIngredients, ItemStack result, int experience) {
        this.recipeGroup = recipeGroup;
        this.ingredients = pIngredients;
        this.result = result;
        this.experience = experience;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getResultCount() {
        return this.result.getCount();
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public @NotNull List<RecipeDisplay> display() {
        return 	List.of(
                new ShapelessCraftingRecipeDisplay(
                this.ingredients.stream().map(Ingredient::display).toList(),
                new SlotDisplay.ItemStackSlotDisplay(this.result),
                new SlotDisplay.ItemSlotDisplay(Items.GRINDSTONE)
                )
        );
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull String group() {
        return this.recipeGroup;
    }

    @Override
    public @NotNull RecipeType<GrindstonePolishingRecipe> getType() {
        return SMRecipeTypes.GRINDSTONE_POLISHING.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredients);
        }
        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SMRecipeSerializer.GRINDSTONE_POLISHING_SERIALIZER.get();
    }

    public static List<GrindstonePolishingRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(SMRecipeTypes.GRINDSTONE_POLISHING.get());
    }

    public static class Serializer implements RecipeSerializer<GrindstonePolishingRecipe> {

        @Override
        public GrindstonePolishingRecipe fromJson(ResourceLocation pRecipeId, JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");

            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for polishing recipe");
            }
            if (!jsonObject.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack result;
            if (jsonObject.get("result").isJsonObject()) {
                result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            }
            else {
                String resultItem = GsonHelper.getAsString(jsonObject, "result");
                ResourceLocation resourcelocation = new ResourceLocation(resultItem);
                result = new ItemStack(ForgeRegistries.ITEMS.getDelegate(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + resultItem + " does not exist")));
            }
            int experience = GsonHelper.getAsInt(jsonObject, "experience", 0);

            return new GrindstonePolishingRecipe(pRecipeId, group, nonnulllist, result, experience);
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            int ingredientSize = pIngredientArray.size();
            for(int i = 0; i < ingredientSize; ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Nullable
        @Override
        public GrindstonePolishingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            for (int j = 0; j < i; j++) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack result = pBuffer.readItem();
            int experience = pBuffer.readInt();

            return new GrindstonePolishingRecipe(pRecipeId, group, nonnulllist, result, experience);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GrindstonePolishingRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.recipeGroup);
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeInt(pRecipe.experience);
        }
    }
}
