package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.List;

public class SpellPull extends SpellBase {
    public SpellPull() {
        super(new ResourceLocation(Elementaristics.MODID, "spell_pull"), 10, 10, 20, Aspects.earth.getColor(), Aspects.light.getColor(), SpellType.PROJECTILE, 2, 0);///Aspects.electricity.getColor(), Aspects.light.getColor(), SpellType.PROJECTILE, 3, 0);
    }

    @Override
    public void affect(RayTraceResult result, EntityLivingBase caster, World world, EntitySpellProjectile projectile) {
        if (result.entityHit instanceof EntityLivingBase) {
            pullEntities((EntityLivingBase) result.entityHit, caster);
        }else {
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, projectile.getEntityBoundingBox().grow(0.5));
            if (!items.isEmpty() && caster instanceof EntityPlayer){
                for (EntityItem item : items){
                    ItemHandlerHelper.giveItemToPlayer((EntityPlayer) caster, item.getItem());
                    item.setDead();
                }
            }
        }

        super.affect(result, caster, world, projectile);
    }

    public void pullEntities(EntityLivingBase target, EntityLivingBase caster){
        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 0, false, false));
        double distX = caster.posX - target.posX;
        double distZ = caster.posZ - target.posZ;
        double distY = target.posY+1.5D - caster.posY;
        double dir = Math.atan2(distZ, distX);
        double speed = 3F / target.getDistance(caster) * 0.5F;
        if (distY<0)
        {
            target.motionY += speed;
        }

        target.motionX = Math.cos(dir) * speed;
        target.motionZ = Math.sin(dir) * speed;
    }
}
