package de.aelpecyem.elementaristics.networking.concentrator;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateConcentrator implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateConcentrator(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateConcentrator(TileEntityConcentrator te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateConcentrator() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateConcentrator, PacketUpdateConcentrator> {

        @Override
        public PacketUpdateConcentrator onMessage(PacketRequestUpdateConcentrator message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityConcentrator te = (TileEntityConcentrator) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateConcentrator(te);
            } else {
                return null;
            }
        }

    }
}
