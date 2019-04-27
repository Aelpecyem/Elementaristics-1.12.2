package de.aelpecyem.elementaristics.networking.tileentity.basin;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateBasin implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateBasin(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateBasin(TileEntityInfusionBasin te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateBasin() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateBasin, PacketUpdateBasin> {

        @Override
        public PacketUpdateBasin onMessage(PacketRequestUpdateBasin message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityInfusionBasin te = (TileEntityInfusionBasin) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateBasin(te);
            } else {
                return null;
            }
        }

    }
}
