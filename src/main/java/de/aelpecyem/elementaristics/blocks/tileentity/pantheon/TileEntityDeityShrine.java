package de.aelpecyem.elementaristics.blocks.tileentity.pantheon;

import com.sun.org.apache.regexp.internal.RE;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityShrineBase;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.init.Deities;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.deities.PacketUpdateDeity;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import java.sql.Time;

public class TileEntityDeityShrine extends TileEntityEnergy implements ITickable {
    public int tickCount;
    public String deityBound = "";
    public boolean isStatue;
    public boolean unusedBool = false;
    public float unusedFloat = 0F;
    public int unusedInt = 0;
    public String unusedString = "";
    public BlockPos altarPos = null;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        compound.setInteger("tickCount", tickCount);

        compound.setString("deityBound", deityBound);
        compound.setBoolean("isStatue", isStatue);
        compound.setBoolean("unusedBool", unusedBool);
        compound.setFloat("unusedFloat", unusedFloat);
        compound.setInteger("unusedInt", unusedInt);
        compound.setString("unusedString", unusedString);

        if (altarPos == null) {
            altarPos = pos;
        }
        compound.setLong("altarPos", altarPos.toLong());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
        tickCount = compound.getInteger("tickCount");

        deityBound = compound.getString("deityBound");
        isStatue = compound.getBoolean("isStatue");

        unusedBool = compound.getBoolean("unusedBool");
        unusedFloat = compound.getFloat("unusedFloat");
        unusedInt = compound.getInteger("unusedInt");
        unusedString = compound.getString("unusedString");

        if (altarPos == null) {
            altarPos = pos;
        }
        altarPos = BlockPos.fromLong(compound.getLong("altarPos"));
        super.readFromNBT(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? (T) storage : super.getCapability(capability, facing);
    }


    @Override
    public void update() {
        super.update();

        if (deityBound == "") {
            deityBound = ((BlockDeityShrineBase) world.getBlockState(pos).getBlock()).deity.getName().toString();
            isStatue = ((BlockDeityShrineBase) world.getBlockState(pos).getBlock()).isStatue;
        }
        if (altarPos == null) {
            altarPos = pos;
        }
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateTickTime(this, tickCount));
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateDeity(this));
        }
        Deity deityActive = Deities.deities.get(new ResourceLocation(deityBound));
        if (deityActive != null) {
            if (TimeUtil.getTimeUnfalsified(world.getWorldTime()) >= deityActive.getTickTimeBegin() && TimeUtil.getTimeUnfalsified(world.getWorldTime()) <= deityActive.getTickTimeBegin() + 1000) {
                if (isStatue) {
                    deityActive.statueEffect(this);
                } else {
                    deityActive.symbolEffect(this);
                }
                doParticles(deityActive);
            } else {
                if (isSupplyingManipulatorBelow()) {
                    if (deityActive instanceof DeitySupplyEffectBase) {
                        if (storage.extractIfPossible(10)) {
                            ((DeitySupplyEffectBase) deityActive).supplyEffect(this);
                        }
                    }
                    doParticles(deityActive);
                }
                if (isPassiveEffectManipulatorBelow()) {
                    if (isStatue) {
                        deityActive.statueEffect(this);
                    } else {
                        deityActive.symbolEffect(this);
                    }
                }
            }
        }
    }

    public TileEntityAltar getAltarBound() {
        if (altarPos != null && !altarPos.equals(pos)) {
            if (world.getTileEntity(altarPos) != null && world.getTileEntity(altarPos) instanceof TileEntityAltar) {
                return (TileEntityAltar) world.getTileEntity(altarPos);
            }
        }
        return null;
    }

    public boolean isSupplyingManipulatorBelow() {
        if (world.getBlockState(pos.down()).getBlock() == ModBlocks.manipulator_supplying && world.isBlockPowered(pos.down())) {
            return true;
        }
        return false;
    }

    public boolean isPassiveEffectManipulatorBelow() {
        if (world.getBlockState(pos.down()).getBlock() == ModBlocks.manipulator_passive && world.isBlockPowered(pos.down())) {
            return true;
        }
        return false;
    }

    public void doParticles(Deity deity) {
        if (world.isRemote) {
            if (!posBound.equals(pos) && altarPos != null && altarPos != pos) {
                int hours = 24;

                double radius = 0.15F;
                double x = altarPos.getX() - radius / 2;
                double y = altarPos.getY() + 0.9;
                double z = altarPos.getZ() + 0.5F;

                for (float i = 0.5F; i < TimeUtil.getHourForTimeBegin(deity.getTickTimeBegin()) + 0.5; i++) { //replace i with hour
                    double angle = 2 * Math.PI * i / hours;
                    x += radius * Math.sin(angle);
                    z += radius * Math.cos(angle);
                    if (i == TimeUtil.getHourForTimeBegin(deity.getTickTimeBegin()) - 0.5)
                        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, x, y, z, 0, 0.01, 0, deity.getColor(), 0.45F + world.rand.nextFloat(), 30 + world.rand.nextInt(5), 0, false, false, 1, true));
                }
            }
        }
    }

    public Deity getDeityBound() {
        return Deities.deities.getOrDefault(new ResourceLocation(deityBound), null);
    }
}



