package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ForgeRecipe {
    public final ResourceLocation name;
    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    public final Ingredient input4;
    public final Ingredient input5;
    public final ItemStack output;

    public ForgeRecipe(ResourceLocation name, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4, Ingredient input5, ItemStack output) {
        this.name = name;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.input5 = input5;
        this.output = output;
    }

}
