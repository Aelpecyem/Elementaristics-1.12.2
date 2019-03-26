package de.aelpecyem.elementaristics.misc.rites;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
import de.aelpecyem.elementaristics.recipe.base.EntropizerRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RiteChaos extends RiteBase {

    public RiteChaos() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_chaos"), 200, 0.49F, 8, ElementInit.chaos);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
        for (Entity entity : entities) {
            if (entity instanceof EntityItem) {
                Elementaristics.proxy.generateGenericParticles(entity, ElementInit.chaos.getColor(), 1, 10, 0, false, true);
                Elementaristics.proxy.generateGenericParticles(entity, ElementInit.aether.getColor(), 1, 10, 0, false, true);
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
                }
            }
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, ElementInit.chaos.getColor(), 3, 60, 0, false, false);
            List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(altarPos.getX() - 2, altarPos.getY() - 1, altarPos.getZ() - 2, altarPos.getX() + 2, altarPos.getY() + 2, altarPos.getZ() + 2), null);
            for (Entity entity : entities) {
                if (entity instanceof EntityItem) {
                    Elementaristics.proxy.generateGenericParticles(entity, ElementInit.chaos.getColor(), 1, 10, 0, false, true);
                    Elementaristics.proxy.generateGenericParticles(entity, ElementInit.aether.getColor(), 1, 10, 0, false, true);
                }
            }
        }
    }
}
