package de.aelpecyem.elementaristics.misc.rites.crafting;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
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

import java.util.List;

public class RiteChaos extends RiteBase {

    public RiteChaos() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_chaos"), 200, 0.3F, 8, Aspects.chaos);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
        for (Entity entity : entities) {
            if (entity instanceof EntityItem) {
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.aether.getColor(), 1, 10, 0, false, true);
                if (!world.isRemote) {
                    ItemStack result;
                    if (EntropizerRecipes.getRecipeForInput(((EntityItem) entity).getItem()) != null) {
                        //((EntityItem) entity).getItem().shrink(4);
                        result = EntropizerRecipes.getRecipeForInput(((EntityItem) entity).getItem()).output;
                        if (((EntityItem) entity).getItem().getCount() >= 4){
                            ((EntityItem) entity).getItem().shrink(4);
                            result.setCount(4);
                        }else{
                            result.setCount(((EntityItem) entity).getItem().getCount());
                            ((EntityItem) entity).getItem().shrink(((EntityItem) entity).getItem().getCount());
                        }
                    } else {
                        result = ModItems.chaotic_matter.getDefaultInstance();
                        if (((EntityItem) entity).getItem().getCount() >= 4){
                            ((EntityItem) entity).getItem().shrink(4);
                            result.setCount(4);
                        }else{
                            result.setCount(((EntityItem) entity).getItem().getCount());
                            ((EntityItem) entity).getItem().shrink(((EntityItem) entity).getItem().getCount());
                        }
                    }
                    world.spawnEntity(new EntityItem(world, entity.posX, entity.posY, entity.posZ, result));
                    world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 0.8F, 0.9F);
                }
            }
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, Aspects.chaos.getColor(), 3, 60, 0, false, false);
        List<Entity> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(altarPos.getX() - 4, altarPos.getY() - 2, altarPos.getZ() - 4, altarPos.getX() + 4, altarPos.getY() + 3, altarPos.getZ() + 4), null);
            for (Entity entity : entities) {
                entity.motionX = (altarPos.getX() + 0.5 - entity.posX) / 20;
                entity.motionY = (altarPos.getY() + 1.5 - entity.posY) / 20;
                entity.motionZ = (altarPos.getZ() + 0.5 - entity.posZ) / 20;
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, false);

        }
    }
}
