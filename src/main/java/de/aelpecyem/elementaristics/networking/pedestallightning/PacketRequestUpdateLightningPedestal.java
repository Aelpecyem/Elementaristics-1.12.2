package de.aelpecyem.elementaristics.networking.pedestallightning;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityLightningPedestal;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateLightningPedestal implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateLightningPedestal(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateLightningPedestal(TileEntityPurifier te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateLightningPedestal() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateLightningPedestal, PacketUpdateLightningPedestal> {

        @Override
        public PacketUpdateLightningPedestal onMessage(PacketRequestUpdateLightningPedestal message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityLightningPedestal te = (TileEntityLightningPedestal) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateLightningPedestal(te);
            } else {
                return null;
            }
        }

    }
}
