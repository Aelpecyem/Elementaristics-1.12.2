package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ForgeRecipe {
    public final ResourceLocation name;
    public final int hitAmount;
    public final Ingredient input;
    public final ItemStack output;

    public ForgeRecipe(ResourceLocation name, Ingredient input, ItemStack output, int hitAmount) {
        this.hitAmount = hitAmount;
        this.name = name;
        this.input = input;
        this.output = output;
    }

}
