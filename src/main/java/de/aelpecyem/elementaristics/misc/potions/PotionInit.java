package de.aelpecyem.elementaristics.misc.potions;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.potions.effects.*;
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
    public static final PotionFocused potionFocused = new PotionFocused();

    public static final PotionFormGaseous potionFormGaseous = new PotionFormGaseous();
    public static final PotionCrystalProtection potionCrystalProtection = new PotionCrystalProtection();
    public static final PotionFireballCharge potionFireballCharge = new PotionFireballCharge();
    public static final PotionCounterspell potionCounterspell = new PotionCounterspell();

    public static final PotionHealthShare potionHealthShare = new PotionHealthShare();

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
        event.getRegistry().register(potionFocused);

        event.getRegistry().register(potionFormGaseous);
        event.getRegistry().register(potionCrystalProtection);
        event.getRegistry().register(potionFireballCharge);
        event.getRegistry().register(potionCounterspell);

        event.getRegistry().register(potionHealthShare);

        event.getRegistry().register(contentment);
        event.getRegistry().register(dread);
        event.getRegistry().register(ecstasy);
        event.getRegistry().register(fear);
        event.getRegistry().register(laughter);
        event.getRegistry().register(silence);
    }
}
