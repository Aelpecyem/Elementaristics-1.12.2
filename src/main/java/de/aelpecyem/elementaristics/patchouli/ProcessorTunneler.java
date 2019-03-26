package de.aelpecyem.elementaristics.patchouli;

import de.aelpecyem.elementaristics.recipe.TunnelerRecipes;
import de.aelpecyem.elementaristics.recipe.base.TunnelerRecipe;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class ProcessorTunneler implements IComponentProcessor {
    TunnelerRecipe recipe;

    @Override
    public void setup(IVariableProvider<String> iVariableProvider) {
        ResourceLocation res = new ResourceLocation(iVariableProvider.get("recipe"));
        this.recipe = TunnelerRecipes.TUNNELER_RECIPES.get(res);
    }

    @Override
    public String process(String s) {
        switch (s) {
            case "input":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input);
            case "output":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.output);
            case "essence0":
                return PatchouliAPI.instance.serializeItemStack(recipe.filterItems.get(0));
            case "essence1":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.filterItems.get(1));
            case "essence2":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.filterItems.get(2));
            default:
                return null;
        }
    }
}
