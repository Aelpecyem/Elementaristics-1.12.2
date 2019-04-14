package de.aelpecyem.elementaristics.networking.cap;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilitySync implements IMessage{
    private int soulId;
    private int ticksLeftStunted;
    private boolean knowsSoul;
    private float maxMagan;
    private float currentMagan;
    private float maganRegenPerTick;
    private int ascensionStage;
    private int cultistCount;

    public CapabilitySync(){
        this.soulId = 0;
        this.ticksLeftStunted = 0;
        this.knowsSoul = false;
        this.maxMagan = 0;
        this.currentMagan = 0;
        this.maganRegenPerTick = 0;
        this.ascensionStage = 0;
        this.cultistCount = 0;
    }

    public CapabilitySync(IPlayerCapabilities cap) {
        this.soulId = cap.getSoulId();
        this.ticksLeftStunted = cap.getTimeStunted();
        this.knowsSoul = cap.knowsSoul();
        this.maxMagan = cap.getMaxMagan();
        this.currentMagan = cap.getMagan();
        this.maganRegenPerTick = cap.getMaganRegenPerTick();
        this.ascensionStage = cap.getPlayerAscensionStage();
        this.cultistCount = cap.getCultistCount();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(soulId);
        buf.writeInt(ticksLeftStunted);
        buf.writeBoolean(knowsSoul);
        buf.writeFloat(maxMagan);
        buf.writeFloat(currentMagan);
        buf.writeFloat(maganRegenPerTick);
        buf.writeInt(ascensionStage);
        buf.writeInt(cultistCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        soulId = buf.readInt();
        ticksLeftStunted = buf.readInt();
        knowsSoul = buf.readBoolean();
        maxMagan = buf.readFloat();
        currentMagan = buf.readFloat();
        maganRegenPerTick = buf.readFloat();
        ascensionStage = buf.readInt();
        cultistCount = buf.readInt();
    }

    public static class Handler implements IMessageHandler<CapabilitySync, IMessage> {

        @Override
        public IMessage onMessage(CapabilitySync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerCapabilities c = Minecraft.getMinecraft().player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                c.setSoulId(message.soulId);
                c.stuntMagan(message.ticksLeftStunted);
                c.setKnowsSoul(message.knowsSoul);
                c.setMaxMagan(message.maxMagan);
                c.setMagan(message.currentMagan);
                c.setMaganRegenPerTick(message.maganRegenPerTick);
                c.setPlayerAscensionStage(message.ascensionStage);
                c.setCultistCount(message.cultistCount);
            });
            return null;
        }

    }
}
