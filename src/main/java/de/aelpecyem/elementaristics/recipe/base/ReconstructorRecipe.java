package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ReconstructorRecipe {
    public final ResourceLocation name;
    public final Block blockTarget;
    public final Ingredient input1, input2, input3;
    public final Block output;

    public ReconstructorRecipe(ResourceLocation name, Block blockTarget, Ingredient input1, Ingredient input2, Ingredient input3, Block output) {
        this.name = name;
        this.blockTarget = blockTarget;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
    }

}
