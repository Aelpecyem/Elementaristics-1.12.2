package de.aelpecyem.elementaristics.networking.forge;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityForge;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateForge implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateForge(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateForge(TileEntityReactor te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateForge() {
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


    public static class Handler implements IMessageHandler<PacketRequestUpdateForge, PacketUpdateForge> {

        @Override
        public PacketUpdateForge onMessage(PacketRequestUpdateForge message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityForge te = (TileEntityForge) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateForge(te);
            } else {
                return null;
            }
        }

    }
}
