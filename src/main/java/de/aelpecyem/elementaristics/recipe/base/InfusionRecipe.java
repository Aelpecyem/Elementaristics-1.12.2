package de.aelpecyem.elementaristics.recipe.base;

import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//haha yes, no this is no tc infusion, but it works actually pretty similar to the crucible
public class InfusionRecipe {
    public final ResourceLocation name;
    public final Ingredient input;
    public final ItemStack output;
    public final Set<Aspect> aspects = new HashSet<>();

    public InfusionRecipe(ResourceLocation name, Ingredient input, ItemStack output, Aspect... aspects) {
        this.name = name;
        this.input = input;
        for (Aspect aspect : aspects) {
            this.aspects.add(aspect);
        }
        this.output = output;
    }

}
