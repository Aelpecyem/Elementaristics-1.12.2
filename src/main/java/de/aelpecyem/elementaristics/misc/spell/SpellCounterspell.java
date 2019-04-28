package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import scala.xml.Elem;

import java.util.List;

public class SpellCounterspell extends SpellBase {
    public SpellCounterspell() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_counterspell"), 30, 10, 60, SoulInit.soulImmutable.getParticleColor(), Aspects.crystal.getColor(), SpellType.SELF);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world) {
        for (int i = 0; i < 10; i++)
            Elementaristics.proxy.generateGenericParticles(caster, color, 4, 200, 0.1F, true, true);
        caster.addPotionEffect(new PotionEffect(PotionInit.potionCounterspell, 400, 0, false, false));

        super.affect(result, caster, world);
    }
}
