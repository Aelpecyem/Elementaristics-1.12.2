package de.aelpecyem.elementaristics.networking.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasTickCount;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EnergySync implements IMessage {
    private BlockPos pos, posTo;
    private int maxEnergy, energyStored, maxExtract, maxReceive;
    private boolean receives;

    public EnergySync() {
        maxEnergy = 0;
        energyStored = 0;
        maxExtract = 0;
        maxReceive = 0;
        receives = false;
    }

    public EnergySync(TileEntityEnergy te, EnergyCapability cap) {
        this.pos = te.getPos();
        if (posTo == null) {
            posTo = pos;
        }
        posTo = te.posBound;
        receives = te.receives;

        maxEnergy = cap.getMaxEnergyStored();
        energyStored = cap.getEnergyStored();
        maxExtract = cap.getMaxExtract();
        maxReceive = cap.getMaxReceive();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        if (posTo == null) {
            posTo = pos;
        }
        buf.writeLong(posTo.toLong());
        buf.writeBoolean(receives);
        buf.writeInt(maxEnergy);
        buf.writeInt(energyStored);
        buf.writeInt(maxExtract);
        buf.writeInt(maxReceive);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        if (posTo == null) {
            posTo = pos;
        }
        posTo = BlockPos.fromLong(buf.readLong());
        receives = buf.readBoolean();
        maxEnergy = buf.readInt();
        energyStored = buf.readInt();
        maxExtract = buf.readInt();
        maxReceive = buf.readInt();
    }

    public static class Handler implements IMessageHandler<EnergySync, IMessage> {

        @Override
        public IMessage onMessage(EnergySync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null && te instanceof TileEntityEnergy) {
                    if (message.posTo == null)
                        ((TileEntityEnergy) te).setPositionBoundTo(message.pos);
                    else
                        ((TileEntityEnergy) te).setPositionBoundTo(message.posTo);
                    ((TileEntityEnergy) te).receives = message.receives;
                    ((TileEntityEnergy) te).storage.setMaxStorage(message.maxEnergy);
                    ((TileEntityEnergy) te).storage.setEnergy(message.energyStored);
                    ((TileEntityEnergy) te).storage.setMaxExtract(message.maxExtract);
                    ((TileEntityEnergy) te).storage.setMaxReceive(message.maxReceive);
                }
            });
            return null;
        }

    }
}
