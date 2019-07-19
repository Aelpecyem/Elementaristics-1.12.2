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
import org.apache.commons.lang3.ArrayUtils;

public class PacketMessage implements IMessage {
    String transKey;
    boolean actionBar;

    public PacketMessage() {

    }

    public PacketMessage(String transKey, boolean actionBar) {
        this.transKey = transKey;
        this.actionBar = actionBar;
    }

    public PacketMessage(String transKey) {
        this.transKey = transKey;
        this.actionBar = false;
    }
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, transKey);
        buf.writeBoolean(actionBar);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        transKey = ByteBufUtils.readUTF8String(buf);
        actionBar = buf.readBoolean();
    }


    public static class Handler implements IMessageHandler<PacketMessage, IMessage> {
        @Override
        public IMessage onMessage(PacketMessage message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = Elementaristics.proxy.getPlayer(ctx);
                    player.sendStatusMessage(new TextComponentString(I18n.format(message.transKey)), message.actionBar);
                }
            });

            return null;
        }
    }
}
