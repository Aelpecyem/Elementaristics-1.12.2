package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.recipe.base.VesselRecipe;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class VesselRecipes { //todo oh god all of that needs to get better in the rewrite
    public static Map<ResourceLocation, VesselRecipe> VESSEL_RECIPES = new HashMap<>();

    public static void addRecipe(VesselRecipe recipe) {
        VESSEL_RECIPES.put(recipe.name, recipe);
    }

    public static VesselRecipe getRecipeForInputs(IItemHandler stacks, EntityLiving cagedEntit) {
        for (VesselRecipe recipe : VESSEL_RECIPES.values()) {
            int checkCount = 0;
            for (int i = 0; i < stacks.getSlots(); i++) {
                ItemStack stack = stacks.getStackInSlot(i);
                CopyOnWriteArrayList<Ingredient> ingredients = new CopyOnWriteArrayList<>();
                ingredients.addAll(recipe.inputs);
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.apply(stack)) {
                        checkCount++;
                        ingredients.remove(ingredient);
                        break;
                    }
                }
            }
            if (checkCount >= 5) {
                if (cagedEntit.getClass() == recipe.mob) {
                    if (recipe.aspect == null || (cagedEntit instanceof EntityCultist && ((EntityCultist) cagedEntit).getAspect().getId() == recipe.aspect.getId()))
                        return recipe;
                }
            }
        }
        return null;
    }

}
