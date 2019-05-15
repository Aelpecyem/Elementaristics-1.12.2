package de.aelpecyem.elementaristics.networking.player;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SpellInit;
import de.aelpecyem.elementaristics.util.Keybinds;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import scala.util.control.Exception;

import java.util.UUID;

public class PacketPressSpellKey implements IMessage {
    String playerUUID;
    boolean up;

    public PacketPressSpellKey() {

    }

    public PacketPressSpellKey(EntityPlayer player, boolean up) {
        this.playerUUID = player.getUniqueID().toString();
        this.up = up;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerUUID);
        buf.writeBoolean(up);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerUUID = ByteBufUtils.readUTF8String(buf);
        this.up = buf.readBoolean();
    }


    public static class Handler implements IMessageHandler<PacketPressSpellKey, IMessage> {
        @Override
        public IMessage onMessage(PacketPressSpellKey message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = Elementaristics.proxy.getPlayer(ctx).world.getPlayerEntityByUUID(UUID.fromString(message.playerUUID));
                    if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).cycleSlot(!message.up);
                    }
                }
            });

            return null;
        }
    }
}
