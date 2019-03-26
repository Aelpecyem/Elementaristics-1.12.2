package de.aelpecyem.elementaristics.misc.rites;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import de.aelpecyem.elementaristics.proxy.ClientProxy;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class RiteKnowledge extends RiteBase {

    public RiteKnowledge() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_knowledge"), 100, 0.5F, 8, ElementInit.soul, ElementInit.light);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (!caps.knowsSoul()) {
                if (world.isRemote) {
                    player.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.know_" + SoulInit.getSoulFromId(caps.getSoulId()).getName())), false);
                }
                Elementaristics.proxy.generateGenericParticles(player, 16777073, 4, 100, 0, false, true);
                caps.setKnowsSoul(true);
                SoulCaps.getCapForSoul(SoulInit.getSoulFromId(caps.getSoulId())).buffsOnSpawning(player, caps);
            }
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, 16777073, 3, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, world.rand.nextGaussian() / 10, 0, world.rand.nextGaussian() / 10, 11513262, 3, 60, 1, true, false);

            for (EntityPlayer player : players) {
                Elementaristics.proxy.generateGenericParticles(player, 16777073, 2, 100, 0, false, true);
            }
        }
    }
}
