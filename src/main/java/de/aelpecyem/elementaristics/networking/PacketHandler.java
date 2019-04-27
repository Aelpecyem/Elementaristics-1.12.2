package de.aelpecyem.elementaristics.networking;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.networking.cap.CapabilitySync;
import de.aelpecyem.elementaristics.networking.tileentity.altar.PacketRequestUpdateAltar;
import de.aelpecyem.elementaristics.networking.tileentity.altar.PacketUpdateAltar;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketRequestUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.concentrator.PacketRequestUpdateConcentrator;
import de.aelpecyem.elementaristics.networking.tileentity.concentrator.PacketUpdateConcentrator;
import de.aelpecyem.elementaristics.networking.tileentity.filterholder.PacketRequestUpdateFilterHolder;
import de.aelpecyem.elementaristics.networking.tileentity.filterholder.PacketUpdateFilterHolder;
import de.aelpecyem.elementaristics.networking.tileentity.pedestal.PacketRequestUpdatePedestal;
import de.aelpecyem.elementaristics.networking.tileentity.pedestal.PacketUpdatePedestal;
import de.aelpecyem.elementaristics.networking.tileentity.pedestallightning.PacketRequestUpdateLightningPedestal;
import de.aelpecyem.elementaristics.networking.tileentity.pedestallightning.PacketUpdateLightningPedestal;
import de.aelpecyem.elementaristics.networking.tileentity.purifier.PacketRequestUpdatePurifier;
import de.aelpecyem.elementaristics.networking.tileentity.purifier.PacketUpdatePurifier;
import de.aelpecyem.elementaristics.networking.tileentity.reactor.PacketRequestUpdateReactor;
import de.aelpecyem.elementaristics.networking.tileentity.reactor.PacketUpdateReactor;
import de.aelpecyem.elementaristics.networking.tileentity.tunneler.PacketRequestUpdateTunneler;
import de.aelpecyem.elementaristics.networking.tileentity.tunneler.PacketUpdateTunneler;
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

        network.registerMessage(new PacketUpdatePurifier.Handler(), PacketUpdatePurifier.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePurifier.Handler(), PacketRequestUpdatePurifier.class, 1, Side.SERVER);

        network.registerMessage(new PacketUpdateLightningPedestal.Handler(), PacketUpdateLightningPedestal.class, 2, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateLightningPedestal.Handler(), PacketRequestUpdateLightningPedestal.class, 3, Side.SERVER);

        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 4, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 5, Side.SERVER);

        network.registerMessage(new PacketUpdateConcentrator.Handler(), PacketUpdateConcentrator.class, 6, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateConcentrator.Handler(), PacketRequestUpdateConcentrator.class, 7, Side.SERVER);

        network.registerMessage(new PacketUpdateTunneler.Handler(), PacketUpdateTunneler.class, 8, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateTunneler.Handler(), PacketRequestUpdateTunneler.class, 9, Side.SERVER);

        network.registerMessage(new PacketUpdateFilterHolder.Handler(), PacketUpdateFilterHolder.class, 10, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateFilterHolder.Handler(), PacketRequestUpdateFilterHolder.class, 11, Side.SERVER);

        network.registerMessage(new PacketUpdateReactor.Handler(), PacketUpdateReactor.class, 12, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateReactor.Handler(), PacketRequestUpdateReactor.class, 13, Side.SERVER);

        network.registerMessage(new PacketUpdateAltar.Handler(), PacketUpdateAltar.class, 14, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateAltar.Handler(), PacketRequestUpdateAltar.class, 15, Side.SERVER);

        network.registerMessage(new PacketUpdateBasin.Handler(), PacketUpdateBasin.class, 16, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateBasin.Handler(), PacketRequestUpdateBasin.class, 17, Side.SERVER);

        network.registerMessage(new CapabilitySync.Handler(), CapabilitySync.class, 18, Side.CLIENT);
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

