package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.ConcentratorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ConcentratorRecipes {
    public static Map<ResourceLocation, ConcentratorRecipe> CONCENTRATOR_RECIPES = new HashMap<>();

    public static void addRecipe(ConcentratorRecipe recipe) {
        CONCENTRATOR_RECIPES.put(recipe.name, recipe);
    }

    public static ConcentratorRecipe getRecipeForInput(ItemStack input) {
        for (ConcentratorRecipe recipe : CONCENTRATOR_RECIPES.values()) {
            if (recipe.input.apply(input)) { //weird phantom error here.i hate it :(
                return recipe;
            }
        }
        return null;
    }
}
