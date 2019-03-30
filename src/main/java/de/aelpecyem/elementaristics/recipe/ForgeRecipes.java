package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import de.aelpecyem.elementaristics.recipe.base.PurifierRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeRecipes {
    public static Map<ResourceLocation, ForgeRecipe> FORGE_RECIPES = new HashMap<>();

    public static void addRecipe(ForgeRecipe recipe) {
        FORGE_RECIPES.put(recipe.name, recipe);
    }

    public static ForgeRecipe getRecipeForInputs(List<ItemStack> stacks) {
        for (ForgeRecipe recipe : FORGE_RECIPES.values()) {
            int checksPassed = 0;
            for (ItemStack stack : stacks) {
                if (recipe.input1.apply(stack)){
                    checksPassed++;
                }else if(recipe.input2.apply(stack)){
                    checksPassed++;
                }else if(recipe.input3.apply(stack)){
                    checksPassed++;
                }else if (recipe.input4.apply(stack)){
                    checksPassed++;
                }else if (recipe.input5.apply(stack)){
                    checksPassed++;
                }
            }
            if (checksPassed >= 5){
                return recipe;
            }
        }
        return null;
    }
}
