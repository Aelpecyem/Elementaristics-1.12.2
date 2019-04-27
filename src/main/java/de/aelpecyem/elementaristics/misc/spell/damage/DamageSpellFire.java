package de.aelpecyem.elementaristics.misc.spell.damage;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DamageSpellFire extends DamageSpellBase {
    public DamageSpellFire() {
        super(new ResourceLocation(Elementaristics.MODID, "damage_fire"), 20, 16, 40, DamageSource.IN_FIRE, 5, Aspects.fire.getColor(), 15348260);

    }


    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world) {
        EntityLivingBase target;
        if (result.entityHit instanceof EntityLivingBase) {
            target = (EntityLivingBase) result.entityHit;
            target.setFire(3);

        } else {
            return;
        }
        super.affect(result, caster, world);
    }
}
