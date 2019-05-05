package de.aelpecyem.elementaristics.networking.tileentity.tick;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasInventory;
import de.aelpecyem.elementaristics.blocks.tileentity.IHasTickCount;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateTickTime implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateTickTime(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateTickTime(TileEntity te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateTickTime() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateTickTime, PacketUpdateTickTime> {

        @Override
        public PacketUpdateTickTime onMessage(PacketRequestUpdateTickTime message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.pos);
            if (te != null && te instanceof IHasTickCount) {
                return new PacketUpdateTickTime(te, ((IHasTickCount) te).getTickCount());
            } else {
                return null;
            }
        }

    }
}
