package de.aelpecyem.elementaristics.misc.rites.crafting;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.util.List;

public class RiteChaos extends RiteBase {

    public RiteChaos() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_chaos"), 200, 0.3F, 8, Aspects.chaos);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityItem> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2));
        for (EntityItem entity : entities) {
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.aether.getColor(), 1, 10, 0, false, true);
            if (!nexus.world.isRemote) {
                ItemStack result;
                if (EntropizerRecipes.getRecipeForInput(entity.getItem()) != null) {
                    result = EntropizerRecipes.getRecipeForInput(((EntityItem) entity).getItem()).output;
                    if (entity.getItem().getCount() >= 4) {
                        entity.getItem().shrink(4);
                        result.setCount(4);
                    } else {
                        result.setCount(entity.getItem().getCount());
                        entity.getItem().shrink(entity.getItem().getCount());
                    }
                } else {
                    result = new ItemStack(ModItems.chaotic_matter);
                    if (entity.getItem().getCount() >= 4) {
                        entity.getItem().shrink(4);
                        result.setCount(4);
                    } else {
                        result.setCount(entity.getItem().getCount());
                        entity.getItem().shrink(entity.getItem().getCount());
                    }
                }
                nexus.world.spawnEntity(new EntityItem(nexus.world, entity.posX, entity.posY, entity.posZ, result));
            }
        }
    }

    @Override
    public void playSound(EntityDimensionalNexus nexus) {
        nexus.world.playSound(null, nexus.posX, nexus.posY, nexus.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 0.8F, 0.9F);
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY + 0.5F, nexus.posZ, Aspects.chaos.getColor(), 3, 60, 0, false, false);
        List<Entity> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(4), null);
        for (Entity entity : entities) {
            nexus.suckInEntity(entity, entity.height / 2);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 40, 0, false, false);

        }
    }
}
