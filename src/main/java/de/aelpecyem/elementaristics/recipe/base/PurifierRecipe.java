package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class PurifierRecipe {
    public final ResourceLocation name;
    public final int time;
    public final Ingredient input;
    public final ItemStack output;

    public PurifierRecipe(ResourceLocation name, Ingredient input, ItemStack output, int time) {
        this.time = time;
        this.name = name;
        this.input = input;
        this.output = output;
    }

}
