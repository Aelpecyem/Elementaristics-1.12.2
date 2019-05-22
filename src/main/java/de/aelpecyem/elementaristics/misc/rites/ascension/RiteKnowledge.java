package de.aelpecyem.elementaristics.misc.rites.ascension;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public class RiteKnowledge extends RiteBase {

    public RiteKnowledge() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_knowledge"), 100, 0.5F, 8, Aspects.soul, Aspects.light);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (!caps.knowsSoul()) {
                PacketHandler.sendTo(player, new PacketMessage("message.know_" + SoulInit.getSoulFromId(caps.getSoulId()).getName()));
                // if (world.isRemote)
                //         player.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.know_" + SoulInit.getSoulFromId(caps.getSoulId()).getName())), false);

                Elementaristics.proxy.generateGenericParticles(player, 16777073, 4, 100, 0, false, true);
                caps.setKnowsSoul(true);
                SoulCaps.getCapForSoul(SoulInit.getSoulFromId(caps.getSoulId())).buffsOnSpawning(player, caps);
                world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.AMBIENT, 1, 0.7F);
            }
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, 16777073, 3, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, world.rand.nextGaussian() / 10, 0, world.rand.nextGaussian() / 10, 11513262, 3, 60, 1, true, false);

            for (EntityPlayer player : players) {
                Elementaristics.proxy.generateGenericParticles(player, 16777073, 2, 100, 0, false, true);
            }
        }
    }
}
