package de.aelpecyem.elementaristics.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityCultist extends EntityTameable {
    Aspect aspect = Aspects.magan;

    public EntityCultist(World worldIn) {
        super(worldIn);
        setSize(0.6F, 1.8F);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("aspectId", aspect.getId());
        return super.writeToNBT(compound);
    }


    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }

    public Aspect getAspect() {
        return aspect;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        aspect = Aspects.getElementById(compound.getInteger("aspectId"));
        super.readFromNBT(compound);
    }

    @Override
    protected void applyEntityAttributes() {

        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }

    @Override
    public boolean getAlwaysRenderNameTag() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        //TODO later... make those actually usable
        Elementaristics.proxy.generateGenericParticles(this, aspect.getColor(), 0.5F, 50, -0.01F, true, true);
        super.onLivingUpdate();
    }

    @Nullable
    @Override
    public EntityLivingBase getOwner() {
        return world.getClosestPlayer(posX, posY, posZ, 2000, false);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, getAIMoveSpeed(), 5, 50));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }



    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}
