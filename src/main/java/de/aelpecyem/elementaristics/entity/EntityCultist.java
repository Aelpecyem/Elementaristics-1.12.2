package de.aelpecyem.elementaristics.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockAltar;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityCultist extends EntityTameable {
    Aspect aspect = Aspects.magan;
    float magan = 100;

    public EntityCultist(World worldIn) {
        super(worldIn);
        setSize(0.6F, 1.8F);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("aspectId", aspect.getId());
        compound.setFloat("magan", magan);
        return super.writeToNBT(compound);
    }

    public void setMagan(float magan) {
        this.magan = magan;
    }

    public float getMagan() {
        return magan;
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
        magan = compound.getFloat("magan");
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


    @Override
    protected void initEntityAI() {
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 0.5D, 4.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
          if(!world.isRemote && hand == EnumHand.MAIN_HAND) {
              if (!isSitting()) {
                  setSitting(true);
                  player.sendStatusMessage(new TextComponentString(I18n.format("message.cultist.sit")), true);
              } else {
                  setSitting(false);
                  player.sendStatusMessage(new TextComponentString(I18n.format("message.cultist.follow")), true);

              }
          }
        return super.processInteract(player, hand);
    }



    @Nullable
    @Override
    public EntityLivingBase getOwner() {
        return world.getClosestPlayer(posX, posY, posZ , 10000, false);
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
