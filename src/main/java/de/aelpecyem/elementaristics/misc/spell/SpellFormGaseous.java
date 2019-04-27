package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellFormGaseous extends SpellBase {
    public SpellFormGaseous() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_form_gaseous"), 80, 200, 400, Aspects.air.getColor(), Aspects.air.getColor(), SpellType.SELF);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world) {
        for (int i = 0; i < 10; i++)
            Elementaristics.proxy.generateGenericParticles(caster, color, 4, 200, 0.1F, true, true);
        caster.addPotionEffect(new PotionEffect(PotionInit.potionFormGaseous, 4000, 0, false, false));
        super.affect(result, caster, world);
    }
}
