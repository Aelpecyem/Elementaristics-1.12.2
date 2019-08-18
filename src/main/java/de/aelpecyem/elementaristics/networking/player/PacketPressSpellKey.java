package de.aelpecyem.elementaristics.networking.player;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketPressSpellKey implements IMessage {
    String playerUUID;
    byte keyValue;

    public PacketPressSpellKey() {

    }

    public PacketPressSpellKey(EntityPlayer player, byte keyValue) {
        this.playerUUID = player.getUniqueID().toString();
        this.keyValue = keyValue;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerUUID);
        buf.writeByte(keyValue);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerUUID = ByteBufUtils.readUTF8String(buf);
        this.keyValue = buf.readByte();
    }


    public static class Handler implements IMessageHandler<PacketPressSpellKey, IMessage> {
        @Override
        public IMessage onMessage(PacketPressSpellKey message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = Elementaristics.proxy.getPlayer(ctx).world.getPlayerEntityByUUID(UUID.fromString(message.playerUUID));
                    if (message.keyValue == 0 || message.keyValue == 1) {
                        if (player.getHeldItemMainhand().getItem() instanceof ItemThaumagral && ((ItemThaumagral) player.getHeldItemMainhand().getItem()).isTuned(player.getHeldItemMainhand())) {
                            ((ItemThaumagral) player.getHeldItemMainhand().getItem()).cycleMode(player.getHeldItemMainhand(), message.keyValue == 1);
                        } else if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                            player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).cycleSlot(message.keyValue == 1);
                        }
                    } else if (message.keyValue == 2) {
                        if (player.getHeldItemMainhand().getItem() instanceof ItemThaumagral) {
                            if (player.isSneaking() && ((ItemThaumagral) player.getHeldItemMainhand().getItem()).isTuned(player.getHeldItemMainhand())) {
                                ((ItemThaumagral) player.getHeldItemMainhand().getItem()).setTaskString(player.getHeldItemMainhand(), "");
                                ((ItemThaumagral) player.getHeldItemMainhand().getItem()).setWIPTask(player.getHeldItemMainhand(), "0");
                                ((ItemThaumagral) player.getHeldItemMainhand().getItem()).setTuned(player.getHeldItemMainhand(), false);
                                if (!player.world.isRemote) {
                                    PacketHandler.sendTo(player, new PacketMessage("message.reset_tasks"));
                                }
                            } else {
                                ((ItemThaumagral) player.getHeldItemMainhand().getItem()).setTuned(player.getHeldItemMainhand(), !((ItemThaumagral) player.getHeldItemMainhand().getItem()).isTuned(player.getHeldItemMainhand()));
                            }
                        }
                    }
                }
            });

            return null;
        }
    }
}
