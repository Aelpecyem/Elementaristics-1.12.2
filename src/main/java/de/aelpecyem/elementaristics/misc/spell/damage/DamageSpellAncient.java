package de.aelpecyem.elementaristics.misc.spell.damage;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DamageSpellAncient extends DamageSpellBase {
    public DamageSpellAncient() {
        super(new ResourceLocation(Elementaristics.MODID, "damage_ancient"), 30, 20, 60, Elementaristics.DAMAGE_PSYCHIC, 5, SoulInit.soulAncient.getParticleColor(), 6144);
        setType(SpellType.WAVE);
    }
}
