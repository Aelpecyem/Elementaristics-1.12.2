package de.aelpecyem.elementaristics.patchouli;

import de.aelpecyem.elementaristics.recipe.PurifierRecipes;
import de.aelpecyem.elementaristics.recipe.base.PurifierRecipe;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class ProcessorPurifier implements IComponentProcessor {
    PurifierRecipe recipe;

    @Override
    public void setup(IVariableProvider<String> iVariableProvider) {
        ResourceLocation res = new ResourceLocation(iVariableProvider.get("recipe"));
        this.recipe = PurifierRecipes.PURIFIER_RECIPES.get(res);
    }

    @Override
    public String process(String s) {
        switch (s) {
            case "input":
                return PatchouliAPI.instance.serializeIngredient(this.recipe.input);
            case "output":
                return PatchouliAPI.instance.serializeItemStack(this.recipe.output);
            case "time":
                return I18n.format("book.purifierTime") + " " + String.valueOf(this.recipe.time);
            default:
                return null;
        }
    }
}
