package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellPush extends SpellBase {
    public SpellPush() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_push"), 20, 10, 60, Aspects.electricity.getColor(), Aspects.light.getColor(), SpellType.EDEMA, 2, 0);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        if (result.entityHit instanceof EntityLivingBase) {
            pushEntities((EntityLivingBase) result.entityHit, caster);
        }
        super.affect(result, caster, world, projectile);
    }

    public void pushEntities(EntityLivingBase target, EntityLivingBase caster){
        target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 10, 0, false, false));
        double distX = target.posX - caster.posX;
        double distZ = target.posZ - caster.posZ;
        double distY = caster.posY+1.5D - caster.posY;
        double dir = Math.atan2(distZ, distX);
        double speed = 4F / target.getDistance(caster) * 0.5F;
        if (distY<0)
        {
            target.motionY += speed;
        }

        target.motionX = Math.cos(dir) * speed;
        target.motionZ = Math.sin(dir) * speed;
    }
}
