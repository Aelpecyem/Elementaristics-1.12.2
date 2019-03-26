package de.aelpecyem.elementaristics.misc.potions;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.potions.effects.PotionIntoxicated;
import de.aelpecyem.elementaristics.misc.potions.effects.PotionTrance;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Elementaristics.MODID)
public class PotionInit {
    public static final PotionIntoxicated potionIntoxicated = new PotionIntoxicated();
    public static final PotionTrance potionTrance = new PotionTrance();

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(potionIntoxicated);
        event.getRegistry().register(potionTrance);
    }
}
