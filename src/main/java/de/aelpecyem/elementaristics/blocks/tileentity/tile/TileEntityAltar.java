package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityAltar extends TileEntity implements ITickable {

    public int tickCount;
    public String currentRite = "";
    Random random = new Random();
    int addedPower = 0;
    Map<Aspect, Integer> aspectsExt = new ConcurrentHashMap<>();
    Map<Soul, Integer> soulsExt = new ConcurrentHashMap<>();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("tickCount", tickCount);
        compound.setString("rite", currentRite);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        tickCount = compound.getInteger("tickCount");
        currentRite = compound.getString("rite");
        super.readFromNBT(compound);
    }

    //check IHasRiteUse for blocks, too
    @Override
    public void update() {
        /*if (world.provider.getWorldTime() % 1000 == 0) {
            aspectsExt.clear();
            addedPower = 0;
        }
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateTickTime(this, tickCount));
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateAltar(TileEntityAltar.this));
        }

        if (!currentRite.equals("")) {
            refreshExternallyAdded();
            if (RiteInit.getRiteForResLoc(currentRite) != null) {
                if (getCultistsInArea().size() < 5) {
                    RiteBase rite = RiteInit.getRiteForResLoc(currentRite);
                    if (drainPlayers(rite)) {
                        tickCount++;
                        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
                        rite.onRitual(world, pos, targets, tickCount, this);

                    } else {
                        if (world.isRemote)
                            doFailingShow();
                        currentRite = "";
                        tickCount = 0;
                        aspectsExt.clear();
                        soulsExt.clear();
                    }

                    if (tickCount >= rite.getTicksRequired()) {
                        if (getAspectsInArea().containsAll(rite.getAspectsRequired())) {
                            if (getItemPowerInArea() >= rite.getItemPowerRequired()) {
                                if (rite.isSoulSpecific()) {
                                    if (getSoulsInArea().contains(rite.getSoulRequired())) {
                                        if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15, false) != null) {
                                            rite.doMagic(world, pos, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, false), this);
                                            consumeConsumables();
                                            aspectsExt.clear();
                                            soulsExt.clear();
                                            currentRite = "";
                                        }
                                    }
                                } else {
                                    if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15, false) != null) {
                                        consumeConsumables();
                                        rite.doMagic(world, pos, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, false), this);
                                        aspectsExt.clear();
                                        soulsExt.clear();
                                        currentRite = "";
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                currentRite = "";
            }
        } else {
            tickCount = 0;
            aspectsExt.clear();
            soulsExt.clear();
            addedPower = 0;
        }*/
        //todo don't forget to keep the block for a while, so that the nexus can easily be obtained for some
    }

    public void recruitCultists(int spot) {
        List<EntityCultist> cultistsThere = getCultistsInArea();
        List<EntityCultist> cultists = world.getEntities(EntityCultist.class, new com.google.common.base.Predicate<EntityCultist>() {

            @Override
            public boolean apply(@Nullable EntityCultist input) {
                for (EntityPlayer player : getPlayersInArea()) {
                    if (input.isOwner(player)) {
                        for (EntityCultist cultistToTest : cultistsThere) {
                            if (input.getUniqueID().toString().equals(cultistToTest.getUniqueID().toString())) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        if (!cultists.isEmpty()) {
            EntityCultist cultist = null;
            for (EntityCultist entity : cultists) {
                if (RiteInit.getRiteForResLoc(currentRite) != null && RiteInit.getRiteForResLoc(currentRite).getAspectsRequired().contains(entity.getAspect())) {
                    cultist = entity;
                    break;
                }
            }
            if (cultist == null) {
                cultist = cultists.get(0);
            }

            cultist.attemptTeleport(pos.getX() + 0.5 + (3 * (spot < 3 ? -1 : 1)), pos.getY(), pos.getZ() + 0.5 + (3 * (spot % 2 == 0 ? -1 : 1)));
            cultist.getLookHelper().setLookPosition(pos.getX(), pos.getY(), pos.getZ(), cultist.getHorizontalFaceSpeed(), cultist.getVerticalFaceSpeed());
            //cultist.getLookHelper().setLookPositionWithEntity(cultist, 45 + 90 * (spot - 1), 90);
        }

    }

    private void doFailingShow() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.125F, Math.abs(world.rand.nextGaussian()) * 0.125F, world.rand.nextGaussian() * 0.125F, 10092778, 3, 10, 0, true, false, 1F, false));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.1F, Math.abs(world.rand.nextGaussian()) * 0.1F, world.rand.nextGaussian() * 0.1F, 10092724, 1, 10, 0, true, false, 1F, false));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.075F, Math.abs(world.rand.nextGaussian()) * 0.075F, world.rand.nextGaussian() * 0.075F, 10092661, 1, 10, 0, true, false, 1F, false));
    }

    public Set<Soul> getSoulsInArea() {
        Set<Soul> souls = new HashSet<>();
        List<EntityPlayer> targets = getPlayersInArea();
        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null))
                    souls.add(player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoul());
            }
        }
        souls.addAll(soulsExt.keySet());
        return souls;
    }

    public boolean drainPlayers(RiteBase rite) {
        List<EntityCultist> cultists = getCultistsInArea().size() < 4 ? getCultistsInArea() : getCultistsInArea().subList(0, 4);
        if (cultists.size() > 0) {
            Iterator iterator = cultists.iterator();
            while (iterator.hasNext()) {
                EntityCultist cultist = (EntityCultist) iterator.next();
                if (MaganUtil.drainMaganFromCultist(cultist, rite.getMaganDrainedPerTick(), 20, true)) {
                    if (tickCount % 5 == 0) {
                        if (world.isRemote) {
                            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, cultist.posX + cultist.world.rand.nextFloat() * cultist.width
                                    * 2.0F - cultist.width,
                                    cultist.posY + 0.5D + cultist.world.rand.nextFloat()
                                            * cultist.height,
                                    cultist.posZ + cultist.world.rand.nextFloat() * cultist.width
                                            * 2.0F - cultist.width, 0, 0, 0, cultist.getAspect().getColor(), 1, 100, 0, true, true, true, true, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F));
                        }
                    }
                    return true;
                }
            }
        }
        List<EntityPlayer> targets = getPlayersInArea();
        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (MaganUtil.drainMaganFromPlayer(player, rite.getMaganDrainedPerTick(), 20, true)) {
                    if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                        if (tickCount % 5 == 0) {
                            if (world.isRemote)
                                Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, player.posX + player.world.rand.nextFloat() * player.width
                                        * 2.0F - player.width,
                                        player.posY + 0.5D + player.world.rand.nextFloat()
                                                * player.height,
                                        player.posZ + player.world.rand.nextFloat() * player.width
                                                * 2.0F - player.width, 0, 0, 0, SoulInit.getSoulFromId(caps.getSoulId()).getParticleColor(), 1, 100, 0, true, true, true, true, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F));
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }

    //Participants
    public List<EntityPlayer> getPlayersInArea() {
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
    }

    public List<EntityCultist> getCultistsInArea() {
        return world.getEntitiesWithinAABB(EntityCultist.class,
                new AxisAlignedBB(pos.getX() - 10, pos.getY() - 4,
                        pos.getZ() - 10, pos.getX() + 10, pos.getY() + 8, pos.getZ() + 10));

    }

    //Power
    public int getItemPowerInArea() {
        int power = 0;
        List<EntityPlayer> targets = getPlayersInArea();
        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                RiteBase rite = RiteInit.getRiteForResLoc(currentRite);
                if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                    for (Aspect aspect : ((IHasRiteUse) player.getHeldItemMainhand().getItem()).getAspects()) {
                        if (rite.getAspectsRequired().contains(aspect)) {
                            power += ((IHasRiteUse) player.getHeldItemMainhand().getItem()).getPower();
                            break;
                        }
                    }
                }
                if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                    for (Aspect aspect : ((IHasRiteUse) player.getHeldItemOffhand().getItem()).getAspects()) {
                        if (rite.getAspectsRequired().contains(aspect)) {
                            power += ((IHasRiteUse) player.getHeldItemOffhand().getItem()).getPower();
                            break;
                        }
                    }
                }
            }
        }

        List<EntityCultist> cultists = getCultistsInArea();
        int cultistPower = 0;
        if (cultists.size() > 0) {
            Iterator iterator = cultists.iterator();
            while (iterator.hasNext()) {
                EntityCultist cultist = (EntityCultist) iterator.next();
                RiteBase rite = RiteInit.getRiteForResLoc(currentRite);
                if (rite.getAspectsRequired().contains(cultist.getAspect())) {
                    cultistPower += 4;
                }
                if (cultistPower >= 16) {
                    break;
                }
            }
        }
        power += cultistPower;
        int blockPower = 0;
        for (int i = 0; i < aspectsExt.keySet().size(); i++) {
            blockPower += 4;
            if (blockPower >= 20) {
                break;
            }
        }
        power += blockPower;
        return power;
    }


    //Aspects - External
    public void addAspect(Aspect aspect) {
        aspectsExt.put(aspect, 2);
    }

    //Souls - External
    public void addSoul(Soul soul) {
        soulsExt.put(soul, 2);
    }

    public void refreshExternallyAdded() {
        //subtract by one; if it reaches 0, then remove it
        for (Aspect aspect : aspectsExt.keySet()) {
            aspectsExt.replace(aspect, aspectsExt.get(aspect), aspectsExt.get(aspect) - 1);
            if (aspectsExt.get(aspect) <= 0) {
                aspectsExt.remove(aspect);
            }
        }
        for (Soul soul : soulsExt.keySet()) {
            soulsExt.replace(soul, soulsExt.get(soul), soulsExt.get(soul) - 1);
            if (soulsExt.get(soul) <= 0) {
                soulsExt.remove(soul);
            }
        }
    }


    //Aspects - All
    public Set<Aspect> getAspectsInArea() {
        Set<Aspect> aspects = new HashSet<>();
        List<EntityPlayer> targets = getPlayersInArea();

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

        List<EntityCultist> cultists = getCultistsInArea();
        if (cultists.size() > 0) {
            Iterator iterator = cultists.iterator();
            while (iterator.hasNext()) {
                EntityCultist cultist = (EntityCultist) iterator.next();
                aspects.add(cultist.getAspect());
            }
        }
        aspects.addAll(aspectsExt.keySet());

        return aspects;
    }

    /////Consumables
    public void consumeConsumables() {
        List<EntityPlayer> targets = getPlayersInArea();
        RiteBase rite = RiteInit.getRiteForResLoc(currentRite);
        if (targets.size() > 0) { //issue is that the rite returned is null, which is BAD
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                    if (((IHasRiteUse) player.getHeldItemMainhand().getItem()).isConsumed()) {
                        boolean flag = false;
                        for (Aspect aspect : ((IHasRiteUse) player.getHeldItemMainhand().getItem()).getAspects()) {
                            if (rite.getAspectsRequired().contains(aspect)) {
                                flag = true;
                            }
                        }
                        if (flag)
                            player.getHeldItemMainhand().shrink(1);
                    }
                }
                if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                    if (((IHasRiteUse) player.getHeldItemOffhand().getItem()).isConsumed()) {
                        boolean flag = false;
                        for (Aspect aspect : ((IHasRiteUse) player.getHeldItemOffhand().getItem()).getAspects()) {
                            if (rite.getAspectsRequired().contains(aspect)) {
                                flag = true;
                            }
                        }
                        if (flag)
                            player.getHeldItemOffhand().shrink(1);
                    }
                }
            }
        }
    }

}



