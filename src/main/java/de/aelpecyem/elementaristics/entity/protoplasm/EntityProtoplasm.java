package de.aelpecyem.elementaristics.entity.protoplasm;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;

public class EntityProtoplasm extends EntityTameable implements IMob{
    protected static final DataParameter<Integer> SIZE = EntityDataManager.<Integer>createKey(EntityProtoplasm.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityProtoplasm.class, DataSerializers.VARINT);

    public EntityProtoplasm(World worldIn) {
        super(worldIn);
        setSlimeSize(3, false);
    }

    @Override
    protected void initEntityAI() {
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityProtoplasm.AIPerformTasks(this));
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(3, this.aiSit);
        if (!isTamed()) {
                this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        }
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.3F + 0.1F * getSize()));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAITempt(this, 1.0D, ModItems.moss_everchaning, false));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));

        this.targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(3, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(SIZE, 3);
        dataManager.register(COLOR, 0);
    }

    //
    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        if (source == DamageSource.DROWN  || source == DamageSource.IN_WALL|| source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE){
            return true;
        }
        return super.isEntityInvulnerable(source);
    }

    protected void updateAITasks() {
        this.dataManager.set(SIZE, getSize());
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
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!isTamed()) {
            if (player.getHeldItem(hand).getItem() == ModItems.moss_everchaning) {
                if (!player.capabilities.isCreativeMode) {
                    player.getHeldItem(hand).shrink(1);
                }
                if (!this.world.isRemote){
                    if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                        this.setTamedBy(player);
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                        this.aiSit.setSitting(true);
                        this.setHealth(getMaxHealth());
                        this.playTameEffect(true);
                        this.world.setEntityState(this, (byte)7);
                    }
                    else {
                        this.playTameEffect(false);
                        this.world.setEntityState(this, (byte)6);
                    }
                }
            }
        }


        if (isOwner(player) && !world.isRemote && hand==EnumHand.MAIN_HAND) {
            if (player.getHeldItem(hand).getItem() instanceof ItemDye){
                addColor(EnumDyeColor.byDyeDamage(player.getHeldItem(hand).getItemDamage()).getColorValue());
                player.getHeldItem(hand).shrink(1);
                return true;
            }
            if(!player.getHeldItem(hand).interactWithEntity(player, this, hand)){
                if (!world.isRemote) {
                    this.aiSit.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    PacketHandler.sendTo(player, new PacketMessage("message.protoplasm." + (isSitting() ? "follow" : "sit"), true));
                    return true;
                }
            }
        }
        return super.processInteract(player, hand);
    }


    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return new ResourceLocation(Elementaristics.MODID, "protoplasm");
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("size", this.getSize() - 1);
        compound.setInteger("color", this.getColor());
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        int i = compound.getInteger("size");
        if (i < 0)
        {
            i = 0;
        }
        this.setSlimeSize(i + 1, false);
        this.setColor(compound.getInteger("color"));
        super.readEntityFromNBT(compound);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SIZE.equals(key)) {
            int i = this.getSize();
            this.setSize(0.51000005F * (float)i, 0.51000005F * (float)i);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;

            if (this.isInWater() && this.rand.nextInt(20) == 0)
            {
                this.doWaterSplashEffect();
            }
        }

        if (COLOR.equals(key)) {
            int i = this.getColor();
            this.setColor(i);
        }

        super.notifyDataManagerChange(key);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (!isOwner(entityIn) && getAttackTarget() != null && getAttackTarget().isEntityEqual(entityIn) && !isSitting()) {
            this.dealDamage(entityIn);
        }
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
        if (getAttackTarget() != null && getAttackTarget().isEntityEqual(entityIn) && entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer)){
            dealDamage((EntityLivingBase) entityIn);
        }
        super.collideWithEntity(entityIn);
    }

    protected void dealDamage(EntityLivingBase entityIn) {
        int i = this.getAttackStrength() * 2;

        if (this.canEntityBeSeen(entityIn) && this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength()))
        {
            this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.knockBack(entityIn, 0.5F, getLookVec().x,  getLookVec().z);
            this.applyEnchantments(this, entityIn);
        }
    }


    public float getEyeHeight() {
        return 0.625F * this.height;
    }

    protected int getAttackStrength() {
        return this.getSize() * 2;
    }


    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setSlimeSize(1, true);
        this.setColor(11582);
        return super.onInitialSpawn(difficulty, livingdata);
    }


    public int getSize(){
        return dataManager.get(SIZE);
    }

    public void setColor (int color){
        dataManager.set(COLOR, color);
        updateAITasks();
    }

    public int getColor(){
        return dataManager.get(COLOR);
    }

    public Color getColorAWT(){
        return MiscUtil.convertIntToColor(dataManager.get(COLOR));
    }

    /**
     * Merges 5% of the chosen color into the current one
     */
    public void addColor(int color){
        setColor(MiscUtil.blend(MiscUtil.convertIntToColor(getColor()), MiscUtil.convertIntToColor(color), 0.9, 0.1).getRGB());
    }

    public void setSlimeSize(int size, boolean resetHealth) {
        if (size > 0 && size <= 10) { //over size ten gets weird, in terms of hitboxes
            this.dataManager.set(SIZE, size);
            this.setSlimeSize(0.51000005F * (float) size, 0.51000005F * (float) size);
            this.setPosition(this.posX, this.posY, this.posZ);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((10 + 6 * size));
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((0.1995D + 0.005 * size));

            if (resetHealth) {
                this.setHealth(this.getMaxHealth());
            }

            this.experienceValue = size;
        }
       // updateAITasks();
    }

    protected void setSlimeSize(float widthIn, float heightIn) {
        if (widthIn != this.width || heightIn != this.height) {
            float f = this.width;
            this.width = widthIn;
            this.height = heightIn;

            if (this.width < f)
            {
                double d0 = (double)widthIn / 2.0D;
                this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double)this.height, this.posZ + d0));
                return;
            }

            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            this.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)this.width, axisalignedbb.minY + (double)this.height, axisalignedbb.minZ + (double)this.width));

            if (this.width > f && !this.firstUpdate && !this.world.isRemote)
            {
                this.move(MoverType.SELF, (double)(f - this.width), 0.1D, (double)(f - this.width));
            }
        }
    }

    static class AIPerformTasks extends EntityAIBase {
        private final EntityProtoplasm slime;
        private int growTieredTimer;
        private int waitingTimer;

        public AIPerformTasks(EntityProtoplasm slimeIn)
        {
            this.slime = slimeIn;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return slime.getOwner() != null && slime.getOwner() instanceof EntityPlayer && ((EntityPlayer)slime.getOwner()).bedLocation != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.growTieredTimer = 500;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            EntityLivingBase entitylivingbase = slime.getOwner();

            if (entitylivingbase == null) {
                return false;
            }
            else if (!entitylivingbase.isEntityAlive()) {
                return false;
            }
            else {
                return --this.growTieredTimer > 0;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            //move this to task instances, but they only use methods
            BlockPos pos = ((EntityPlayer)slime.getOwner()).bedLocation;
            slime.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), 1.0D);
        }
    }



}
