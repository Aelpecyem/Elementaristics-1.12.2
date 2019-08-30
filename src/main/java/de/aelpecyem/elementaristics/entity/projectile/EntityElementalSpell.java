package de.aelpecyem.elementaristics.entity.projectile;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

//yup, grabbed that code from https://github.com/zabi94/Covens-reborn/blob/master/src/main/java/com/covens/common/entity/EntitySpellCarrier.java
public class EntityElementalSpell extends EntityThrowable {
    private static final DataParameter<Integer> ASPECT = EntityDataManager.<Integer>createKey(EntityElementalSpell.class, DataSerializers.VARINT);
    private static final DataParameter<String> CASTER = EntityDataManager.<String>createKey(EntityElementalSpell.class, DataSerializers.STRING);
    private int damage;

    public EntityElementalSpell(World world) {
        super(world);
    }

    public EntityElementalSpell(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityElementalSpell(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void entityInit() {
        this.setEntityInvulnerable(true);
        this.setSize(0.1f, 0.1f);
        this.getDataManager().register(ASPECT, 0);
        this.getDataManager().register(CASTER, "");
        damage = 5;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setAspectId(int aspect) {
        this.getDataManager().set(ASPECT, aspect);
    }

    public Aspect getAspect() {
        return Aspects.getElementById(getDataManager().get(ASPECT));
    }

    public int getAspectId() {
        return this.getDataManager().get(ASPECT);
    }

    private void setCaster(String uuid) {
        this.getDataManager().set(CASTER, uuid);
        this.getDataManager().setDirty(CASTER);
    }

    public void setCaster(EntityLivingBase player) {
        if (player != null) {
            this.setCaster(player.getUniqueID().toString());
        } else {
            this.setCaster("");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("aspect", this.getAspectId());
        compound.setString("caster", this.getCasterUUID());
        compound.setInteger("damage", damage);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setAspectId(compound.getInteger("aspect"));
        this.setCaster(compound.getString("caster"));
        this.setDamage(compound.getInteger("damage"));
    }

    private String getCasterUUID() {
        return this.getDataManager().get(CASTER);
    }

    public void setThrower(EntityLivingBase thrower) {
        this.thrower = thrower;
    }

    @Override
    public void onUpdate() {
        if (this.ticksExisted > 600) {
            this.setDead();
        }

        if (world.isRemote && getAspect() != null) {
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posX, posY, posZ, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, getAspect().getColor(), 4, 40, 0, true, true, 0.8F, true));
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posX, posY, posZ, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, getAspect().getColor(), 4, 40, 0, true, true, 0.8F, true));
        }
        super.onUpdate();
    }


    @Nullable
    public EntityLivingBase getCaster() {
        String uuid = this.getCasterUUID();
        if ((uuid == null) || uuid.equals("")) {
            return null;
        }
        EntityLivingBase player = this.world.getPlayerEntityByUUID(UUID.fromString(uuid));
        if (player != null) {
            return player;
        }
        ArrayList<Entity> ent = new ArrayList<Entity>(this.world.getLoadedEntityList());
        for (Entity e : ent) {
            if ((e instanceof EntityLivingBase) && uuid.equals(e.getUniqueID().toString())) {
                return (EntityLivingBase) e;
            }
        }
        return null;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            //   EntityLivingBase caster = this.getCaster();
            if (getAspect() != null) {
                if ((result.typeOfHit != RayTraceResult.Type.ENTITY) || (result.entityHit != getThrower())) {
                    EntityLivingBase target;
                    if (result.entityHit instanceof EntityLivingBase) {
                        target = (EntityLivingBase) result.entityHit;
                        target.attackEntityFrom(new EntityDamageSourceIndirect(DamageSource.MAGIC.getDamageType(), this, getCaster()), getDamage());

                    } else {
                        return;
                    }
                }
                if ((result.typeOfHit == RayTraceResult.Type.BLOCK && world.getBlockState(result.getBlockPos()).getMaterial().blocksMovement())) {// && ((spell.getType() == EnumSpellType.PROJECTILE_BLOCK) || (spell.getType() == EnumSpellType.PROJECTILE_ALL))) {
                    this.setDead();
                }
                if ((result.typeOfHit == RayTraceResult.Type.ENTITY)) {
                    this.setDead();
                }

            }
        }
    }

    public void shoot(Entity shooter, float pitch, float yaw, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround) {
            this.motionY += shooter.motionY;
        }
    }

    @Override
    public void setDead() {
        super.setDead();
    }
}
