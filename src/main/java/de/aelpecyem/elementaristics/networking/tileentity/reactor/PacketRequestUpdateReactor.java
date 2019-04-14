package de.aelpecyem.elementaristics.networking.tileentity.reactor;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateReactor implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateReactor(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateReactor(TileEntityReactor te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateReactor() {
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


    public static class Handler implements IMessageHandler<PacketRequestUpdateReactor, PacketUpdateReactor> {

        @Override
        public PacketUpdateReactor onMessage(PacketRequestUpdateReactor message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityReactor te = (TileEntityReactor) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateReactor(te);
            } else {
                return null;
            }
        }

    }
}
