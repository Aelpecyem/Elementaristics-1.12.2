package de.aelpecyem.elementaristics.networking.tileentity.basin;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.ArrayUtils;
import scala.Int;

import java.util.ArrayList;
import java.util.List;

public class PacketUpdateBasin implements IMessage {
    private BlockPos pos;
    private List<Integer> aspects = new ArrayList<>();
    private int aspectsSize;
    private int fillCount;

    public PacketUpdateBasin(BlockPos pos, List<Integer> aspects, int fillCount) {
        this.pos = pos;
        this.aspectsSize = aspects.size();
        this.aspects = aspects;
        this.fillCount = fillCount;

    }

    public PacketUpdateBasin(TileEntityInfusionBasin te) {
        this(te.getPos(), te.aspectIDs, te.fillCount);
    }

    public PacketUpdateBasin() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(aspectsSize);
        for (int i : aspects) {
            buf.writeInt(i);
        }
        buf.writeInt(fillCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        aspectsSize = buf.readInt();
        aspects.clear();
        for (int i = 0; i < aspectsSize; i++) {

            aspects.add(buf.readInt());
        }
        fillCount = buf.readInt();

    }

    public static class Handler implements IMessageHandler<PacketUpdateBasin, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateBasin message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityInfusionBasin te = (TileEntityInfusionBasin) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.aspectIDs = message.aspects;
                    te.fillCount = message.fillCount;
                }
            });
            return null;
        }

    }
}
