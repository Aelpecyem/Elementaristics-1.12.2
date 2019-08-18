package de.aelpecyem.elementaristics.networking.other;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.items.base.burnable.ItemBurnableAffectingBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBurnableAffect implements IMessage {
    int entity;

    public PacketBurnableAffect() {

    }

    public PacketBurnableAffect(EntityItem item) {
        this.entity = item.getEntityId();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entity);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entity = buf.readInt();
    }


    public static class Handler implements IMessageHandler<PacketBurnableAffect, IMessage> {
        @Override
        public IMessage onMessage(PacketBurnableAffect message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                Entity e = Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entity);
                if (e instanceof EntityItem && ((EntityItem) e).getItem().getItem() instanceof ItemBurnableAffectingBase) {
                    ((ItemBurnableAffectingBase) ((EntityItem) e).getItem().getItem()).affect(e.world, e.posX, e.posY, e.posZ);
                }
            });

            return null;
        }
    }
}
