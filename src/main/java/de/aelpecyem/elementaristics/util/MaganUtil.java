package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MaganUtil {
    public static boolean drainMaganFromClosestPlayer(BlockPos posToDrainFrom, World world, int range, float amount, int stuntTime, boolean particles) {
        EntityPlayer player = world.getClosestPlayer(posToDrainFrom.getX(), posToDrainFrom.getY(), posToDrainFrom.getZ(), range, false);
        if (player != null) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                caps.stuntMagan(stuntTime);
                if (caps.getMagan() >= amount) {
                    if (particles) {
                        Elementaristics.proxy.generateGenericParticles(player, SoulInit.getSoulFromId(caps.getSoulId()).getParticleColor(), 0.2F, 50, -0.01F, true, true);
                    }
                }
                return caps.drainMagan(amount);
            }
        }
        return false;
    }

    public static boolean drainMaganFromPlayer(EntityPlayer player, float amount, int stuntTime, boolean particles) {
        if (player != null) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                caps.stuntMagan(stuntTime);
                if (caps.getMagan() >= amount) {
                    if (particles) {
                        Elementaristics.proxy.generateGenericParticles(player, SoulInit.getSoulFromId(caps.getSoulId()).getParticleColor(), 0.5F, 50, -0.01F, true, true);
                    }
                }
                return caps.drainMagan(amount);
            }
        }
        return false;
    }

    public static boolean drainMaganFromCultist(EntityCultist cultist, float amount, int stuntTime, boolean particles) {
        if (cultist != null) {
            cultist.setStuntTime(stuntTime);
            if (cultist.getMagan() >= amount) {
                if (particles) {
                    Elementaristics.proxy.generateGenericParticles(cultist, cultist.getAspect().getColor(), 2F, 50, -0.01F, true, true);
                }
            }
            return cultist.drainMagan(amount);
        }
        return false;
    }
}
