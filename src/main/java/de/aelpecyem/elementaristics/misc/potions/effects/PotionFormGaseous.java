package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionFormGaseous extends PotionBase {
    public PotionFormGaseous() {
        super("gaseous", false, 39381, 10);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public void hitEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityLivingBase && event.getEntityLiving().getActivePotionEffects().contains(event.getEntityLiving().getActivePotionEffect(this))) {
            event.getTarget().attackEntityFrom(Elementaristics.DAMAGE_AIR, 1);
            event.setCanceled(true);
        }
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(this))) {
            if (entityLivingBaseIn.isSneaking()) {
                entityLivingBaseIn.removePotionEffect(this);
            }
        }
        entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20, 0, false, false));
        entityLivingBaseIn.motionY = 0;
        entityLivingBaseIn.fallDistance = 0;
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, Aspects.air.getColor(), 3, 30, 0, true, false);
        float yaw = entityLivingBaseIn.rotationYaw;
        float pitch = entityLivingBaseIn.rotationPitch;
        float f = 0.4F + amplifier * 0.2F + (entityLivingBaseIn instanceof EntityPlayer ? ((EntityPlayer) entityLivingBaseIn).capabilities.getWalkSpeed() : 0);
        double motionX = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
        double motionZ = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
        double motionY = (double) (-MathHelper.sin((pitch) / 180.0F * (float) Math.PI) * f);
        entityLivingBaseIn.setVelocity(motionX, motionY, motionZ);

        super.performEffect(entityLivingBaseIn, amplifier);
    }

}
