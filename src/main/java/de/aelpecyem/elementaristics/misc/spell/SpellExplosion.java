package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class SpellExplosion extends SpellBase {
    public SpellExplosion() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_explosion"), 20, 20, 40, Aspects.fire.getColor(), Aspects.chaos.getColor(), SpellType.PROJECTILE, 3, 0);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        Explosion explosion = new Explosion(world, projectile, projectile.posX, projectile.posY, projectile.posZ, 2, false, false);
        explosion.doExplosionA();
        explosion.doExplosionB(true);

        projectile.setDead();
        super.affect(result, caster, world, projectile);
    }
}
