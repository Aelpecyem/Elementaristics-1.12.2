package de.aelpecyem.elementaristics.misc.rites.ascension;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class RiteKnowledge extends RiteBase {

    public RiteKnowledge() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_knowledge"), 100, 0.5F, 8, Aspects.soul, Aspects.light);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (!caps.knowsSoul()) {
                if (!nexus.world.isRemote)
                PacketHandler.sendTo(player, new PacketMessage("message.know_" + SoulInit.getSoulFromId(caps.getSoulId()).getName()));

                Elementaristics.proxy.generateGenericParticles(player, 16777073, 4, 100, 0, false, true);
                caps.setKnowsSoul(true);
                SoulCaps.getCapForSoul(SoulInit.getSoulFromId(caps.getSoulId())).buffsOnSpawning(player, caps);
                if (!nexus.world.isRemote) {
                    CustomAdvancements.Advancements.ASCEND.trigger((EntityPlayerMP) player);
                }
                nexus.world.playSound(null, nexus.posX, nexus.posY, nexus.posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.AMBIENT, 1, 0.7F);
            }
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        if (nexus.getRiteTicks() % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, 16777073, 3, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, nexus.world.rand.nextGaussian() / 10, 0, nexus.world.rand.nextGaussian() / 10, 11513262, 3, 60, 0.01F, true, false);

            for (EntityPlayer player : nexus.getPlayersInArea(false)) {
                Elementaristics.proxy.generateGenericParticles(player, 16777073, 2, 100, 0, false, true);
            }
        }
    }
}
