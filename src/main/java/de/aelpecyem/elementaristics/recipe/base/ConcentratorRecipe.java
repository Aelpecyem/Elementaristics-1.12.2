package de.aelpecyem.elementaristics.recipe.base;

import de.aelpecyem.elementaristics.items.base.ItemEssence;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ConcentratorRecipe {
    public final ResourceLocation name;
    public final Ingredient input;
    public final ItemStack inputInfluencing;
    public final ItemStack output;

    public ConcentratorRecipe(ResourceLocation name, Ingredient input, ItemStack inputInfluencing, ItemStack output) {
        this.name = name;
        this.input = input;
        this.inputInfluencing = inputInfluencing;
        this.output = output;
    }

}
