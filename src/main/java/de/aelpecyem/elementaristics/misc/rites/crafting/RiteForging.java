package de.aelpecyem.elementaristics.misc.rites.crafting;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
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
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
        List<ItemStack> stacks = new ArrayList<>();
        for (EntityItem entity : entities) {
            for (int i = 0; i < entity.getItem().getCount(); i++)
                stacks.add(new ItemStack(entity.getItem().getItem(), 1, entity.getItem().getMetadata()));
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.fire.getColor(), 1, 10, 0, false, true);
        }
        if (!world.isRemote) {
            if (ForgeRecipes.getRecipeForInputs(stacks) != null) {
                ForgeRecipe recipe = ForgeRecipes.getRecipeForInputs(stacks);
                //test for slots, not in numbers
                //try shrinking the valid stacks first; if that does not work, check if the entity's item (one) matches, then shrink the entity's item by one
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
                EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, recipe.output);
                world.spawnEntity(item);
                world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.AMBIENT, 1, 0.9F);
            }
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1.5F, altarPos.getZ() + 0.5F, 14762496, 5, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1.5F, altarPos.getZ() + 0.5F, Aspects.magan.getColor(), 3, 60, 0, false, false);
        List<Entity> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(altarPos.getX() - 4, altarPos.getY() - 2, altarPos.getZ() - 4, altarPos.getX() + 4, altarPos.getY() + 3, altarPos.getZ() + 4), null);
            for (Entity entity : entities) {
                    entity.motionX = (altarPos.getX() + 0.5 - entity.posX) / 20;
                    entity.motionY = (altarPos.getY() + 1.5 - entity.posY) / 20;
                    entity.motionZ = (altarPos.getZ()+ 0.5 - entity.posZ) / 20;
                    Elementaristics.proxy.generateGenericParticles(entity, 14762496, 1, 10, 0, false, false);
                    Elementaristics.proxy.generateGenericParticles(entity, Aspects.light.getColor(), 1, 10, 0, false, false);

            }
    }
}
