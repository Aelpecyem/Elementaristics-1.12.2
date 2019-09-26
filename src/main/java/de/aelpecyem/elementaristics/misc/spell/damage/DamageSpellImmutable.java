package de.aelpecyem.elementaristics.misc.spell.damage;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.projectile.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
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
        EntityThrowable target;
        System.out.println("uwu");
        if (result.entityHit instanceof EntityThrowable) {
            target = (EntityThrowable) result.entityHit;
            Elementaristics.proxy.generateGenericParticles(target, SoulInit.soulImmutable.getParticleColor(), 3, 200, 0, false, true);
            target.setDead();
        }
        super.affect(result, caster, world, projectile);
    }
}
