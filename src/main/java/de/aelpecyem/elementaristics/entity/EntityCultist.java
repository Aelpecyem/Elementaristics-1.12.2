package de.aelpecyem.elementaristics.entity;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemSoulMirror;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
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
    private static final DataParameter<Integer> VARIANT_ID = EntityDataManager.createKey(EntityCultist.class, DataSerializers.VARINT); //only until there's fancy textures for each cultist
  //  public static final String[] possibleNames = { "Ael", "Edward", "Amelie", "Sirona"};
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
        dataManager.register(VARIANT_ID, 0);
        dataManager.set(VARIANT_ID, rand.nextInt(6));
       // setCustomNameTag("AWAF"); choose from the possible names etc.
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

    public int getVariant() {
        return dataManager.get(VARIANT_ID);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        setAspect(Aspects.getElementById(compound.getInteger("aspectId")));
        setMagan(compound.getFloat("magan"));
        setStuntTime(compound.getInteger("stuntTime"));
        super.readFromNBT(compound);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("aspectId", getAspect().getId());
        compound.setFloat("magan", getMagan());
        compound.setInteger("stuntTime", getStuntTime());
        return super.writeToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setAspect(Aspects.getElementById(compound.getInteger("aspectId")));
        setMagan(compound.getFloat("magan"));
        setStuntTime(compound.getInteger("stuntTime"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("aspectId", getAspect().getId());
        compound.setFloat("magan", getMagan());
        compound.setInteger("stuntTime", getStuntTime());
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
        if (getStuntTime() < 0) {
            if (getMagan() < 80) {
                setMagan(getMagan() + 0.05F);
            } else {
                setMagan(80);
            }
        } else {
            setStuntTime(getStuntTime() - 1);
        }
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

    public void lookAt(double px, double py, double pz) {
        double directionX = posX - px;
        double directionY = posY - py;
        double directionZ = posZ - pz;

        double length = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);

        directionX /= length;
        directionY /= length;
        directionZ /= length;

        double pitch = Math.asin(directionY);
        double yaw = Math.atan2(directionZ, directionX);

        //to degree
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;

        yaw += 90f;
        rotationPitch = (float) pitch;
        rotationYaw = (float) yaw;
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
                if (!isSitting()) {
                    if (!world.isRemote)
                        setSitting(true);
                    if (world.isRemote)
                        player.sendStatusMessage(new TextComponentString(I18n.format("message.cultist.sit")), true);
                } else {
                    if (!world.isRemote)
                        setSitting(false);
                    if (world.isRemote)
                        player.sendStatusMessage(new TextComponentString(I18n.format("message.cultist.follow")), true);
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
