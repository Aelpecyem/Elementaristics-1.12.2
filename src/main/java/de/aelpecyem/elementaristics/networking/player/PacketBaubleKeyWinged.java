package de.aelpecyem.elementaristics.networking.player;

import baubles.api.BaublesApi;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketBaubleKeyWinged implements IMessage {
    String playerUUID;

    public PacketBaubleKeyWinged() {

    }

    public PacketBaubleKeyWinged(EntityPlayer uuid) {
        this.playerUUID = uuid.getUniqueID().toString();
    }

    public PacketBaubleKeyWinged(UUID uuid) {
        this.playerUUID = uuid.toString();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerUUID);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerUUID = ByteBufUtils.readUTF8String(buf);
    }


    public static class Handler implements IMessageHandler<PacketBaubleKeyWinged, IMessage> {
        @Override
        public IMessage onMessage(PacketBaubleKeyWinged message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = Elementaristics.proxy.getPlayer(ctx).world.getPlayerEntityByUUID(UUID.fromString(message.playerUUID));
                    ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaublesApi.isBaubleEquipped(player, ModItems.key_winged));
                    stack.getTagCompound().setFloat("charge", stack.getTagCompound().getFloat("charge") - 1F);
                }
            });

            return null;
        }
    }
}
