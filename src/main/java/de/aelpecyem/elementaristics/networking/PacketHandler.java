package de.aelpecyem.elementaristics.networking;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.networking.cap.CapabilitySync;
import de.aelpecyem.elementaristics.networking.tileentity.altar.PacketRequestUpdateAltar;
import de.aelpecyem.elementaristics.networking.tileentity.altar.PacketUpdateAltar;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketRequestUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.energy.EnergySync;
import de.aelpecyem.elementaristics.networking.tileentity.energy.RequestEnergySync;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketRequestUpdateInventory;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketUpdateInventory;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketRequestUpdateTickTime;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    private static SimpleNetworkWrapper network;

    public static void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Elementaristics.MODID);

        network.registerMessage(new PacketUpdateAltar.Handler(), PacketUpdateAltar.class, 1, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateAltar.Handler(), PacketRequestUpdateAltar.class, 2, Side.SERVER);

        network.registerMessage(new PacketUpdateBasin.Handler(), PacketUpdateBasin.class, 3, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateBasin.Handler(), PacketRequestUpdateBasin.class, 4, Side.SERVER);

        network.registerMessage(new PacketUpdateInventory.Handler(), PacketUpdateInventory.class, 5, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateInventory.Handler(), PacketRequestUpdateInventory.class, 6, Side.SERVER);

        network.registerMessage(new PacketUpdateTickTime.Handler(), PacketUpdateTickTime.class, 7, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateTickTime.Handler(), PacketRequestUpdateTickTime.class, 8, Side.SERVER);

        network.registerMessage(new EnergySync.Handler(), EnergySync.class, 9, Side.CLIENT);
        network.registerMessage(new RequestEnergySync.Handler(), RequestEnergySync.class, 10, Side.SERVER);

        network.registerMessage(new CapabilitySync.Handler(), CapabilitySync.class, 11, Side.CLIENT);
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
}

