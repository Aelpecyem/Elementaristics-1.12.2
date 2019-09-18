package de.aelpecyem.elementaristics.networking.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateProtoplasmInventory implements IMessage {
    int entity;
    ItemStack item;

    public PacketUpdateProtoplasmInventory() {

    }

    public PacketUpdateProtoplasmInventory(EntityProtoplasm slime) {
        this.entity = slime.getEntityId();
        this.item = slime.inventory.getStackInSlot(0);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entity);
        ByteBufUtils.writeItemStack(buf, item);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entity = buf.readInt();
        item = ByteBufUtils.readItemStack(buf);
    }


    public static class Handler implements IMessageHandler<PacketUpdateProtoplasmInventory, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateProtoplasmInventory message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                Entity protoplasm = Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entity);
                if (protoplasm instanceof EntityProtoplasm) {
                    ((EntityProtoplasm) protoplasm).inventory.setStackInSlot(0, message.item);
                }
            });

            return null;
        }
    }
}
