package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class GloriousRecipe {
    public final ResourceLocation name;
    public final Ingredient input;
    public final Block output;

    public GloriousRecipe(ResourceLocation name, Ingredient input, Block output) {
        this.name = name;
        this.input = input;
        this.output = output;
    }

}
