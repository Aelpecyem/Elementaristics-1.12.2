package de.aelpecyem.elementaristics.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static KeyBinding spellUp;
    public static KeyBinding spellDown;

    public static void register() {
        spellUp = new KeyBinding("key.spell_up", Keyboard.KEY_UP, "category.elementaristics");
        spellDown = new KeyBinding("key.spell_down", Keyboard.KEY_DOWN, "category.elementaristics");
        ClientRegistry.registerKeyBinding(spellUp);
        ClientRegistry.registerKeyBinding(spellDown);
    }
}
