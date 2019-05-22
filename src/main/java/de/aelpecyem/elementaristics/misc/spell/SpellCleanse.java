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

public class SpellCleanse extends SpellBase {
    public SpellCleanse() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_cleanse"), 40, 20, 100, Aspects.water.getColor(), Aspects.body.getColor(), SpellType.SELF, 1, 0);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        for (int i = 0; i < 10; i++)
            Elementaristics.proxy.generateGenericParticles(caster, color, 4, 200, 0.1F, true, true);
        caster.heal(8);
        PotionEffect[] effects = new PotionEffect[]{
        };
        effects = caster.getActivePotionEffects().toArray(effects);
        for (PotionEffect effect : effects) {
            if (effect.getPotion().isBadEffect()) {
                caster.removePotionEffect(effect.getPotion());
                break;
            }
        }
        super.affect(result, caster, world, projectile);
    }
}
