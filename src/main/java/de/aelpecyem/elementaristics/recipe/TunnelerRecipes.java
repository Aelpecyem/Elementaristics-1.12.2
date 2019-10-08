package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.TunnelerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TunnelerRecipes {
    public static Map<ResourceLocation, TunnelerRecipe> TUNNELER_RECIPES = new HashMap<>();

    public static TunnelerRecipe getRecipeForInput(ItemStack input) {
        for (TunnelerRecipe recipe : TUNNELER_RECIPES.values()) {
            if (recipe.input.apply(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static void addRecipe(TunnelerRecipe tunnelerRecipe) {
        TUNNELER_RECIPES.put(tunnelerRecipe.name, tunnelerRecipe);

    }

}
