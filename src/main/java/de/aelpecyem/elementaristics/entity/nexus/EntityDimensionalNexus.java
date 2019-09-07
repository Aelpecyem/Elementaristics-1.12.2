package de.aelpecyem.elementaristics.entity.nexus;

import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.entity.nexus.PacketSyncNexus;
import de.aelpecyem.elementaristics.util.CultUtil;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
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
    Map<Soul, Integer> soulsExt = new ConcurrentHashMap<>();
    Set<Aspect> aspects = new HashSet<>();
    Set<Soul> souls = new HashSet<>();
    List<EntityPlayer> players = new ArrayList<>();
    List<EntityCultist> cultists = new ArrayList<>();

    public static final int MAX_CULTISTS = 8;

    public EntityDimensionalNexus(World worldIn) {
        super(worldIn);
        setSize(1F, 1F);
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
            //something?
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    public void onEntityUpdate() {
        if (ticksExisted % Config.nexusUpdateInterval == 0) {
            getItemPowerInArea(true);
            getAspectsInArea(true);
            getPlayersInArea(true);
            getCultistsInArea(true);
            getSoulsInArea(true);
        }
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, getPosition(), 64, new PacketSyncNexus(this));
        }
        if (getRite() != null) {
            if (drainPower(getRite())) {
                setRiteTicks(getRiteTicks() + 1);
                getRite().onRitual(this);
            } else {
                setRiteString("");
                setRiteTicks(100);
                return;
            }
            if (isRiteComplete()) {
                getRite().doMagic(this);
                getRite().playSound(this);
                consumeConsumables();
                setRiteString("");
                setRiteTicks(0);
            }
        } else {
            if (getRiteTicks() > 0) {
                setRiteTicks(getRiteTicks() - 1);
            }
        }
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("riteTicks", this.getRiteTicks());
        compound.setString("rite", this.getRiteString());
        compound.setString("ownerUUID", this.getOwnerUUID().toString());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setRiteTicks(compound.getInteger("riteTicks"));
        this.setRiteString(compound.getString("rite"));
        this.setOwnerUUID(compound.getString("ownerUUID"));
    }

    public void suckInEntity(Entity entity, float yOffset) {
        entity.motionX = (posX - entity.posX) / 20;
        entity.motionY = (posY + 0.8F - entity.posY - yOffset) / 20;
        entity.motionZ = (posZ - entity.posZ) / 20;
    }

    public boolean isValidUser(EntityPlayer player) {
        return isOwner(player) || CultUtil.isCultMember(player, world.getPlayerEntityByUUID(getOwnerUUID()));
    }

    public boolean isRiteComplete() {
        if (getRiteTicks() >= getRite().getTicksRequired()) {
            if (getAspectsInArea(true).containsAll(getRite().getAspectsRequired())) {
                if (getItemPowerInArea(true) >= getRite().getItemPowerRequired()) {
                    return getRite().areSoulsValid(getSoulsInArea(true));
                }
            }
        }
        return false;
    }

    public void consumeConsumables() {
        List<EntityPlayer> targets = getPlayersInArea(false);
        RiteBase rite = getRite();
        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                for (ItemStack stack : PlayerUtil.getHeldItems(player)) {
                    if (stack.getItem() instanceof IHasRiteUse && ((IHasRiteUse) stack.getItem()).isConsumed()) {
                        for (Aspect aspect : ((IHasRiteUse) stack.getItem()).getAspects()) {
                            if (rite.getAspectsRequired().contains(aspect)) {
                                stack.shrink(1);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<Soul> getSoulsInArea(boolean refresh) {
        if (refresh) {
            souls.clear();
            List<EntityPlayer> targets = getPlayersInArea(false);
            if (targets.size() > 0) {
                Iterator iterator = targets.iterator();
                while (iterator.hasNext()) {
                    EntityPlayer player = (EntityPlayer) iterator.next();
                    if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null))
                        souls.add(player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoul());
                }
            }
            souls.addAll(soulsExt.keySet());
            refreshSoulsExt();
        }
        return souls;
    }

    public boolean drainPower(RiteBase rite) {
        List<EntityCultist> cultists = getCultistsInArea(false).size() < MAX_CULTISTS ? getCultistsInArea(false) : getCultistsInArea(false).subList(0, MAX_CULTISTS - 1);
        if (cultists.size() > 0) {
            Iterator iterator = cultists.iterator();
            while (iterator.hasNext()) {
                EntityCultist cultist = (EntityCultist) iterator.next();
                if (MaganUtil.drainMaganFromCultist(cultist, rite.getMaganDrainedPerTick(), 20, true)) {
                    return true;
                }
            }
        }
        List<EntityPlayer> targets = getPlayersInArea(false);
        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (MaganUtil.drainMaganFromPlayer(player, rite.getMaganDrainedPerTick(), 20, true)) {
                    return true;
                }
            }
        }

        return false;
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

            List<EntityCultist> cultists = getCultistsInArea(false).size() < MAX_CULTISTS ? getCultistsInArea(false) : getCultistsInArea(false).subList(0, MAX_CULTISTS - 1);
            if (cultists.size() > 0) {
                Iterator iterator = cultists.iterator();
                while (iterator.hasNext()) {
                    EntityCultist cultist = (EntityCultist) iterator.next();
                    aspects.add(cultist.getAspect());
                }
            }
            aspects.addAll(aspectsExt.keySet());
            refreshAspectsExt();
        }
        return aspects;
    }

    public List<EntityPlayer> getPlayersInArea(boolean refresh) {
        if (refresh) {
            players = world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().grow(10), (EntityPlayer p) -> isValidUser(p));
        }
        return players;
    }

    public List<EntityCultist> getCultistsInArea(boolean refresh) {
        if (refresh) {
            cultists = world.getEntitiesWithinAABB(EntityCultist.class, getEntityBoundingBox().grow(10), (EntityCultist p) -> p.getOwner() instanceof EntityPlayer && isValidUser((EntityPlayer) p.getOwner()));
        }
        return cultists;
    }

    //Aspects - External
    public void addAspect(Aspect aspect) {
        aspectsExt.put(aspect, 2);
    }

    //Souls - External
    public void addSoul(Soul soul) {
        soulsExt.put(soul, 2);
    }

    public void refreshAspectsExt() {
        for (Aspect aspect : aspectsExt.keySet()) {
            aspectsExt.replace(aspect, aspectsExt.get(aspect), aspectsExt.get(aspect) - 1);
            if (aspectsExt.get(aspect) <= 0) {
                aspectsExt.remove(aspect);
            }
        }
    }

    public void refreshSoulsExt() {
        for (Soul soul : soulsExt.keySet()) {
            soulsExt.replace(soul, aspectsExt.get(soul), soulsExt.get(soul) - 1);
            if (soulsExt.get(soul) <= 0) {
                soulsExt.remove(soul);
            }
        }
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


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().grow(10);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}
