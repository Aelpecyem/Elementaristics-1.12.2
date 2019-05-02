package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCapAncient extends SoulCap {
    public SoulCapAncient() {
        super(SoulInit.soulAncient);
    }

    @Override
    public void onHitting(LivingHurtEvent event, EntityPlayer attacker, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (attacker.world.rand.nextInt(20) + cap.getPlayerAscensionStage() >= 19) {
                event.getEntityLiving().attackEntityFrom(Elementaristics.DAMAGE_PSYCHIC, 2);
            }
        }
        super.onHitting(event, attacker, cap);
    }

    @Override
    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.getPlayerAscensionStage() > 0) {
            if (player.world.rand.nextInt(1000) <= cap.getPlayerAscensionStage()) {
                player.heal(5);

            }
        }
        super.onTickEvent(event, player, cap);
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
                if (cap.getMaxMagan() < 180) {
                    cap.setMaxMagan(180);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (cap.getMaxMagan() < 190) {
                    cap.setMaxMagan(190);
                }
            }
            if (cap.getPlayerAscensionStage() >= 5) {
                if (cap.getMaxMagan() < 200) {
                    cap.setMaxMagan(200);
                }
            }
        }
    }
}
