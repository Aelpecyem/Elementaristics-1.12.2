package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCapMana extends SoulCap {
    public SoulCapMana() {
        super(SoulInit.soulMana);
    }

    @Override
    public void onHitting(LivingHurtEvent event, EntityPlayer attacker, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (attacker.world.rand.nextInt(20) + cap.getPlayerAscensionStage() >= 19) {
                event.getEntityLiving().attackEntityFrom(DamageSource.MAGIC, 3);
            }
        }
        super.onHitting(event, attacker, cap);
    }


    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        super.normalize(player, cap);
    }


    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (cap.getMaxMagan() < 170) {
                cap.setMaxMagan(170);
            }
            if (cap.getPlayerAscensionStage() >= 1) {
                if (cap.getMaxMagan() < 190) {
                    cap.setMaxMagan(190);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (cap.getMaxMagan() < 210) {
                    cap.setMaxMagan(210);
                }
            }
            if (cap.getPlayerAscensionStage() >= 5) {
                if (cap.getMaxMagan() < 250) {
                    cap.setMaxMagan(250);
                }
            }
        }
    }
}
