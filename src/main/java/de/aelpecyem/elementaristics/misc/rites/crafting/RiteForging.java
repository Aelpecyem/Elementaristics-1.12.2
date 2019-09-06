package de.aelpecyem.elementaristics.misc.rites.crafting;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.List;

public class RiteForging extends RiteBase {

    public RiteForging() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_forging"), 200, 0.35F, 8, Aspects.fire);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityItem> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2), null);
        List<ItemStack> stacks = new ArrayList<>();
        for (EntityItem entity : entities) {
            for (int i = 0; i < entity.getItem().getCount(); i++)
                stacks.add(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()));
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.fire.getColor(), 1, 10, 0, false, true);
        }
        if (!nexus.world.isRemote) {
            if (ForgeRecipes.getRecipeForInputs(stacks) != null) {
                ForgeRecipe recipe = ForgeRecipes.getRecipeForInputs(stacks);
                for (EntityItem entity : entities) {
                    if (recipe.input1.apply(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()))) {
                        entity.getItem().shrink(1);
                    }
                    if (recipe.input2.apply(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()))) {
                        entity.getItem().shrink(1);
                    }
                    if (recipe.input3.apply(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()))) {
                        entity.getItem().shrink(1);
                    }
                    if (recipe.input4.apply(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()))) {
                        entity.getItem().shrink(1);
                    }
                    if (recipe.input5.apply(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()))) {
                        entity.getItem().shrink(1);
                    }
                }
                EntityItem item = new EntityItem(nexus.world, nexus.posX, nexus.posY, nexus.posZ, recipe.output);
                nexus.world.spawnEntity(item);
                nexus.world.playSound(null, nexus.posX, nexus.posY, nexus.posZ, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.AMBIENT, 1, 0.9F);
            }
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, 14762496, 5, 60, 0, false, false);
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, Aspects.magan.getColor(), 3, 60, 0, false, false);
        List<Entity> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(4), null);
        for (Entity entity : entities) {
            entity.motionX = (nexus.posX - entity.posX) / 20;
            entity.motionY = (nexus.posY - entity.posY) / 20;
            entity.motionZ = (nexus.posZ - entity.posZ) / 20;
            Elementaristics.proxy.generateGenericParticles(entity, 14762496, 1, 10, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.light.getColor(), 1, 10, 0, false, false);

        }
    }
}
