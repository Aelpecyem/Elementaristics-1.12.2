package de.aelpecyem.elementaristics.entity;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nullable;

public class EntitySilverThread extends EntityMob {

    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public EntitySilverThread(World worldIn) {
        super(worldIn);
        setSize(0.6F, 1.8F);

    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if(damageSrc.getTrueSource() != null  && damageSrc.getTrueSource().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = damageSrc.getTrueSource().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getSoulId() == SoulInit.soulInstable.getId()) {
                damageAmount = 10;

            } else {
                damageAmount = 2;
            }
            super.damageEntity(damageSrc, damageAmount); //should only be hurt by players
         }

    }

    @Override
    public void onDeath(DamageSource cause) {
        EntityPlayer player = world.getClosestPlayer(posX, posY, posZ, 100, false);
        if (cause.getTrueSource() instanceof EntityPlayer) {
             player = (EntityPlayer) cause.getTrueSource();
             if(world.isRemote)
            player.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.ascension_1.standard")), false);
        }else if (player != null) {
            if(player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)){
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                if (cap.knowsSoul()) {
                    if (cap.getPlayerAscensionStage() < 1){
                        cap.setPlayerAscensionStage(1);
                        if (world.isRemote)
                            player.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.ascension_1.standard")), false);
                    }

                }
            }

        }
        super.onDeath(cause);
    }


    @Override
    protected void applyEntityAttributes() {

        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34F);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }


    protected void updateAITasks() {
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        entityIn.attackEntityFrom(DamageSource.MAGIC, 1);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean hitByEntity(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase) entity;
            switch (world.rand.nextInt(5)) {
                case 1:
                    if (!attemptTeleport(posX + 3 + rand.nextInt(7), entity.posY, posZ + 3 + rand.nextInt(7))) {

                    } else {
                        for (int i = 0; i < world.rand.nextInt(8); i++) {
                            if (attemptTeleport(posX + 3 + rand.nextInt(7), posY + rand.nextInt(5), posX + rand.nextInt(10)))
                                break;
                        }
                    }
                    break;
                case 2:
                    if (!attemptTeleport(posX -rand.nextInt(10), entity.posY, posZ-rand.nextInt(10))) {

                    } else {
                        for (int i = 0; i < world.rand.nextInt(8); i++) {
                            if (attemptTeleport(posX -3 -rand.nextInt(7), posY + rand.nextInt(5), posZ - 3 - rand.nextInt(10)))
                                break;
                        }
                    }

                default:
                    BlockPos curPos = getPosition();
                    setPosition(living.posX, living.posY, living.posZ);
                    living.setPosition(curPos.getX(), curPos.getY(), curPos.getZ());
                    setRotation(living.getRotationYawHead(), living.rotationPitch);
                    living.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 20, 3, false, false));

            }
            if (!attemptTeleport(rand.nextInt(10), 0, rand.nextInt(10))) {
                this.attemptTeleport((Math.abs(this.posX) - Math.abs(living.posX)) * -1 + posX, living.posY, (Math.abs(this.posZ) - Math.abs(living.posZ)) * -1 + posZ);
            } else {
                for (int i = 0; i < 5; i++) {
                    attemptTeleport(rand.nextInt(10), rand.nextInt(5), rand.nextInt(10));
                }
            }
        }


        return super.hitByEntity(entity);
    }

    @Override
    protected void initEntityAI() {

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityPigZombie.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));

    }

    @Override
    public void onLivingUpdate() {
        for (int i = 0; i < 3; i++) {
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(
                    world,
                    posX + world.rand.nextFloat() * width
                            * 2.0F - width,
                    posY + 0.5D + world.rand.nextFloat()
                            * height,
                    posZ + world.rand.nextFloat() * width
                            * 2.0F - width,
                    0,
                    0,
                    0,
                    12249855, 4, 120, 0, true, true, 0.4F));
        }
        super.onLivingUpdate();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return super.getHurtSound(damageSourceIn);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }
}
