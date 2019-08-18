package de.aelpecyem.elementaristics.networking;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.networking.cap.CapabilityChunkSync;
import de.aelpecyem.elementaristics.networking.cap.CapabilitySync;
import de.aelpecyem.elementaristics.networking.cap.SpawnBoundParticles;
import de.aelpecyem.elementaristics.networking.other.PacketBurnableAffect;
import de.aelpecyem.elementaristics.networking.other.PacketDyeProtoplasm;
import de.aelpecyem.elementaristics.networking.player.PacketBaubleKeyWinged;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.networking.player.PacketMove;
import de.aelpecyem.elementaristics.networking.player.PacketPressSpellKey;
import de.aelpecyem.elementaristics.networking.tileentity.altar.PacketUpdateAltar;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.deities.PacketUpdateDeity;
import de.aelpecyem.elementaristics.networking.tileentity.energy.EnergySync;
import de.aelpecyem.elementaristics.networking.tileentity.energy.PacketUpdateEmulator;
import de.aelpecyem.elementaristics.networking.tileentity.energy.PacketUpdateTransmitter;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketUpdateInventory;
import de.aelpecyem.elementaristics.networking.tileentity.pos.PosSync;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper network;

    private static int nextId = 0;

    public static int next() {
        return nextId++;
    }
    public static void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Elementaristics.MODID);

        network.registerMessage(new PacketUpdateInventory.Handler(), PacketUpdateInventory.class, next(), Side.CLIENT);
        network.registerMessage(new PacketUpdateTickTime.Handler(), PacketUpdateTickTime.class, next(), Side.CLIENT);
        network.registerMessage(new EnergySync.Handler(), EnergySync.class, next(), Side.CLIENT);
        network.registerMessage(new PosSync.Handler(), PosSync.class, next(), Side.CLIENT);

        network.registerMessage(new PacketUpdateDeity.Handler(), PacketUpdateDeity.class, next(), Side.CLIENT);
        network.registerMessage(new PacketUpdateTransmitter.Handler(), PacketUpdateTransmitter.class, next(), Side.CLIENT);
        network.registerMessage(new PacketUpdateEmulator.Handler(), PacketUpdateEmulator.class, next(), Side.CLIENT);
        network.registerMessage(new PacketUpdateAltar.Handler(), PacketUpdateAltar.class, next(), Side.CLIENT);
        network.registerMessage(new PacketUpdateBasin.Handler(), PacketUpdateBasin.class, next(), Side.CLIENT);


        network.registerMessage(new CapabilitySync.Handler(), CapabilitySync.class, next(), Side.CLIENT);
        network.registerMessage(new CapabilityChunkSync.Handler(), CapabilityChunkSync.class, next(), Side.CLIENT);

        network.registerMessage(new SpawnBoundParticles.Handler(), SpawnBoundParticles.class, next(), Side.CLIENT);
        network.registerMessage(new PacketMessage.Handler(), PacketMessage.class, next(), Side.CLIENT);
        network.registerMessage(new PacketMove.Handler(), PacketMove.class, next(), Side.CLIENT);

        network.registerMessage(new PacketPressSpellKey.Handler(), PacketPressSpellKey.class, next(), Side.SERVER);
        network.registerMessage(new PacketDyeProtoplasm.Handler(), PacketDyeProtoplasm.class, next(), Side.SERVER);

        network.registerMessage(new PacketBaubleKeyWinged.Handler(), PacketBaubleKeyWinged.class, next(), Side.SERVER);
        network.registerMessage(new PacketBaubleKeyWinged.Handler(), PacketBaubleKeyWinged.class, next(), Side.CLIENT);

        network.registerMessage(new PacketBurnableAffect.Handler(), PacketBurnableAffect.class, next(), Side.SERVER);
        network.registerMessage(new PacketBurnableAffect.Handler(), PacketBurnableAffect.class, next(), Side.CLIENT);

    }

    public static void sendToAllLoaded(World world, BlockPos pos, IMessage message) {
        network.sendToAllTracking(message, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 0));
    }

    public static void sendToAllAround(World world, BlockPos pos, int range, IMessage message) {
        network.sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
    }

    public static void sendTo(EntityPlayer player, IMessage message) {
        network.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendToAllLoaded(Entity entity, IMessage message) {
        network.sendToAllTracking(message, entity);
    }

    public static void sendToAll(IMessage message) {
        network.sendToAll(message);
    }

    public static void sendToServer(IMessage message) {
        network.sendToServer(message);
    }

    public static void sendToDim(IMessage message, int dimensionId) {
        network.sendToDimension(message, dimensionId);
    }
}

