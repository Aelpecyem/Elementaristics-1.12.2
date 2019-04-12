package de.aelpecyem.elementaristics.misc.rites;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RiteForging extends RiteBase {

    public RiteForging() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_forging"), 200, 0.35F, 8, Aspects.fire);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
        List<ItemStack> stacks = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof EntityItem) {
                stacks.add(((EntityItem) entity).getItem());
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.fire.getColor(), 1, 10, 0, false, true);
            }
        }
        if (!world.isRemote) {
            if (ForgeRecipes.getRecipeForInputs(stacks) != null) {
                ForgeRecipe recipe = ForgeRecipes.getRecipeForInputs(stacks);

                for (Entity entity : entities) {
                    if (entity instanceof EntityItem) {
                        if (recipe.input1.apply(((EntityItem) entity).getItem())) {
                            ((EntityItem) entity).getItem().shrink(recipe.input1.getMatchingStacks()[0].getCount());
                        } else if (recipe.input2.apply(((EntityItem) entity).getItem())) {
                            ((EntityItem) entity).getItem().shrink(recipe.input2.getMatchingStacks()[0].getCount());
                        } else if (recipe.input3.apply(((EntityItem) entity).getItem())) {
                            ((EntityItem) entity).getItem().shrink(recipe.input3.getMatchingStacks()[0].getCount());
                        } else if (recipe.input4.apply(((EntityItem) entity).getItem())) {
                            ((EntityItem) entity).getItem().shrink(recipe.input4.getMatchingStacks()[0].getCount());
                        } else if (recipe.input5.apply(((EntityItem) entity).getItem())) {
                            ((EntityItem) entity).getItem().shrink(recipe.input5.getMatchingStacks()[0].getCount());
                        }
                    }
                }
                EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, recipe.output);
                world.spawnEntity(item);
            }

        }

    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1.5F, altarPos.getZ() + 0.5F, 14762496, 5, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1.5F, altarPos.getZ() + 0.5F, Aspects.magan.getColor(), 3, 60, 0, false, false);
            List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(altarPos.getX() - 4, altarPos.getY() - 2, altarPos.getZ() - 4, altarPos.getX() + 4, altarPos.getY() + 3, altarPos.getZ() + 4), null);
            for (Entity entity : entities) {
                if (entity instanceof EntityItem) {
                    entity.motionX = (altarPos.getX() + 0.5 - entity.posX) / 20;
                    entity.motionY = (altarPos.getY() + 1.5 - entity.posY) / 20;
                    entity.motionZ = (altarPos.getZ()+ 0.5 - entity.posZ) / 20;
                    Elementaristics.proxy.generateGenericParticles(entity, 14762496, 1, 10, 0, false, false);
                    Elementaristics.proxy.generateGenericParticles(entity, Aspects.light.getColor(), 1, 10, 0, false, false);
                }

            }

    }
}
