package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockReactor;
import de.aelpecyem.elementaristics.capability.souls.Soul;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.SoulUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.*;

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
                if (drainPlayers(rite)){
                    tickCount++;
                    List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                            new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                            new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
                    rite.onRitual(world, pos, targets, tickCount);

                }else{
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
                            }else{
                                if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15, false) != null) {
                                    rite.doMagic(world, pos, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, false));
                                    currentRite = "";
                                    consumeConsumables();
                                }
                            }
                        }
                    }
                }

            } else {
                currentRite = "";
            }
        }else{
            tickCount = 0;
        }


    }

    private void doFailingShow() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.125F, Math.abs(world.rand.nextGaussian()) * 0.125F, world.rand.nextGaussian() * 0.125F, 10092778, 3, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.1F, Math.abs(world.rand.nextGaussian()) * 0.1F, world.rand.nextGaussian() * 0.1F, 10092724, 1, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.075F, Math.abs(world.rand.nextGaussian()) * 0.075F, world.rand.nextGaussian() * 0.075F, 10092661, 1, 10, 0, true, false, 1F));
    }

    public Set<Soul> getSoulsInArea() {
        Set<Soul> souls = new HashSet<>();
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
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
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
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
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));

        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                    power += ((IHasRiteUse) player.getHeldItemMainhand().getItem()).getPower();
                }
                if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                    power += ((IHasRiteUse) player.getHeldItemOffhand().getItem()).getPower();
                }
            }
        }


        return power;
    }

    public Set<Aspect> getAspectsInArea() {
        Set<Aspect> aspects = new HashSet<>();
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));

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

    public void consumeConsumables(){
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));

        if (targets.size() > 0) {
            Iterator iterator = targets.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (player.getHeldItemMainhand().getItem() instanceof IHasRiteUse) {
                   if (((IHasRiteUse) player.getHeldItemMainhand().getItem()).isConsumed()){
                       player.getHeldItemMainhand().shrink(1);
                   }
                }
                if (player.getHeldItemOffhand().getItem() instanceof IHasRiteUse) {
                    if (((IHasRiteUse) player.getHeldItemOffhand().getItem()).isConsumed()){
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



