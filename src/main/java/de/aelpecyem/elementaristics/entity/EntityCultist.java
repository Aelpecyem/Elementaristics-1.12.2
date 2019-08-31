package de.aelpecyem.elementaristics.entity;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.projectile.EntityElementalSpell;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemSoulMirror;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.entity.cultist.PacketSpawnCultistAttackParticles;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityCultist extends EntityTameable {

    private static final DataParameter<Integer> ASPECT_ID = EntityDataManager.createKey(EntityCultist.class, DataSerializers.VARINT);
    private static final DataParameter<Float> MAGAN = EntityDataManager.createKey(EntityCultist.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> STUNT_TIME = EntityDataManager.createKey(EntityCultist.class, DataSerializers.VARINT);
    //  private static final DataParameter<Boolean> WANDERING = EntityDataManager.createKey(EntityCultist.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> CASTING = EntityDataManager.createKey(EntityCultist.class, DataSerializers.VARINT);

    public EntityCultist(World worldIn) {
        super(worldIn);
        setSize(0.6F, 1.8F);

    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(ASPECT_ID, 0);
        dataManager.register(MAGAN, 80F);
        dataManager.register(STUNT_TIME, 0);
        dataManager.register(CASTING, 0);
        // dataManager.register(WANDERING, false);
    }

    public void setStuntTime(int stuntTime) {
        dataManager.set(STUNT_TIME, stuntTime);
    }

    public int getStuntTime() {
        return dataManager.get(STUNT_TIME);
    }

    public void setMagan(float magan) {
        dataManager.set(MAGAN, magan);
    }

    public float getMagan() {
        return dataManager.get(MAGAN);
    }

    public void setAspect(Aspect aspect) {
        dataManager.set(ASPECT_ID, aspect.getId());
    }

    public Aspect getAspect() {
        return Aspects.getElementById(dataManager.get(ASPECT_ID));
    }


    public int getCastingProgress() {
        return dataManager.get(CASTING);
    }

    public boolean isCasting() {
        return getCastingProgress() > 0;
    }

    public void setCastingProgress(int progress) {
        dataManager.set(CASTING, progress);
    }

    public void continueCasting() {
        setCastingProgress(getCastingProgress() + 1);
    }


    //public boolean isWandering() {
    //    return dataManager.get(WANDERING);
    //  }

    //  public void setWandering(boolean wandering) {
    //       dataManager.set(WANDERING, wandering);
    //  }

    //plans to add defense of sorts to cultists
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setAspect(Aspects.getElementById(compound.getInteger("aspectId")));
        setMagan(compound.getFloat("magan"));
        setStuntTime(compound.getInteger("stuntTime"));
        //    setWandering(compound.getBoolean("wander"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("aspectId", getAspect().getId());
        compound.setFloat("magan", getMagan());
        compound.setInteger("stuntTime", getStuntTime());
        compound.setInteger("castingProgress", getCastingProgress());
        //  compound.setBoolean("wander", isWandering());
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }

    @Override
    public boolean getAlwaysRenderNameTag() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        if (getStuntTime() < 0) {
            if (getMagan() < 80) {
                setMagan(getMagan() + 0.05F);
            } else {
                setMagan(80);
            }
        } else {
            setStuntTime(getStuntTime() - 1);
        }
        if (getRNG().nextFloat() < 0.1)
            Elementaristics.proxy.generateGenericParticles(this, getAspect().getColor(), 0.5F, 50, -0.01F, true, true);

        super.onLivingUpdate();
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (getOwner() != null && getOwner().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = getOwner().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            cap.setCultistCount(cap.getCultistCount() - 1);
        }
        super.onDeath(cause);
    }

    @Override
    protected void initEntityAI() { //todo, add some sort of wandering mode, though that may be reserved for later builds
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new AICastSpell(this));
        this.tasks.addTask(3, this.aiSit);
        this.tasks.addTask(4, new EntityAIFollowOwner(this, 1D, 4.0F, 2.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this)); //same as below
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));//doesn't get called, check conditions
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND && isOwner(player)) {
            if (isOwner(player)) {
                if (player.getHeldItemMainhand().getItem() instanceof ItemSoulMirror) {
                    if (world.isRemote) {
                        player.sendMessage(new TextComponentString(I18n.format("message.cultist_stats.name")));
                        player.sendMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.cultist_aspect.name") + " " + getAspect().getLocalizedName()));
                        player.sendMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.cultist_magan.name") + " " + getMagan()));
                    }
                    return true;
                }
                if (!world.isRemote) {
                        this.setSitting(!this.isSitting());
                        this.isJumping = false;
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                        PacketHandler.sendTo(player, new PacketMessage("message.cultist." + (!isSitting() ? "follow" : "sit"), true));
                        return true;
                }
            } else {
                if (world.isRemote)
                    player.sendStatusMessage(new TextComponentString(I18n.format("message.cultist.deny")), true);
            }
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                cap.setCultistCount(world.getEntities(EntityCultist.class, new Predicate<EntityCultist>() {
                    @Override
                    public boolean apply(@Nullable EntityCultist input) {
                        return input.isOwner(player);
                    }
                }).size());
            }
        }
        return super.processInteract(player, hand);
    }

    public boolean drainMagan(float amount) {
        if (getMagan() >= amount) {
            setMagan(getMagan() - amount);
            return true;
        } else {
            setMagan(0);
            return false;
        }
    }

    @Override
    public void setSitting(boolean sitting) {
        super.setSitting(sitting);
        if (aiSit != null) {
            aiSit.setSitting(sitting);
        }
        updateAITasks();
    }

    @Override
    public boolean isSitting() {
        return super.isSitting();
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

    static class AICastSpell extends EntityAIBase {
        private final EntityCultist cultist;
        private boolean finished;

        public AICastSpell(EntityCultist cultist) {
            this.cultist = cultist;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityLivingBase target = this.cultist.getAttackTarget();
            if (target != null && target.isEntityAlive()) {
                return target.getDistance(cultist) < 40;
            }
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            cultist.setCastingProgress(1);
            finished = false;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            EntityLivingBase target = this.cultist.getAttackTarget();
            if (target == null || target.getDistance(cultist) >= 40 || target.isDead || finished) {
                cultist.setCastingProgress(0);
                return false;
            }
            return true;
        }

        @Override
        public void resetTask() {
            cultist.setCastingProgress(0);
            finished = false;
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            cultist.continueCasting();
            EntityLivingBase target = this.cultist.getAttackTarget();
            PacketHandler.sendToAllLoaded(cultist, new PacketSpawnCultistAttackParticles(cultist));
            if (target != null && !target.isDead) {
                cultist.getLookHelper().setLookPosition(target.posX, target.posY + target.getEyeHeight(), target.posZ, cultist.getHorizontalFaceSpeed(), cultist.getVerticalFaceSpeed());
                if (cultist.getCastingProgress() >= 40) {
                    EntityElementalSpell projectile = new EntityElementalSpell(cultist.world, cultist);
                    projectile.setAspectId(cultist.getAspect().getId());
                    projectile.shoot(cultist, cultist.rotationPitch, cultist.rotationYawHead, 0.5F * 3.0F, 1);
                    if (!cultist.world.isRemote) {
                        cultist.world.spawnEntity(projectile);
                    }
                    cultist.drainMagan(5);
                    finished = true;
                }
            } else {
                cultist.setCastingProgress(0);
            }
            super.updateTask();
        }
    }
}
