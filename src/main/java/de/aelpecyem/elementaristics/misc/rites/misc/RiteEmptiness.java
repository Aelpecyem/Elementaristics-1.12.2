package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RiteEmptiness extends RiteBase {

    public RiteEmptiness() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_emptiness"), 400, 0.2F, 6, Aspects.vacuum);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
        for (EntityPlayer playerAffected : targets) {
            playerAffected.removePotionEffect(PotionInit.laughter);
            playerAffected.removePotionEffect(PotionInit.silence);
            playerAffected.removePotionEffect(PotionInit.dread);
            playerAffected.removePotionEffect(PotionInit.contentment);
            playerAffected.removePotionEffect(PotionInit.ecstasy);
            playerAffected.removePotionEffect(PotionInit.fear);
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, Aspects.vacuum.getColor(), 3, 60, 0, false, false);
            for (EntityPlayer player : players) {
                Elementaristics.proxy.generateGenericParticles(player, Aspects.vacuum.getColor(), 0.5F, 100, 0, false, true);
            }
        }
    }
}
