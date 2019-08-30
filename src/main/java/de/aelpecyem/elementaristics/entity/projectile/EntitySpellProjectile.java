package de.aelpecyem.elementaristics.entity.projectile;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.SpellInit;
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
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

//yup, grabbed that code from https://github.com/zabi94/Covens-reborn/blob/master/src/main/java/com/covens/common/entity/EntitySpellCarrier.java
public class EntitySpellProjectile extends EntityThrowable {
    private static final DataParameter<String> SPELL = EntityDataManager.<String>createKey(EntitySpellProjectile.class, DataSerializers.STRING);
    private static final DataParameter<String> CASTER = EntityDataManager.<String>createKey(EntitySpellProjectile.class, DataSerializers.STRING);

    public EntitySpellProjectile(World world) {
        super(world);
    }

    public EntitySpellProjectile(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntitySpellProjectile(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void entityInit() {
        this.setEntityInvulnerable(true);
        // setInvisible(true);
        this.setSize(0.1f, 0.1f);
        this.getDataManager().register(SPELL, "");
        this.getDataManager().register(CASTER, "");
    }

    public void setSpell(SpellBase spell) {
        this.setSpell(spell.getName());
    }

    private void setSpell(ResourceLocation spell) {
        this.getDataManager().set(SPELL, spell.toString());
        this.getDataManager().setDirty(SPELL);
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
        compound.setString("spell", this.getSpellName());
        compound.setString("caster", this.getCasterUUID());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setSpell(new ResourceLocation(compound.getString("spell")));
        this.setCaster(compound.getString("caster"));
    }

    private String getSpellName() {
        return this.getDataManager().get(SPELL);
    }

    @Nullable
    public SpellBase getSpell() {
        return SpellInit.spells.get(new ResourceLocation(this.getSpellName()));
    }


    private String getCasterUUID() {
        return this.getDataManager().get(CASTER);
    }

    public void setThrower(EntityLivingBase thrower) {
        this.thrower = thrower;
    }

    @Override
    public void onUpdate() {
        if (getSpell() != null && getSpell().getType() == SpellBase.SpellType.WAVE)
            this.setNoGravity(true);
        if (getSpell() != null) {
            if (getSpell().getType() == SpellBase.SpellType.EDEMA) {
                if (this.ticksExisted > 4) {
                    this.setDead();
                }
            } else if (getSpell().getType() == SpellBase.SpellType.WAVE) {
                if (this.ticksExisted > 20) {
                    this.setDead();
                }
            } else {
                if (this.ticksExisted > 600) {
                    this.setDead();
                }
            }
        } else if (this.ticksExisted > 600) {
            this.setDead();
        }

        if (world.isRemote && getSpell() != null) {
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posX, posY, posZ, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, getSpell().getColor(), 4, 40, 0, true, true, 0.8F, true));
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posX, posY, posZ, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, getSpell().getColor2(), 4, 40, 0, true, true, 0.8F, true));
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
            SpellBase spell = this.getSpell();
            //   EntityLivingBase caster = this.getCaster();
            if (spell != null) {
                if ((result.typeOfHit != RayTraceResult.Type.ENTITY) || (result.entityHit != getThrower())) {
                    //  drinkEffect(result, caster, this.world);
                    spell.affect(result, getThrower(), this.world, this);
                }
                if ((result.typeOfHit == RayTraceResult.Type.BLOCK && world.getBlockState(result.getBlockPos()).getMaterial().blocksMovement())) {// && ((spell.getType() == EnumSpellType.PROJECTILE_BLOCK) || (spell.getType() == EnumSpellType.PROJECTILE_ALL))) {
                    this.setDead();
                }
                if ((result.typeOfHit == RayTraceResult.Type.ENTITY) && spell.getType() != SpellBase.SpellType.WAVE) {// && ((spell.getType() == EnumSpellType.PROJECTILE_ENTITY) || (spell.getType() == EnumSpellType.PROJECTILE_ALL)) && (result.entityHit != caster)) {
                    this.setDead();
                }

            }
        }
    }

    public void shoot(Entity shooter, float pitch, float yaw, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);

        if (getSpell() != null && getSpell().getType() == SpellBase.SpellType.EDEMA) {
            f /= 2;
            f1 /= 2;
            f2 /= 2;
        }
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
   /* public void drinkEffect(RayTraceResult result, EntityLivingBase caster, World world){
        EntityLivingBase target;
        if (result.entityHit instanceof EntityLivingBase){
            target = (EntityLivingBase) result.entityHit;
            target.attackEntityFrom(DamageSource.MAGIC, 100);
        }else {
            return;
        }
    }*/
}
