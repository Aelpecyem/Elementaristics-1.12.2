package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RiteDay extends RiteBase {

    public RiteDay() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_day"), 400, 0.5F, 12, Aspects.light, Aspects.fire);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        List<EntitySheep> targets = world.getEntitiesWithinAABB(EntitySheep.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 2F, pos.getY() - 4F, pos.getZ() - 2F),
                new BlockPos(pos.getX() + 2F, pos.getY() + 4F, pos.getZ() + 2F)));
        boolean flag = false;
        for (EntitySheep sheepAffected : targets) {
            if (flag)
                break;
            if (sheepAffected.getFleeceColor() == EnumDyeColor.WHITE) {
                sheepAffected.setDropItemsWhenDead(false);
                sheepAffected.attackEntityFrom(DamageSource.GENERIC, 20);
                flag = true;
            }
        }
        if (flag)
            world.setWorldTime(6000);
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F + (world.rand.nextGaussian() / 2), altarPos.getY() + 1F, altarPos.getZ() + 0.5F + (world.rand.nextGaussian() / 2), 0, 0.1F, 0, Aspects.light.getColor(), 3, 180, 0, false, true);
        }
    }
}
