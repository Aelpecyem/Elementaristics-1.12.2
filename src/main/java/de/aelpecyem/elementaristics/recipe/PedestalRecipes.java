package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.recipe.base.PedestalRecipe;
import de.aelpecyem.elementaristics.recipe.base.PurifierRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class PedestalRecipes {
    public static Map<ResourceLocation, PedestalRecipe> PEDESTAL_RECIPES = new HashMap<>();

    public static void addRecipe(PedestalRecipe recipe) {
        PEDESTAL_RECIPES.put(recipe.name, recipe);
    }

    public static PedestalRecipe getRecipeForInput(ItemStack input) {
        for (PedestalRecipe recipe : PEDESTAL_RECIPES.values()) {
            if (recipe.input.apply(input)) {
                return recipe;
            }
        }
        return null;
    }
}
