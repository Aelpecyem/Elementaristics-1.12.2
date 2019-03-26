package de.aelpecyem.elementaristics.patchouli;

import de.aelpecyem.elementaristics.recipe.ReactorRecipes;
import de.aelpecyem.elementaristics.recipe.base.ReactorRecipe;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class ProcessorReactor implements IComponentProcessor {
    ReactorRecipe recipe;

    @Override
    public void setup(IVariableProvider<String> iVariableProvider) {
        ResourceLocation res = new ResourceLocation(iVariableProvider.get("recipe"));
        this.recipe = ReactorRecipes.REACTOR_RECIPES.get(res);
    }

    @Override
    public String process(String s) {
        switch (s) {
            case "input":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input);
            case "output":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.output);
            case "input2":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input2);
            default:
                return null;
        }
    }
}
