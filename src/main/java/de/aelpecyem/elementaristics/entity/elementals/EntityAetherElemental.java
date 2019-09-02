package de.aelpecyem.elementaristics.entity.elementals;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityAetherElemental extends AbstractElemental {
    private static final DataParameter<Integer> WARP_STATE = EntityDataManager.createKey(EntityCultist.class, DataSerializers.VARINT); //Let's do the time warp again!

    public EntityAetherElemental(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(WARP_STATE, 0);
        setAspect(Aspects.aether);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AITeleportWithFading(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 100.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

    }

    @Override
    public void onLivingUpdate() {
        for (int i = 0; i < 3; i++) {
            if (getRNG().nextInt(85) < 100 - getWarpState() && world.isRemote) {
                Elementaristics.proxy.generateGenericParticles(this, getAspect().getColor(), 2.5F + getRNG().nextFloat() - (float) getWarpState() / 30F, 60 + getRNG().nextInt(20), -0.01F, true, true);
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if ((100 - getWarpState()) < 25) {
            return false;
        }
        return super.attackEntityFrom(source, amount - (float) getWarpState() / 50F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if ((100 - getWarpState()) < 45) {
            return false;
        }
        return super.attackEntityAsMob(entityIn);
    }

    public int getWarpState() {
        return dataManager.get(WARP_STATE);
    }

    public void setWarpState(int state) {
        dataManager.set(WARP_STATE, state);
    }

    public boolean teleportCloseToTarget(EntityLivingBase target) {
        if (target != null) {
            double telPosX, telPosY, telPosZ;
            for (int i = 0; i < 100; i++) {
                telPosX = target.posX;
                telPosZ = target.posZ;
                telPosX += getRNG().nextGaussian() * (getRNG().nextInt(6) + 3);
                telPosZ += getRNG().nextGaussian() * (getRNG().nextInt(6) + 3);

                for (telPosY = posY - 4; telPosY < posY + 5; telPosY++) {
                    if (attemptTeleport(telPosX, telPosY, telPosZ)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean attemptTeleport(double x, double y, double z) {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(this);
        World world = this.world;

        if (world.isBlockLoaded(blockpos)) {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0) {
                BlockPos blockpos1 = blockpos.down();
                IBlockState iblockstate = world.getBlockState(blockpos1);

                if (iblockstate.getMaterial().blocksMovement()) {
                    flag1 = true;
                } else {
                    --this.posY;
                    blockpos = blockpos1;
                }
            }

            if (flag1) {
                this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

                if (world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(this.getEntityBoundingBox())) {
                    flag = true;
                }
            }
        }

        if (!flag) {
            this.setPositionAndUpdate(d0, d1, d2);
            return false;
        }
        return true;
    }

    public boolean advanceWarpState(boolean reverse) {
        if (!reverse) {
            if (getWarpState() < 100) {
                setWarpState(getWarpState() + 1);
                return getWarpState() >= 100;
            }
        } else {
            if (getWarpState() > 0) {
                setWarpState(getWarpState() - 1);
                return getWarpState() <= 0;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        setAspect(Aspects.aether);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    static class AITeleportWithFading extends EntityAIBase { //change this to just fade out, add other ai etc. add attack cooldown to Silver T
        private final EntityAetherElemental elemental;
        private int stage;

        public AITeleportWithFading(EntityAetherElemental cultist) {
            this.elemental = cultist;
            this.setMutexBits(8);
        }

        public boolean shouldExecute() {
            EntityLivingBase target = this.elemental.getAttackTarget();
            if (target != null && target.isEntityAlive()) {
                return elemental.getRNG().nextFloat() < 0.05F;
            }
            return false;
        }

        public void startExecuting() {
            stage = 0;
            super.startExecuting();
        }

        public boolean shouldContinueExecuting() {
            return elemental.getAttackTarget() != null && !(stage >= 2) && elemental.getWarpState() >= 0;
        }

        @Override
        public void resetTask() {
            elemental.setWarpState(0);
            stage = 0;
            super.resetTask();
        }

        public void updateTask() {
            EntityLivingBase target = this.elemental.getAttackTarget();
            if (elemental.getWarpState() < 100 && stage < 1) {
                elemental.advanceWarpState(false);
                if (elemental.getWarpState() >= 100) {
                    elemental.teleportCloseToTarget(target);
                    stage = 1;
                }
            } else {
                elemental.advanceWarpState(true);
                if (elemental.getWarpState() <= 0) {
                    stage = 2;
                }
            }

            super.updateTask();
        }
    }
}
