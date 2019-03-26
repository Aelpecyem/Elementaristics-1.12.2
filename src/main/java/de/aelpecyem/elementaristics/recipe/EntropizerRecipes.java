package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.EntropizerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class EntropizerRecipes {
    public static Map<ResourceLocation, EntropizerRecipe> ENTROPIZER_RECIPES = new HashMap<>();

    public static void addRecipe(EntropizerRecipe recipe) {
        ENTROPIZER_RECIPES.put(recipe.name, recipe);
    }

    public static EntropizerRecipe getRecipeForInput(ItemStack input) {
        for (EntropizerRecipe recipe : ENTROPIZER_RECIPES.values()) {
            if (recipe.input.apply(input)) { //weird phantom error here.i hate it :(
                return recipe;
            }
        }
        return null;
    }
}
