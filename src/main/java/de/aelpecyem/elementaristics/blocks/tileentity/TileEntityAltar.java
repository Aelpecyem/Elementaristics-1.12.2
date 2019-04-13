package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockReactor;
import de.aelpecyem.elementaristics.capability.souls.Soul;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.SoulUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class TileEntityAltar extends TileEntity implements ITickable {

    public int tickCount;
    public boolean active;
    public String currentRite = "";
    Random random = new Random();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("tickCount", tickCount);
        compound.setBoolean("active", active);
        compound.setString("rite", currentRite);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        tickCount = compound.getInteger("tickCount");
        active = compound.getBoolean("active");
        currentRite = compound.getString("rite");
        super.readFromNBT(compound);
    }


    @Override
    public void update() {
        if (!currentRite.equals("")) {
            if (RiteInit.getRiteForResLoc(currentRite) != null) {
                RiteBase rite = RiteInit.getRiteForResLoc(currentRite);
                if (drainPlayers(rite)) {

                    if (tickCount % 20 == 0 && getCultistsInArea().size() < 4){
                   //     recruitCultists(getCultistsInArea().size() + 1); tweak that out a bit and add actual support of those
                    }
                    tickCount++;
                    List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                            new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                            new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
                    rite.onRitual(world, pos, targets, tickCount);

                } else {
                    doFailingShow();
                    currentRite = "";
                    tickCount = 0;
                }

                if (tickCount >= rite.getTicksRequired()) {
                    if (getAspectsInArea().containsAll(rite.getAspectsRequired())) {
                        if (getItemPowerInArea() >= rite.getItemPowerRequired()) {
                            if (rite.isSoulSpecific()) {
                                if (getSoulsInArea().contains(rite.getSoulRequired())) {
                                    if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15, false) != null) {
                                        rite.doMagic(world, pos, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, false));
                                        currentRite = "";
                                        consumeConsumables();
                                    }
                                }
                            } else {
                                if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15, false) != null) {
                                    consumeConsumables();
                                    rite.doMagic(world, pos, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, false));
                                    currentRite = "";

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
        }


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
                        System.out.println("Found cultist");
                        return true;
                    }
                }
                return false;
            }
        });
        if (!cultists.isEmpty()) {
            System.out.println("Processing Cultist");
            EntityCultist cultist = cultists.get(0);

            cultist.attemptTeleport(    pos.getX() + (3 * (spot < 3 ? -1 : 1)), pos.getY(), pos.getZ() + (3 * (spot % 2 == 0 ? -1 : 1)));
            System.out.println((pos.getX() + (3 * (spot < 3 ? -1 : 1)))  + " + " + (pos.getY()) + " + " + (pos.getZ() + (3 * (spot % 2 == 0 ? 1 : -1))));
            cultist.setSitting(true);
            cultist.getLookHelper().setLookPositionWithEntity(cultist, 45 + 90 * (spot - 1), 90);
        }

    }

    private void doFailingShow() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.125F, Math.abs(world.rand.nextGaussian()) * 0.125F, world.rand.nextGaussian() * 0.125F, 10092778, 3, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.1F, Math.abs(world.rand.nextGaussian()) * 0.1F, world.rand.nextGaussian() * 0.1F, 10092724, 1, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.075F, Math.abs(world.rand.nextGaussian()) * 0.075F, world.rand.nextGaussian() * 0.075F, 10092661, 1, 10, 0, true, false, 1F));
    }

    public Set<Soul> getSoulsInArea() {
        Set<Soul> souls = new HashSet<>();
        List<EntityPlayer> targets = getPlayersInArea();
        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                souls.add(SoulUtil.getSoulFromPlayer(player));
            }
        }
        return souls;
    }

    public boolean drainPlayers(RiteBase rite) {
        List<EntityPlayer> targets = getPlayersInArea();
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


        return power;
    }

    public List<EntityPlayer> getPlayersInArea() {
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
    }

    public List<EntityCultist> getCultistsInArea(){
        return world.getEntitiesWithinAABB(EntityCultist.class,
                new AxisAlignedBB(pos.getX() - 10, pos.getY() - 4,
                        pos.getZ() - 10, pos.getX() + 10, pos.getY() + 4, pos.getZ() + 10));

    }

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


        return aspects;
    }

    public void consumeConsumables() {
        List<EntityPlayer> targets = getPlayersInArea();

        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                    if (((IHasRiteUse) player.getHeldItemMainhand().getItem()).isConsumed()) {
                        player.getHeldItemMainhand().shrink(1);
                    }
                }
                if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                    if (((IHasRiteUse) player.getHeldItemOffhand().getItem()).isConsumed()) {
                        player.getHeldItemOffhand().shrink(1);
                    }
                }
            }
        }
    }

    public EnumFacing getBlockFacing() {
        return this.world.getBlockState(this.pos).getValue(BlockReactor.FACING);
    }

}



