package de.aelpecyem.elementaristics.misc.poisons;

import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PoisonGlassfinger extends PoisonEffectBase {
    public PoisonGlassfinger() {
        super(PoisonInit.poisons.size(), Aspects.light.getColor());
    }

    @Override
    public void performEffect(World world, EntityLivingBase player) {
        player.attackEntityFrom(DamageSource.FLY_INTO_WALL, 100);
        super.performEffect(world, player);
    }
}
