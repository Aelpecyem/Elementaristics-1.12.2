package de.aelpecyem.elementaristics.entity;

import com.google.common.base.Optional;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityProtoplasm extends EntityTameable implements IMob{
    protected static final DataParameter<Integer> SIZE = EntityDataManager.<Integer>createKey(EntityProtoplasm.class, DataSerializers.VARINT);
    public EntityProtoplasm(World worldIn) {
        super(worldIn);
        setSize(1, false);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    public int getSize(){
        return dataManager.get(SIZE);
    }

    public void setSize(int size, boolean resetHealth) {
        if (size > 0) {
            this.dataManager.set(SIZE, size);
            this.setSizeButDirty(0.51000005F * (float) size, 0.51000005F * (float) size);
            this.setPosition(this.posX, this.posY, this.posZ);
            if (resetHealth)
                this.setHealth(this.getMaxHealth());
            this.experienceValue = size;
        }
    }

    protected void setSizeButDirty(float width, float height){
        if (width != this.width || height != this.height)
        {
            float f = this.width;
            this.width = width;
            this.height = height;

            if (this.width < f)
            {
                double d0 = (double)width / 2.0D;
                this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double)this.height, this.posZ + d0));
                return;
            }

            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            this.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)this.width, axisalignedbb.minY + (double)this.height, axisalignedbb.minZ + (double)this.width));

            if (this.width > f && !this.firstUpdate && !this.world.isRemote)
            {
                this.move(MoverType.SELF, (double)(f - this.width), 0.0D, (double)(f - this.width));
            }
        }
    }


    @Override
    protected void entityInit() {
        dataManager.register(SIZE, 1);
        super.entityInit();
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        if (hand==EnumHand.MAIN_HAND) {
            if (player.isSneaking())
                setSize(getSize() + 1, false);
            else
                setSize(getSize() - 1, false);
        }
        if (!isTamed())
        setTamedBy(player);
//test end
        if (isOwner(player)) {
            if (!isSitting()) {
                if (!world.isRemote)
                    setSitting(true);
                if (world.isRemote)
                    player.sendStatusMessage(new TextComponentString(I18n.format("message.protoplasm.sit")), true); //TODO add translation keys for these messages
            } else {
                if (!world.isRemote)
                    setSitting(false);
                if (world.isRemote)
                    player.sendStatusMessage(new TextComponentString(I18n.format("message.protoplasm.follow")), true);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    protected void initEntityAI() {
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 0.25D, 4.0F, 2.0F));
        if(!isTamed()) {
            this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
            this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        }
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        if (!isTamed()) {
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityPigZombie.class}));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
            this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
            this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        }

    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Size", this.getSize() - 1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        int i = compound.getInteger("Size");

        if (i < 0)
        {
            i = 0;
        }

        this.setSize(i + 1, false);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (canDamagePlayer()) {
            this.dealDamage(entityIn);
        }
    }



    protected void dealDamage(EntityLivingBase entityIn)
    {
        int i = this.getSize();

        if (this.canEntityBeSeen(entityIn) && this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength()))
        {
            this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.knockBack(entityIn, 0.5F, getLookVec().x,  getLookVec().z);
            this.applyEnchantments(this, entityIn);
        }
    }

    protected boolean canDamagePlayer()
    {
        return !this.isTamed();
    }

    public float getEyeHeight()
    {
        return 0.625F * this.height;
    }

    protected int getAttackStrength()
    {
        return this.getSize();
    }

    public void notifyDataManagerChange(DataParameter<?> key)
    {
        if (SIZE.equals(key))
        {
            int i = this.getSize();
            this.setSize(0.51000005F * (float)i, 0.51000005F * (float)i);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;

            if (this.isInWater() && this.rand.nextInt(20) == 0)
            {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
    }

}
