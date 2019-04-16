package de.aelpecyem.elementaristics.misc.potions;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.potions.effects.PotionIntoxicated;
import de.aelpecyem.elementaristics.misc.potions.effects.PotionTrance;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.*;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Elementaristics.MODID)
public class PotionInit {
    public static List<PotionEmotion> emotions = new ArrayList<>();
    public static final PotionIntoxicated potionIntoxicated = new PotionIntoxicated();
    public static final PotionTrance potionTrance = new PotionTrance();

    public static PotionEmotionContentment contentment = new PotionEmotionContentment(); //lower regen
    public static PotionEmotionDread dread = new PotionEmotionDread(); //higher regen
    public static PotionEmotionEcstasy ecstasy = new PotionEmotionEcstasy(); //little regen / no regen
    public static PotionEmotionFear fear = new PotionEmotionFear();  //no regen
    public static PotionEmotionLaughter laughter = new PotionEmotionLaughter(); //strongest regen
    public static PotionEmotionSilence silence = new PotionEmotionSilence(); //no regen at all

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(potionIntoxicated);
        event.getRegistry().register(potionTrance);

        event.getRegistry().register(contentment);
        event.getRegistry().register(dread);
        event.getRegistry().register(ecstasy);
        event.getRegistry().register(fear);
        event.getRegistry().register(laughter);
        event.getRegistry().register(silence);
    }
}
