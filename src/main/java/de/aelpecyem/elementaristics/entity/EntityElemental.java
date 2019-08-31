package de.aelpecyem.elementaristics.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityGoldenThread;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockGoldenThread;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class EntityElemental extends EntityMob {
    public EntityElemental(World worldIn) {
        super(worldIn);
        setSize(1.5F, 1.5F);

    }

    @Override
    public float getBrightness() {
        return 1F;
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if (damageSrc.getTrueSource() != null && damageSrc.getTrueSource().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = damageSrc.getTrueSource().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getSoulId() == SoulInit.soulUnstable.getId()) {
                damageAmount = 10;

            } else {
                damageAmount = 2;
            }
            super.damageEntity(damageSrc, damageAmount); //should only be hurt by players
        }

    }

    @Override
    public void onDeath(DamageSource cause) {
        List<BlockPos> blocks = (List<BlockPos>) BlockPos.getAllInBox(getPosition().add(16, 16, 16), getPosition().add(-16, -16, -16));
        System.out.println(blocks);
        if (blocks.size() > 0) {
            Iterator iterator = blocks.iterator();
            while (iterator.hasNext()) {
                BlockPos pos = (BlockPos) iterator.next();
                if (world.getBlockState(pos).getBlock() instanceof BlockGoldenThread) {
                    TileEntity te = world.getTileEntity(pos);
                    if (te instanceof TileEntityGoldenThread) {
                        ((TileEntityGoldenThread) te).charge++;
                        //also add fancy ass particles
                    }
                }
            }
        }
        super.onDeath(cause);
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32F);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }


    @Override
    protected void initEntityAI() {

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 100.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    public void onLivingUpdate() {
        for (int i = 0; i < 2; i++) {
            if (world.isRemote)
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
                        12249855, 4, 120, 0, true, true, 0.4F, true));
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
