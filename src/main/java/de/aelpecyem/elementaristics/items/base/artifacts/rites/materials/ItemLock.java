package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLock extends ItemBase {
    public static final String TICKS = "tickCount";
    public ItemLock() {
        super("item_lock");
    }
    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (!entityItem.world.isDaytime())
        entityItem.setEntityInvulnerable(true);
        if (entityItem.isBurning()){
            List<EntitySheep> sheep = entityItem.getEntityWorld().getEntitiesWithinAABB(EntitySheep.class, entityItem.getRenderBoundingBox().grow(8), new Predicate<EntitySheep>() {
                @Override
                public boolean apply(@Nullable EntitySheep input) {
                    return input.getFleeceColor() == EnumDyeColor.RED;
                }
            });
            if (entityItem.world.isRemote){
                double motionX = entityItem.world.rand.nextGaussian() * 0.03D;
                double motionY = entityItem.world.rand.nextFloat() * 0.15D;
                double motionZ = entityItem.world.rand.nextGaussian() * 0.03D;
                Elementaristics.proxy.generateGenericParticles(entityItem.world, entityItem.posX, entityItem.posY, entityItem.posZ, motionX, motionY, motionZ, Aspects.life.getColor(), 0.7F + entityItem.world.rand.nextFloat(), 120, 0.001F, true, false);//particles
            }
            if (!sheep.isEmpty()){
                double motionX = entityItem.world.rand.nextGaussian() * 0.03D;
                double motionY = entityItem.world.rand.nextFloat() * 0.15D;
                double motionZ = entityItem.world.rand.nextGaussian() * 0.03D;
                Elementaristics.proxy.generateGenericParticles(entityItem.world, entityItem.posX, entityItem.posY, entityItem.posZ, motionX, motionY, motionZ, Aspects.life.getColor(), 0.7F + entityItem.world.rand.nextFloat() + (float) getTicks(entityItem.getItem()) / 30F, 120, 0.001F, true, false);//particles
                addTick(entityItem.getItem());
                for (EntitySheep sheepy : sheep){
                    sheepy.motionX = (entityItem.posX  - sheepy.posX) / 20;
                    sheepy.motionY = (entityItem.posY + 0.5 - sheepy.posY) / 20;
                    sheepy.motionZ = (entityItem.posZ - sheepy.posZ) / 20;
                    sheepy.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 30, 0, false, false));
                }
                if (getTicks(entityItem.getItem()) > 160){ //todo particles
                    for (EntitySheep sheepy : sheep){
                        sheepy.attackEntityFrom(Elementaristics.DAMAGE_AETHER, 666);
                        entityItem.setItem(new ItemStack(ModItems.fragment_mother));
                        entityItem.motionY += 5;
                        entityItem.setEntityInvulnerable(true);
                        break;
                    }
                }

            }else{
                if (entityItem.getItem().hasTagCompound() && entityItem.getItem().getTagCompound().hasKey(TICKS))
                entityItem.getItem().getTagCompound().removeTag(TICKS);
            }
        }

        return super.onEntityItemUpdate(entityItem);
    }

    public void addTick(ItemStack stack){
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        if (!stack.getTagCompound().hasKey(TICKS)) stack.getTagCompound().getInteger(TICKS);
        stack.getTagCompound().setInteger(TICKS, stack.getTagCompound().getInteger(TICKS) + 1);
    }

    public void setTicks(ItemStack stack, int ticks){
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        if (!stack.getTagCompound().hasKey(TICKS)) stack.getTagCompound().getInteger(TICKS);
        stack.getTagCompound().setInteger(TICKS, ticks);
    }

    public int getTicks(ItemStack stack){
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        if (!stack.getTagCompound().hasKey(TICKS)) stack.getTagCompound().getInteger(TICKS);
        return stack.getTagCompound().getInteger(TICKS);
    }
}
