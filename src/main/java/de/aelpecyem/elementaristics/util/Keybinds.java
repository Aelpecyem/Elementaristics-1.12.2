package de.aelpecyem.elementaristics.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static KeyBinding elementaristicsUp;
    public static KeyBinding elementaristicsDown;
    public static KeyBinding elementaristicsMaster;

    public static void register() {
        elementaristicsUp = new KeyBinding("key.elementaristics_up", Keyboard.KEY_UP, "category.elementaristics");
        elementaristicsDown = new KeyBinding("key.elementaristics_down", Keyboard.KEY_DOWN, "category.elementaristics");
        elementaristicsMaster = new KeyBinding("key.elementaristics_master", Keyboard.KEY_RETURN, "category.elementaristics");
        ClientRegistry.registerKeyBinding(elementaristicsUp);
        ClientRegistry.registerKeyBinding(elementaristicsDown);
        ClientRegistry.registerKeyBinding(elementaristicsMaster);
    }
}
