package de.aelpecyem.elementaristics.networking.tileentity.purifier;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdatePurifier implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdatePurifier(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdatePurifier(TileEntityPurifier te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdatePurifier() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdatePurifier, PacketUpdatePurifier> {

        @Override
        public PacketUpdatePurifier onMessage(PacketRequestUpdatePurifier message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityPurifier te = (TileEntityPurifier) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdatePurifier(te);
            } else {
                return null;
            }
        }

    }
}
