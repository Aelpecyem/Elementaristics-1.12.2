package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellActivate extends SpellBase {
    public SpellActivate() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_activate"), 20, 20, 40, Aspects.mana.getColor(), Aspects.earth.getColor(), SpellType.PROJECTILE, 3, 0);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        if (caster instanceof EntityPlayer && result.getBlockPos() != null && result.sideHit != null) {
            world.getBlockState(result.getBlockPos()).getBlock().onBlockActivated(world, result.getBlockPos(),
                    world.getBlockState(result.getBlockPos()), (EntityPlayer) caster, EnumHand.OFF_HAND, result.sideHit, 0.5F, 0.5F, 0.5F);
        }
        super.affect(result, caster, world, projectile);
    }
}
