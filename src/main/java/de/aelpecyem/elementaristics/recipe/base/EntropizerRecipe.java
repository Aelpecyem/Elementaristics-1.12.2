package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class EntropizerRecipe {
    public final ResourceLocation name;
    public final Ingredient input;
    public final ItemStack output;

    public EntropizerRecipe(ResourceLocation name, Ingredient input, ItemStack output) {
        this.name = name;
        this.input = input;
        this.output = output;
    }

}
