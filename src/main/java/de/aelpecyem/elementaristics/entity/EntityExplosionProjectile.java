package de.aelpecyem.elementaristics.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.SpellInit;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class EntityExplosionProjectile extends EntityThrowable {
    private static final DataParameter<String> THROWER = EntityDataManager.<String>createKey(EntityExplosionProjectile.class, DataSerializers.STRING);

    public EntityExplosionProjectile(World world) {
        super(world);
    }

    public EntityExplosionProjectile(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityExplosionProjectile(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void entityInit() {
        this.setEntityInvulnerable(true);
        this.setSize(0.1f, 0.1f);
        this.getDataManager().register(THROWER, "");
    }

    private void setCaster(String uuid) {
        this.getDataManager().set(THROWER, uuid);
        this.getDataManager().setDirty(THROWER);
    }

    public void setCaster(EntityLivingBase player) {
        ignoreEntity = player;
        if (player != null) {
            this.setCaster(player.getUniqueID().toString());
        } else {
            this.setCaster("");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("caster", this.getCasterUUID());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCaster(compound.getString("caster"));
    }


    private String getCasterUUID() {
        return this.getDataManager().get(THROWER);
    }

    public void setThrower(EntityLivingBase thrower) {
        this.thrower = thrower;
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
    public void onUpdate() {
        if (world.isRemote) {
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posX, posY, posZ, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, Aspects.fire.getColor(), 4, 40, 0, true, true, 0.8F));
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posX, posY, posZ, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, Aspects.chaos.getColor(), 4, 40, 0, true, true, 0.8F));
        }
        super.onUpdate();
    }


    @Override
    protected void onImpact(RayTraceResult result) {

        if ((result.typeOfHit != RayTraceResult.Type.ENTITY) || (result.entityHit != getCaster())) {
            affect();
            this.setDead();
        }
        if ((result.typeOfHit == RayTraceResult.Type.BLOCK && world.getBlockState(result.getBlockPos()).getMaterial().blocksMovement())) {
            affect();
            this.setDead();
        }
          /*  if(result.typeOfHit != RayTraceResult.Type.BLOCK || !world.getBlockState(result.getBlockPos()).getMaterial().blocksMovement()) {
                Explosion explosion = new Explosion(world, this, this.posX, this.posY, this.posZ, 3, false, false);
                explosion.doExplosionA();
                explosion.doExplosionB(true);
                this.setDead();
            }*/
    }

    public void affect() {
        Explosion explosion = new Explosion(world, this, this.posX, this.posY, this.posZ, 3, false, false);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
    }

    @Override
    public void setDead() {
        super.setDead();
    }

}
