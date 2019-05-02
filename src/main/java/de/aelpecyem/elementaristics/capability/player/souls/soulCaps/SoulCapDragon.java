package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SoulCapDragon extends SoulCap {
    public SoulCapDragon() {
        super(SoulInit.soulDragon);
    }


    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        super.normalize(player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (cap.getMaxMagan() < 150) {
                cap.setMaxMagan(150);
            }
            if (cap.getPlayerAscensionStage() >= 1) {
                if (cap.getMaxMagan() < 160) {
                    cap.setMaxMagan(160);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (cap.getMaxMagan() < 170) {
                    cap.setMaxMagan(170);
                }
            }
            if (cap.getPlayerAscensionStage() >= 5) {
                if (cap.getMaxMagan() < 180) {
                    cap.setMaxMagan(180);
                }
            }
        }
    }
}
