package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellCrystalProtection extends SpellBase {
    public SpellCrystalProtection() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_crystal_protection"), 70, 100, 200, Aspects.crystal.getColor(), Aspects.earth.getColor(), SpellType.SELF, 1, 0);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        for (int i = 0; i < 10; i++)
            Elementaristics.proxy.generateGenericParticles(caster, color, 4, 200, 0.1F, true, true);
        caster.addPotionEffect(new PotionEffect(PotionInit.potionCrystalProtection, 4000, 0, false, false));
        super.affect(result, caster, world, projectile);
    }
}