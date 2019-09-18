package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.util.ResourceLocation;

public class DeityKing extends DeitySupplyEffectBase {

    public DeityKing() {
        super(TimeUtil.getTickTimeStartForHour(20), new ResourceLocation(Elementaristics.MODID, "deity_king"), Aspects.body, 6296600);
    }
}

