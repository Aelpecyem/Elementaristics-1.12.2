package de.aelpecyem.elementaristics.networking.tileentity.pos;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneTransmitter;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RequestPosSync implements IMessage {
    private BlockPos pos;
    private int dimension;

    public RequestPosSync(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public RequestPosSync(TileEntity te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public RequestPosSync() {
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

    public static class Handler implements IMessageHandler<RequestPosSync, PosSync> {

        @Override
        public PosSync onMessage(RequestPosSync message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.pos);
            if (te != null && te instanceof IHasBoundPosition) {
                return new PosSync((TileEntityRedstoneTransmitter) te);
            } else {
                return null;
            }
        }

    }
}
