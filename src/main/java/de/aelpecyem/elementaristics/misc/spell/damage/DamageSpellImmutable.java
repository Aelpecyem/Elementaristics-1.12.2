package de.aelpecyem.elementaristics.misc.spell.damage;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DamageSpellImmutable extends DamageSpellBase {
    public DamageSpellImmutable() {
        super(new ResourceLocation(Elementaristics.MODID, "damage_nullifying"), 20, 16, 40, DamageSource.GENERIC, 4, SoulInit.soulImmutable.getParticleColor());

    }


    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        EntitySpellProjectile target;
        if (result.entityHit instanceof EntitySpellProjectile) {
            target = (EntitySpellProjectile) result.entityHit;
            Elementaristics.proxy.generateGenericParticles(target, SoulInit.soulImmutable.getParticleColor(), 3, 200, 0, false, true);
            target.setDead();
        }
        super.affect(result, caster, world, projectile);
    }
}
