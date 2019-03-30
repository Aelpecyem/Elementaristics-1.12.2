package de.aelpecyem.elementaristics.misc.rites;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RiteForging extends RiteBase {

    public RiteForging() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_forging"), 200, 0.35F, 8, Aspects.fire);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
        
        for (Entity entity : entities) {
            if (entity instanceof EntityItem) {
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
                Elementaristics.proxy.generateGenericParticles(entity, Aspects.aether.getColor(), 1, 10, 0, false, true);
                if (!world.isRemote) {
                    ItemStack result = ItemStack.EMPTY;


                    world.spawnEntity(new EntityItem(world, entity.posX, entity.posY, entity.posZ, result));
                }
            }
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, Aspects.chaos.getColor(), 3, 60, 0, false, false);
            List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(altarPos.getX() - 2, altarPos.getY() - 1, altarPos.getZ() - 2, altarPos.getX() + 2, altarPos.getY() + 2, altarPos.getZ() + 2), null);
            for (Entity entity : entities) {
                if (entity instanceof EntityItem) {
                    Elementaristics.proxy.generateGenericParticles(entity, Aspects.chaos.getColor(), 1, 10, 0, false, true);
                    Elementaristics.proxy.generateGenericParticles(entity, Aspects.aether.getColor(), 1, 10, 0, false, true);
                }
            }
        }
    }
}
