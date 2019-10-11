package de.aelpecyem.elementaristics.entity.elementals;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockGoldenThread;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.TileEntityGoldenThread;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;

public class AbstractElemental extends EntityMob {
    protected static final DataParameter<Integer> ASPECT_ID = EntityDataManager.createKey(EntityCultist.class, DataSerializers.VARINT);

    public AbstractElemental(World worldIn) {
        super(worldIn);
        setSize(0.6F, 1.675F);

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
    protected void entityInit() {
        super.entityInit();
        dataManager.register(ASPECT_ID, Aspects.aether.getId());

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32F);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        setAspect(Aspects.getElementById(compound.getInteger("aspect")));
        super.readEntityFromNBT(compound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("aspect", getAspect().getId());
        super.writeEntityToNBT(compound);
    }

    public void setAspect(Aspect aspect) {
        dataManager.set(ASPECT_ID, aspect.getId());
    }

    public Aspect getAspect() {
        return Aspects.getElementById(dataManager.get(ASPECT_ID));
    }

    @Override
    public float getBrightness() {
        return 5F;
    }

    @Override
    public void onDeath(DamageSource cause) {
        Iterable<BlockPos> blocks = BlockPos.getAllInBox(getPosition().add(16, 16, 16), getPosition().add(-16, -16, -16));
        Iterator iterator = blocks.iterator();
        while (iterator.hasNext()) {
            BlockPos posTo = (BlockPos) iterator.next();
            if (world.getBlockState(posTo).getBlock() instanceof BlockGoldenThread) {
                TileEntity te = world.getTileEntity(posTo);
                if (te instanceof TileEntityGoldenThread && ((TileEntityGoldenThread) te).aspect == getAspect().getId()) {
                    ((TileEntityGoldenThread) te).charge++;
                    if (world.isRemote) {
                        for (int i = 0; i < 15 + getRNG().nextInt(5); i++)
                            Elementaristics.proxy.generateGenericParticles(world, posX + width / 2 + getRNG().nextGaussian() / 2, posY + width / 2 + getRNG().nextGaussian() / 2, posZ + width / 2 + getRNG().nextGaussian() / 2, 0, 0, 0, getAspect().getColor(), 1.8F + getRNG().nextFloat(), 200, 0, false, false, true, posTo.getX() + 0.5, posTo.getY() + 0.5, posTo.getZ() + 0.5);
                    }
                }
            }
        }
        super.onDeath(cause);
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
