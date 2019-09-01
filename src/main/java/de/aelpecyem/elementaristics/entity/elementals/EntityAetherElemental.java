package de.aelpecyem.elementaristics.entity.elementals;

import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        this.tasks.addTask(1, new AITeleportWithFading(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 100.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

    }

    public int getWarpState() {
        return dataManager.get(WARP_STATE);
    }

    public void setWarpState(int state) {
        dataManager.set(WARP_STATE, state);
    }

    public boolean teleportCloseToTarget(EntityLivingBase target) {
        double telPosX = 0, telPosY = 0, telPosZ = 0;
        for (int i = 0; i < 100; i++) {
            telPosX = target.posX;
            telPosY = target.posY;
            telPosZ = target.posZ;
            telPosX += getRNG().nextGaussian() * (getRNG().nextInt(6) + 3);
            telPosZ += getRNG().nextGaussian() * (getRNG().nextInt(6) + 3);

            for (int y = -5; y < 5; y++) {
                if (world.getBlockState(new BlockPos(telPosX, telPosY + y - 1, telPosZ)).getMaterial().blocksMovement()) {
                    telPosY += posY;
                    break;
                }
            }

            if (!world.getBlockState(new BlockPos(telPosX, telPosY, telPosZ)).getMaterial().blocksMovement() && !world.getBlockState(new BlockPos(telPosX, telPosY + 1, telPosZ)).getMaterial().blocksMovement()) {
                break;
            }
        }
        setPositionAndUpdate(telPosX, telPosY, telPosZ);
        getLookHelper().setLookPosition(target.posX, target.posY + target.getEyeHeight(), target.posZ, getHorizontalFaceSpeed(), getVerticalFaceSpeed());
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

    static class AITeleportWithFading extends EntityAIBase {
        private final EntityAetherElemental elemental;
        private boolean finished;

        public AITeleportWithFading(EntityAetherElemental cultist) {
            this.elemental = cultist;
            this.setMutexBits(8);
        }

        public boolean shouldExecute() {
            EntityLivingBase target = this.elemental.getAttackTarget();
            if (target != null && target.isEntityAlive()) {
                System.out.println("yay?");
                return elemental.getRNG().nextFloat() < 0.4F;
            }
            return false;
        }

        public void startExecuting() {
            finished = false;
            super.startExecuting();
        }

        public boolean shouldContinueExecuting() {
            return !finished && elemental.getWarpState() >= 0;
        }

        @Override
        public void resetTask() {
            elemental.setWarpState(0);
            finished = false;
            super.resetTask();
        }

        public void updateTask() {
            EntityLivingBase target = this.elemental.getAttackTarget();
            if (elemental.getWarpState() < 100 && !finished) {
                elemental.advanceWarpState(false);
                if (elemental.getWarpState() >= 100) {
                    elemental.teleportCloseToTarget(target);
                    finished = true;
                }
            } else {
                elemental.advanceWarpState(true);
            }

            super.updateTask();
        }
    }
}
