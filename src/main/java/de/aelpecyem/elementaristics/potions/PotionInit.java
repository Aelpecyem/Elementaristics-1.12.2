package de.aelpecyem.elementaristics.potions;

import de.aelpecyem.elementaristics.potions.effects.PotionIntoxicated;
import de.aelpecyem.elementaristics.potions.effects.PotionTrance;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionInit {
    public static final PotionIntoxicated potionIntoxicated = new PotionIntoxicated();
    public static final PotionTrance potionTrance = new PotionTrance();

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(potionIntoxicated);
        event.getRegistry().register(potionTrance);
    }
}
