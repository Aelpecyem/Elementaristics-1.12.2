package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.capability.*;
import net.minecraftforge.common.capabilities.CapabilityManager;


public class ModCaps {
    public static void registerCapabilites() {
        CapabilityManager.INSTANCE.register(IPlayerCapabilities.class, new PlayerCapStorage(), PlayerCapabilities.class);
    }

}
