package de.aelpecyem.elementaristics.misc.spell.damage;

import de.aelpecyem.elementaristics.entity.projectile.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DamageSpellBase extends SpellBase {
    DamageSource damageType;
    float damage;

    public DamageSpellBase(ResourceLocation name, float maganCost, int cooldownTicks, int stuntTime, DamageSource type, float damage, int color) {
        super(name, maganCost, cooldownTicks, stuntTime, color, color, 0, 0);
        this.damage = damage;
        this.damageType = type;
    }

    public DamageSpellBase(ResourceLocation name, float maganCost, int cooldownTicks, int stuntTime, DamageSource type, float damage, int color, int color2) {
        super(name, maganCost, cooldownTicks, stuntTime, color, color2, 0, 0);
        this.damage = damage;
        this.damageType = type;
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        EntityLivingBase target;
        if (result.entityHit instanceof EntityLivingBase) {
            target = (EntityLivingBase) result.entityHit;
            //new EntityDamageSourceIndirect(damageType.getDamageType(), projectile, caster);
            target.attackEntityFrom(new EntityDamageSourceIndirect(damageType.getDamageType(), projectile, caster), damage);

        } else {
            return;
        }
        super.affect(result, caster, world, projectile);
    }
}
