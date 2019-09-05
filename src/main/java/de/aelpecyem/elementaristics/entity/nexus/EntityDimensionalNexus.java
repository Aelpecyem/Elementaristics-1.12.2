package de.aelpecyem.elementaristics.entity.nexus;

import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.util.CultUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EntityDimensionalNexus extends Entity {
    //protected static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityDimensionalNexus.class, DataSerializers.VARINT);

    protected static final DataParameter<String> RITE_ACTIVE = EntityDataManager.<String>createKey(EntityDimensionalNexus.class, DataSerializers.STRING);
    protected static final DataParameter<Integer> TICKS = EntityDataManager.<Integer>createKey(EntityDimensionalNexus.class, DataSerializers.VARINT);

    protected static final DataParameter<String> OWNER_UUID = EntityDataManager.<String>createKey(EntityDimensionalNexus.class, DataSerializers.STRING);
    int itemPower = 0;
    Map<Aspect, Integer> aspectsExt = new ConcurrentHashMap<>(); //todo, handle external aspects
    Set<Aspect> aspects = new HashSet<>();
    List<EntityPlayer> players = new ArrayList<>();
    List<EntityCultist> cultists = new ArrayList<>();

    public EntityDimensionalNexus(World worldIn) {
        super(worldIn);
        setSize(1.5F, 1.5F);
        setNoGravity(true);
    }

    protected void entityInit() {
        dataManager.register(RITE_ACTIVE, "");
        dataManager.register(TICKS, 0);
        dataManager.register(OWNER_UUID, "");
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public boolean hitByEntity(Entity entityIn) {
        if (entityIn instanceof EntityLivingBase && (isOwner((EntityLivingBase) entityIn) || (entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).isCreative()))) {
            if (entityIn.isSneaking()) {
                if (!world.isRemote) {
                    dropItem(ModItems.nexus_dimensional, 1);
                    setDead();
                    return true;
                }
            }
        }
        return super.hitByEntity(entityIn);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (isValidUser(player) && player.isSneaking()) {
            recruitCultists();
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    public void onEntityUpdate() {
        if (getRite() != null && ticksExisted % Config.nexusUpdateInterval == 0) {
            getItemPowerInArea(true);
            getAspectsInArea(true);
            getPlayersInArea(true);
            getCultistsInArea(true);
        }
        //particles
        super.onEntityUpdate();
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("riteTicks", this.getRiteTicks());
        compound.setString("rite", this.getRiteString());//todo write task stuff to nbt
        compound.setString("ownerUUID", this.getOwnerUUID().toString());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setRiteTicks(compound.getInteger("riteTicks"));
        this.setRiteString(compound.getString("rite"));
        this.setOwnerUUID(compound.getString("ownerUUID"));
    }

    public boolean isValidUser(EntityPlayer player) {
        return isOwner(player) || CultUtil.isCultMember(player, world.getPlayerEntityByUUID(getOwnerUUID()));
    }

    public void recruitCultists() {
        //todo fill that
    }

    public int getItemPowerInArea(boolean refresh) {
        if (refresh && getRite() != null) {
            itemPower = 0;
            List<EntityPlayer> targets = getPlayersInArea(false);
            if (targets.size() > 0) {
                Iterator iterator = targets.iterator();
                while (iterator.hasNext()) {
                    EntityPlayer player = (EntityPlayer) iterator.next();
                    RiteBase rite = getRite();
                    if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                        for (Aspect aspect : ((IHasRiteUse) player.getHeldItemMainhand().getItem()).getAspects()) {
                            if (rite.getAspectsRequired().contains(aspect)) {
                                itemPower += ((IHasRiteUse) player.getHeldItemMainhand().getItem()).getPower();
                                break;
                            }
                        }
                    }
                    if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                        for (Aspect aspect : ((IHasRiteUse) player.getHeldItemOffhand().getItem()).getAspects()) {
                            if (rite.getAspectsRequired().contains(aspect)) {
                                itemPower += ((IHasRiteUse) player.getHeldItemOffhand().getItem()).getPower();
                                break;
                            }
                        }
                    }
                }
            }

            List<EntityCultist> cultists = getCultistsInArea(false);
            int cultistPower = 0;
            if (cultists.size() > 0) {
                Iterator iterator = cultists.iterator();
                while (iterator.hasNext()) {
                    EntityCultist cultist = (EntityCultist) iterator.next();
                    RiteBase rite = getRite();
                    if (rite.getAspectsRequired().contains(cultist.getAspect())) {
                        cultistPower += 4;
                    }
                    if (cultistPower >= 16) {
                        break;
                    }
                }
            }
            itemPower += cultistPower;
            int blockPower = 0;
            for (int i = 0; i < aspectsExt.keySet().size(); i++) {
                blockPower += 4;
                if (blockPower >= 20) {
                    break;
                }
            }
            itemPower += blockPower;
        }
        return itemPower;
    }

    public Set<Aspect> getAspectsInArea(boolean refresh) { //only refresh now and then, ever second maybe?
        if (refresh && getRite() != null) {
            aspects = new HashSet<>();
            List<EntityPlayer> targets = getPlayersInArea(false);

            if (targets.size() > 0) {
                Iterator iterator = targets.iterator();
                while (iterator.hasNext()) {
                    EntityPlayer player = (EntityPlayer) iterator.next();
                    if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                        aspects.addAll(((IHasRiteUse) player.getHeldItemMainhand().getItem()).getAspects());
                    }
                    if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                        aspects.addAll(((IHasRiteUse) player.getHeldItemOffhand().getItem()).getAspects());
                    }
                }
            }

            List<EntityCultist> cultists = getCultistsInArea(false);
            if (cultists.size() > 0) {
                Iterator iterator = cultists.iterator();
                while (iterator.hasNext()) {
                    EntityCultist cultist = (EntityCultist) iterator.next();
                    aspects.add(cultist.getAspect());
                }
            }
            aspects.addAll(aspectsExt.keySet());
        }
        return aspects;
    }

    public List<EntityPlayer> getPlayersInArea(boolean refresh) {
        if (refresh) {
            players = world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().grow(10), (EntityPlayer p) -> isValidUser(p));
        }
        return players;
    }

    List<EntityCultist> getCultistsInArea(boolean refresh) {
        if (refresh) {
            cultists = world.getEntitiesWithinAABB(EntityCultist.class, getEntityBoundingBox().grow(10), (EntityCultist p) -> p.getOwner() instanceof EntityPlayer && isValidUser((EntityPlayer) p.getOwner()));
        }
        return cultists;
    }

    public RiteBase getRite() {
        return RiteInit.getRiteForResLoc(getRiteString());
    }

    public int getRiteTicks() {
        return dataManager.get(TICKS);
    }

    public void setRiteTicks(int ticks) {
        dataManager.set(TICKS, ticks);
    }

    public String getRiteString() {
        return dataManager.get(RITE_ACTIVE);
    }

    public void setRiteString(String riteString) {
        dataManager.set(RITE_ACTIVE, riteString);
    }

    public boolean isOwner(EntityLivingBase livingBase) {
        return livingBase.getUniqueID().equals(getOwnerUUID());
    }

    public UUID getOwnerUUID() {
        return UUID.fromString(dataManager.get(OWNER_UUID));
    }

    public void setOwnerUUID(UUID uuid) {
        dataManager.set(OWNER_UUID, uuid.toString());
    }

    public void setOwnerUUID(String uuid) {
        dataManager.set(OWNER_UUID, uuid);
    }


}
