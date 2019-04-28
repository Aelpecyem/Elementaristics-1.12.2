package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ConcurrentModificationException;

public class PotionFireballCharge extends PotionBase {
    public PotionFireballCharge() {
        super("fireball_charge", false, 39381, 12);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public void hitEntity(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.getActivePotionEffects().contains(event.player.getActivePotionEffect(this))) {
            if (event.player.world.getEntitiesWithinAABB(EntityLivingBase.class, event.player.getEntityBoundingBox().grow(0.5)).size() > 1) {
                event.player.setEntityInvulnerable(true);
                Explosion xplosn = new Explosion(event.player.world, event.player, event.player.posX, event.player.posY,
                        event.player.posZ, 2.5F, false, false);
                for (int i = 0; i < 5; i++)
                    Elementaristics.proxy.generateGenericParticles(event.player.world, event.player.posX + event.player.width / 2,
                            event.player.posY + event.player.height / 2, event.player.posZ + event.player.width / 2,
                            event.player.world.rand.nextGaussian(),
                            event.player.world.rand.nextGaussian(),
                            event.player.world.rand.nextGaussian(), Aspects.fire.getColor(), 2, 60, 0, false, true);
                try {
                    xplosn.doExplosionA();
                    xplosn.doExplosionB(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                event.player.setEntityInvulnerable(false);
                knockBack(event.player, 1, event.player.getLookVec().x, event.player.getLookVec().z);
                event.player.removePotionEffect(this);
            }
        }
    }

    public void knockBack(EntityLivingBase livingBase, float strength, double xRatio, double zRatio) {
        if (livingBase.world.rand.nextDouble() >= livingBase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()) {
            livingBase.isAirBorne = true;
            float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
            livingBase.motionX /= 2.0D;
            livingBase.motionZ /= 2.0D;
            livingBase.motionX -= xRatio / (double) f * (double) strength;
            livingBase.motionZ -= zRatio / (double) f * (double) strength;

            if (livingBase.onGround) {
                livingBase.motionY /= 2.0D;
                livingBase.motionY += (double) strength;

                if (livingBase.motionY > 0.4000000059604645D) {
                    livingBase.motionY = 0.4000000059604645D;
                }
            }
        }
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(this))) {
            if (entityLivingBaseIn.isSneaking()) {
                entityLivingBaseIn.removePotionEffect(this);
            }
        }
        entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 2, 0, false, false));
        entityLivingBaseIn.motionY = 0;
        entityLivingBaseIn.fallDistance = 0;

        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2 + 0.3, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2, Aspects.fire.getColor(), 4, 20, 0, true, false);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2 + 0.3, Aspects.fire.getColor(), 4, 20, 0, true, false);

        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2 - 0.3, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2, Aspects.fire.getColor(), 4, 20, 0, true, false);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2 - 0.3, Aspects.fire.getColor(), 4, 20, 0, true, false);

        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2 - 0.3, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2, Aspects.fire.getColor(), 4, 20, 0, true, false);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2 + 0.3, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2, Aspects.fire.getColor(), 4, 20, 0, true, false);

        //    Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn.world, entityLivingBaseIn.posX + entityLivingBaseIn.width / 2 + x, entityLivingBaseIn.posY + entityLivingBaseIn.height / 2 + y, entityLivingBaseIn.posZ + entityLivingBaseIn.width / 2 + z, Aspects.magan.getColor(), 3, 20, 0, true, false); }

        float yaw = entityLivingBaseIn.rotationYaw;
        float pitch = entityLivingBaseIn.rotationPitch;
        float f = 3.5F;// + amplifier * 0.2F + (entityLivingBaseIn instanceof EntityPlayer ? ((EntityPlayer) entityLivingBaseIn).capabilities.getWalkSpeed() : 0);
        double motionX = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
        double motionZ = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
        double motionY = (double) (-MathHelper.sin((pitch) / 180.0F * (float) Math.PI) * f);
        entityLivingBaseIn.setVelocity(motionX, motionY, motionZ);

        super.performEffect(entityLivingBaseIn, amplifier);
    }

}
