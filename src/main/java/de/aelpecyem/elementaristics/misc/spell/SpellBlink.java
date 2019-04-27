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

public class SpellBlink extends SpellBase {
    public SpellBlink() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_blink"), 10, 20, 40, Aspects.aether.getColor(), Aspects.mana.getColor(), SpellType.SELF);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world) {
        for (int i = 0; i < 10; i++)
            Elementaristics.proxy.generateGenericParticles(caster, color, 3, 200, 0.1F, true, true);
        System.out.println(result.hitVec);
        if (caster.isSneaking())
            caster.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
        else {
            caster.attemptTeleport(result.hitVec.x, result.hitVec.y, result.hitVec.z);
        }
        super.affect(result, caster, world);
    }
}
