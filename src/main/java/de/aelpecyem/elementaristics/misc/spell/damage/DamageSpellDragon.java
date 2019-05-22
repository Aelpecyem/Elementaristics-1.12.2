package de.aelpecyem.elementaristics.misc.spell.damage;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DamageSpellDragon extends DamageSpellBase {
    public DamageSpellDragon() {
        super(new ResourceLocation(Elementaristics.MODID, "damage_dragon"), 10, 0, 80, DamageSource.IN_FIRE, 5, Aspects.fire.getColor(), 15348260);
        setType(SpellType.EDEMA);
    }


    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        EntityLivingBase target;
        if (result.entityHit instanceof EntityLivingBase) {
            target = (EntityLivingBase) result.entityHit;
            target.setFire(2);

        } else {
            return;
        }
        super.affect(result, caster, world, projectile);
    }
}
