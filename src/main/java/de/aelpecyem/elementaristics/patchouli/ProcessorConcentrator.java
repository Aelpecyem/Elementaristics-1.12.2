package de.aelpecyem.elementaristics.patchouli;

import de.aelpecyem.elementaristics.recipe.ConcentratorRecipes;
import de.aelpecyem.elementaristics.recipe.base.ConcentratorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class ProcessorConcentrator implements IComponentProcessor {
    ConcentratorRecipe recipe;

    @Override
    public void setup(IVariableProvider<String> iVariableProvider) {
        ResourceLocation res = new ResourceLocation(iVariableProvider.get("recipe"));
        this.recipe = ConcentratorRecipes.CONCENTRATOR_RECIPES.get(res);
    }

    @Override
    public String process(String s) {
        switch (s) {
            case "input":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input);
            case "output":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.output);
            case "inputInfluencing":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.inputInfluencing);
            default:
                return null;
        }
    }
}
