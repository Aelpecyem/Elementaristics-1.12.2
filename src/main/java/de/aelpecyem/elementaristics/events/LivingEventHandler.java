package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class LivingEventHandler {
    public static final String LAST_DMG_STRING = "lastDmg";
    @SubscribeEvent
    public void damageTaken(LivingHurtEvent event) {
        event.getEntityLiving().getEntityData().setInteger(LAST_DMG_STRING, (int) Math.ceil(event.getAmount()));
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.world.isRemote) {
            CustomAdvancements.Advancements.DUMMY.trigger((EntityPlayerMP) event.player, "login");
        }
    }
}
