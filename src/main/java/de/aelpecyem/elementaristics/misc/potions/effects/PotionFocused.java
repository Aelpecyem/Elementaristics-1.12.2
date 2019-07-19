package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class PotionFocused extends PotionBase {
    public PotionFocused() {
        super("focused", false, 7733416, 9);
        //  MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof EntityPlayer) {
            if (((EntityPlayer) entityLivingBaseIn).getSleepTimer() > 97) {
                if (amplifier == 0 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.contentment))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.contentment);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 1, true, true));
                    if (((EntityPlayer) entityLivingBaseIn).world.isRemote)
                    ((EntityPlayer) entityLivingBaseIn).sendStatusMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.meditation_to_dread.name")), false);


                } else if (amplifier == 1 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.dread))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.dread);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 2, true, true));
                    if (((EntityPlayer) entityLivingBaseIn).world.isRemote)
                    ((EntityPlayer) entityLivingBaseIn).sendStatusMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.meditation_to_fear.name")), false);

                } else if (amplifier == 2 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.fear))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.fear);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 3, true, true));
                    if (((EntityPlayer) entityLivingBaseIn).world.isRemote)
                    ((EntityPlayer) entityLivingBaseIn).sendStatusMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.meditation_to_silence.name")), false);

                } else if (amplifier == 3 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.silence))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.silence);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 4, true, true));
                    if (((EntityPlayer) entityLivingBaseIn).world.isRemote)
                    ((EntityPlayer) entityLivingBaseIn).sendStatusMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.meditation_to_laughter.name")), false);

                } else if (amplifier == 4 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.laughter))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.laughter);
                    if (entityLivingBaseIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        IPlayerCapabilities cap = entityLivingBaseIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                        if (cap.getPlayerAscensionStage() < 2) {
                            cap.setPlayerAscensionStage(2);
                            entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 500, 5));
                        }
                        if (!((EntityPlayer) entityLivingBaseIn).world.isRemote)
                            PacketHandler.sendTo((EntityPlayer) entityLivingBaseIn, new PacketMessage("message.meditation_ascension.name"));
                       }

                }
            }

            super.performEffect(entityLivingBaseIn, amplifier);
        }


    }
}
