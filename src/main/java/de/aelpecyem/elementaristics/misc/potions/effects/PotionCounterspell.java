package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class PotionCounterspell extends PotionBase {
    public PotionCounterspell() {
        super("counterspell", false, 7733416, 13);
        //  MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @SubscribeEvent
    public void hitEntity(TickEvent.PlayerTickEvent event) {
        //play some sound effect idk
        if (event.player.getActivePotionEffects().contains(event.player.getActivePotionEffect(this))) {
            AxisAlignedBB counterBox = new AxisAlignedBB(event.player.posX + event.player.width / 2 + 10, event.player.posY + event.player.height / 2 + 5, event.player.posZ + event.player.width / 2 + 10,
                    event.player.posX + event.player.width / 2 - 10, event.player.posY + event.player.height / 2 - 5, event.player.posZ + event.player.width / 2 - 10);
            List<EntitySpellProjectile> projectiles = event.player.world.getEntitiesWithinAABB(EntitySpellProjectile.class, counterBox);
            if (!projectiles.isEmpty()) {
                for (EntitySpellProjectile projectile : projectiles) {
                    if (!(projectile.getCaster() == event.player)) {
                        projectile.motionX = -projectile.motionX;
                        projectile.motionY = -projectile.motionY;
                        projectile.motionZ = -projectile.motionZ;
                        projectile.setCaster(event.player);
                        Elementaristics.proxy.generateGenericParticles(event.player, SoulInit.soulMana.getParticleColor(), 3, 100, 0, false, true);
                        Elementaristics.proxy.generateGenericParticles(projectile, SoulInit.soulMana.getParticleColor(), 3, 200, 0, false, true);
                        event.player.removePotionEffect(this);
                    }
                }
            }

        }
    }
}
