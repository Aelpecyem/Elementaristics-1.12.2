package de.aelpecyem.elementaristics.recipe.base;

import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemGoldenThread;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import java.util.List;

public class VesselRecipe {
    public final ResourceLocation name;
    public final List<Ingredient> inputs;
    public final ItemStack output;
    public final Class<? extends EntityLiving> mob;
    public final Aspect aspect;

    public VesselRecipe(ResourceLocation name, ItemStack output, Class<? extends EntityLiving> mob, Ingredient... ingredients) {
        this.name = name;
        this.mob = mob;
        this.aspect = null;
        this.output = output;
        this.inputs = Arrays.asList(ingredients);
    }

    public VesselRecipe(ResourceLocation name, Aspect aspect, Class<? extends EntityLiving> mob, Ingredient... ingredients) {
        this.name = name;
        this.mob = mob;
        this.aspect = aspect;
        ItemStack output = new ItemStack(ModItems.item_golden_thread);
        ((ItemGoldenThread) ModItems.item_golden_thread).setAspect(output, aspect);
        this.output = output;
        this.inputs = Arrays.asList(ingredients);
    }
}
