package de.aelpecyem.elementaristics.networking.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMessage implements IMessage {
    String transKey;

    public PacketMessage() {

    }

    public PacketMessage(String transKey) {
        this.transKey = transKey;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, transKey);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        transKey = ByteBufUtils.readUTF8String(buf);
    }


    public static class Handler implements IMessageHandler<PacketMessage, IMessage> {
        @Override
        public IMessage onMessage(PacketMessage message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = Elementaristics.proxy.getPlayer(ctx);
                    player.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format(message.transKey)), false);
                }
            });

            return null;
        }
    }
}
