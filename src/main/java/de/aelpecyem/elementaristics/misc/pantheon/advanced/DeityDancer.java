package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.chunk.ChunkCapProvider;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;

public class DeityDancer extends DeitySupplyEffectBase {
    public DeityDancer() {
        super(TimeUtil.getTickTimeStartForHour(19), new ResourceLocation(Elementaristics.MODID, "deity_dancer"), Aspects.soul, 11700900);
    }
    @Override
    public void supplyEffect(TileEntityDeityShrine te, boolean isStatue) {
        Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getPos());
        if (chunk != null && chunk.hasCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null) && !te.getWorld().isRemote){
            te.unusedInt = chunk.getCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null).getInfluenceId();
        }
        if (te.getNexusBound() != null) {
            EntityDimensionalNexus nexus = te.getNexusBound();
            nexus.addAspect(getAspect());
            if (SoulInit.getSoulFromIdWithNull(te.unusedInt) != null && te.storage.extractIfPossible(isStatue ? 3 : 10))
                nexus.addSoul(SoulInit.getSoulFromIdWithNull(te.unusedInt));
        }
    }

    @Override
    public void passiveParticles(TileEntityDeityShrine te) {
        if (SoulInit.getSoulFromIdWithNull(te.unusedInt) != null && te.getWorld().getWorldTime() % 10 == 0 && te.getWorld().isRemote) {
            double motionX = te.getWorld().rand.nextGaussian() * 0.002D;
            double motionY = te.getWorld().rand.nextGaussian() * 0.002D;
            double motionZ = te.getWorld().rand.nextGaussian() * 0.002D;
            Elementaristics.proxy.generateGenericParticles(te.getWorld(), te.getPos().getX() + te.getWorld().rand.nextFloat(), te.getPos().getY() + te.getWorld().rand.nextFloat(), te.getPos().getZ() + te.getWorld().rand.nextFloat(), motionX, motionY, motionZ, SoulInit.getSoulFromId(te.unusedInt).getParticleColor(), 1, 120, 0, false, false);
        }
        super.passiveParticles(te);
    }
}

