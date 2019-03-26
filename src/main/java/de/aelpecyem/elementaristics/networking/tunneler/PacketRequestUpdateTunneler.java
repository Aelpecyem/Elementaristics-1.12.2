package de.aelpecyem.elementaristics.networking.tunneler;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityTunneler;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateTunneler implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateTunneler(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateTunneler(TileEntityTunneler te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateTunneler() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateTunneler, PacketUpdateTunneler> {

        @Override
        public PacketUpdateTunneler onMessage(PacketRequestUpdateTunneler message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityTunneler te = (TileEntityTunneler) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateTunneler(te);
            } else {
                return null;
            }
        }

    }
}
