package de.aelpecyem.elementaristics.patchouli;

import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
import de.aelpecyem.elementaristics.recipe.ReactorRecipes;
import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import de.aelpecyem.elementaristics.recipe.base.ReactorRecipe;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class ProcessorForge implements IComponentProcessor {
    ForgeRecipe recipe;

    @Override
    public void setup(IVariableProvider<String> iVariableProvider) {
        ResourceLocation res = new ResourceLocation(iVariableProvider.get("recipe"));
        this.recipe = ForgeRecipes.FORGE_RECIPES.get(res);
    }

    @Override
    public String process(String s) {
        switch (s) {
            case "input1":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input1);
            case "input2":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input2);
            case "input3":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input3);
            case "input4":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input4);
            case "input5":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input5);
            case "output":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.output);
            default:
                return null;
        }
    }
}
