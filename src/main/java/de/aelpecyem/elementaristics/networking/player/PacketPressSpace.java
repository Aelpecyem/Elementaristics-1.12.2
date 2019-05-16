package de.aelpecyem.elementaristics.networking.player;

import baubles.api.BaublesApi;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketPressSpace implements IMessage {
    String playerUUID;

    public PacketPressSpace() {

    }

    public PacketPressSpace(EntityPlayer player) {
        this.playerUUID = player.getUniqueID().toString();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerUUID);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerUUID = ByteBufUtils.readUTF8String(buf);
    }


    public static class Handler implements IMessageHandler<PacketPressSpace, IMessage> {
        @Override
        public IMessage onMessage(PacketPressSpace message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = (EntityPlayer) Elementaristics.proxy.getPlayer(ctx).world.getPlayerEntityByUUID(UUID.fromString(message.playerUUID));
                    if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null) && player.isAirBorne) {
                        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaublesApi.isBaubleEquipped(player, ModItems.key_winged));
                        stack.getTagCompound().setFloat("charge", stack.getTagCompound().getFloat("charge") - 1F);

                        player.motionY = 0;
                        player.fallDistance -= 10;
                        player.jumpMovementFactor += 0.1;
                        float yaw = player.rotationYaw;
                        float pitch = -36.000065F;
                        float f = 0.8F;// + (entityLivingBaseIn instanceof EntityPlayer ? ((EntityPlayer) entityLivingBaseIn).capabilities.getFlySpeed() : 0);
                        if (player.isSneaking()) {
                            player.motionX = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
                            player.motionZ = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
                        }
                        player.motionY = player.motionY + (double) (-MathHelper.sin((pitch) / 180.0F * (float) Math.PI) * f);
                    }
                }
            });

            return null;
        }
    }
}
