package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ReactorRecipe {
    public final ResourceLocation name;
    public final Ingredient input;
    public final Ingredient input2;
    public final ItemStack output;

    public ReactorRecipe(ResourceLocation name, Ingredient input, Ingredient input2, ItemStack output) {
        this.name = name;
        this.input = input;
        this.input2 = input2;
        this.output = output;
    }

}
