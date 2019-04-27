package de.aelpecyem.elementaristics.networking.tileentity.energy.generatorCombustion;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityGeneratorArcaneCombustion;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateCombustionGenerator implements IMessage {
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateCombustionGenerator(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateCombustionGenerator(TileEntityPurifier te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateCombustionGenerator() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateCombustionGenerator, PacketUpdateCombustionGenerator> {

        @Override
        public PacketUpdateCombustionGenerator onMessage(PacketRequestUpdateCombustionGenerator message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension); //worldServerForDimension(message.dimension);
            TileEntityGeneratorArcaneCombustion te = (TileEntityGeneratorArcaneCombustion) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateCombustionGenerator(te);
            } else {
                return null;
            }
        }

    }
}
