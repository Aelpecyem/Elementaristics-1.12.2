package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeRecipes { //todo oh god all of that needs to get better in the rewrite
    public static Map<ResourceLocation, ForgeRecipe> FORGE_RECIPES = new HashMap<>();

    public static void addRecipe(ForgeRecipe recipe) {
        FORGE_RECIPES.put(recipe.name, recipe);
    }

    public static ForgeRecipe getRecipeForInputs(List<ItemStack> stacks) {
        for (ForgeRecipe recipe : FORGE_RECIPES.values()) {
            int checksPassed = 0;
            boolean passedSlot1 = false, passedSlot2 = false, passedSlot3 = false, passedSlot4 = false, passedSlot5 = false;
            for (ItemStack stack : stacks) {
                if (!passedSlot1 && recipe.input1.apply(stack)) {
                    checksPassed++;
                    passedSlot1 = true;
                } else if (!passedSlot2 && recipe.input2.apply(stack)) {
                    checksPassed++;
                    passedSlot2 = true;
                } else if (!passedSlot3 && recipe.input3.apply(stack)) {
                    checksPassed++;
                    passedSlot3 = true;
                } else if (!passedSlot4 && recipe.input4.apply(stack)) {
                    checksPassed++;
                    passedSlot4 = true;
                } else if (!passedSlot5 && recipe.input5.apply(stack)) {
                    checksPassed++;
                    passedSlot5 = true;
                }
            }
            if (checksPassed >= 5){
                return recipe;
            }
        }
        return null;
    }

}
