package de.aelpecyem.elementaristics.networking.filterholder;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityFilterHolder;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPedestal;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateFilterHolder implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateFilterHolder(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateFilterHolder(TileEntityPedestal te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateFilterHolder() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateFilterHolder, PacketUpdateFilterHolder> {

        @Override
        public PacketUpdateFilterHolder onMessage(PacketRequestUpdateFilterHolder message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityFilterHolder te = (TileEntityFilterHolder) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateFilterHolder(te);
            } else {
                return null;
            }
        }

    }
}
