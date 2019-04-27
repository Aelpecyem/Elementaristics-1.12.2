package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapStorage;
import de.aelpecyem.elementaristics.capability.player.PlayerCapabilities;
import net.minecraftforge.common.capabilities.CapabilityManager;


public class ModCaps {
    public static void registerCapabilites() {
        CapabilityManager.INSTANCE.register(IPlayerCapabilities.class, new PlayerCapStorage(), PlayerCapabilities.class);
    }

}
