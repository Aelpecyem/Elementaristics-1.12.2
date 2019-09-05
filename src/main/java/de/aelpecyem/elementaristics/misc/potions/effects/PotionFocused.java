package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.util.CapabilityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

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
            int sleepTimer = ObfuscationReflectionHelper.getPrivateValue(EntityPlayer.class, (EntityPlayer) entityLivingBaseIn, "field_71076_b", "sleepTimer");
            if (sleepTimer > 97) {
                if (amplifier == 0 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.contentment))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.contentment);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 1, true, true));
                    if (!((EntityPlayer) entityLivingBaseIn).world.isRemote)
                        PacketHandler.sendTo((EntityPlayer) entityLivingBaseIn, new PacketMessage("message.meditation_to_dread.name"));

                } else if (amplifier == 1 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.dread))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.dread);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 2, true, true));
                    if (!((EntityPlayer) entityLivingBaseIn).world.isRemote)
                        PacketHandler.sendTo((EntityPlayer) entityLivingBaseIn, new PacketMessage("message.meditation_to_fear.name")); //todo, continue replacing the old ones with messages, add gold formatting to the messages
                } else if (amplifier == 2 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.fear))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.fear);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 3, true, true));
                    if (!((EntityPlayer) entityLivingBaseIn).world.isRemote)
                        PacketHandler.sendTo((EntityPlayer) entityLivingBaseIn, new PacketMessage("message.meditation_to_silence.name")); //todo, continue replacing the old ones with messages, add gold formatting to the messages
                } else if (amplifier == 3 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.silence))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.silence);
                    entityLivingBaseIn.addPotionEffect(new PotionEffect(this, 24000, 4, true, true));
                    if (!((EntityPlayer) entityLivingBaseIn).world.isRemote)
                        PacketHandler.sendTo((EntityPlayer) entityLivingBaseIn, new PacketMessage("message.meditation_to_laughter.name"));

                } else if (amplifier == 4 && entityLivingBaseIn.getActivePotionEffects().contains(entityLivingBaseIn.getActivePotionEffect(PotionInit.laughter))) {
                    entityLivingBaseIn.removePotionEffect(this);
                    entityLivingBaseIn.removePotionEffect(PotionInit.laughter);
                    if (entityLivingBaseIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        IPlayerCapabilities cap = entityLivingBaseIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                        if (cap.getPlayerAscensionStage() < 2) {
                            CapabilityUtil.ascend(2, entityLivingBaseIn);
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
