package de.aelpecyem.elementaristics.networking.tileentity.altar;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateAltar implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateAltar(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateAltar(TileEntityAltar te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateAltar() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateAltar, PacketUpdateAltar> {

        @Override
        public PacketUpdateAltar onMessage(PacketRequestUpdateAltar message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityAltar te = (TileEntityAltar) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateAltar(te);
            } else {
                return null;
            }
        }

    }
}
