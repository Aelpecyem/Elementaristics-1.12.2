package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.chunk.ChunkCapProvider;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;
import java.util.List;

public class DeityDancer extends DeitySupplyEffectBase {
    public DeityDancer() {
        super(TimeUtil.getTickTimeStartForHour(19), new ResourceLocation(Elementaristics.MODID, "deity_dancer"), Aspects.soul, 11700900);
    }
    @Override
    public void supplyEffect(TileEntityDeityShrine te) {
        Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getPos());
        if (chunk != null && chunk.hasCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null) && !te.getWorld().isRemote){
            te.unusedInt = chunk.getCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null).getInfluenceId();
        }
        if (te.getAltarBound() != null) {
            TileEntityAltar tile = te.getAltarBound();
            tile.addAspect(getAspect());
            if (SoulInit.getSoulFromIdWithNull(te.unusedInt) != null && te.storage.extractIfPossible(3))
            tile.addSoul(SoulInit.getSoulFromIdWithNull(te.unusedInt));
        }
    }

    @Override
    public void passiveParticles(TileEntityDeityShrine te) {
        if (SoulInit.getSoulFromIdWithNull(te.unusedInt) != null && te.getWorld().getWorldTime() % 10 == 0 && te.getWorld().isRemote) {
            double motionX = te.getWorld().rand.nextGaussian() * 0.002D;
            double motionY = te.getWorld().rand.nextGaussian() * 0.002D;
            double motionZ = te.getWorld().rand.nextGaussian() * 0.002D;
            System.out.println(te.unusedInt);
            Elementaristics.proxy.generateGenericParticles(te.getWorld(), te.getPos().getX() + te.getWorld().rand.nextFloat(), te.getPos().getY() + te.getWorld().rand.nextFloat(), te.getPos().getZ() + te.getWorld().rand.nextFloat(), motionX, motionY, motionZ, SoulInit.getSoulFromId(te.unusedInt).getParticleColor(), 1, 120, 0, false, false);
        }
        super.passiveParticles(te);
    }
}

