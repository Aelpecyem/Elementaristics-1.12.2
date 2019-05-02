package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;

public class SoulCapMagan extends SoulCap {
    public SoulCapMagan() {
        super(SoulInit.soulMagan);
    }

    @Override
    public void onHitting(LivingHurtEvent event, EntityPlayer attacker, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (attacker.world.rand.nextInt(20) + cap.getPlayerAscensionStage() >= 19) {
                event.getEntityLiving().attackEntityFrom(DamageSource.MAGIC, 2);
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
            if (cap.getMaxMagan() < 130) {
                cap.setMaxMagan(130);
            }
            if (cap.getPlayerAscensionStage() >= 1) {
                if (cap.getMaxMagan() < 140) {
                    cap.setMaxMagan(140);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (cap.getMaxMagan() < 180) {
                    cap.setMaxMagan(180);
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
