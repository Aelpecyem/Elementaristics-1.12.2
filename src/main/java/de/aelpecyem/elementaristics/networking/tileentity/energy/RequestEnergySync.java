package de.aelpecyem.elementaristics.networking.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RequestEnergySync implements IMessage {
    private BlockPos pos;
    private int dimension;

    public RequestEnergySync(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public RequestEnergySync(TileEntity te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public RequestEnergySync() {
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

    public static class Handler implements IMessageHandler<RequestEnergySync, EnergySync> {

        @Override
        public EnergySync onMessage(RequestEnergySync message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.pos);
            if (te != null && te instanceof TileEntityEnergy) {
                return new EnergySync((TileEntityEnergy) te, ((TileEntityEnergy) te).storage);
            } else {
                return null;
            }
        }

    }
}
