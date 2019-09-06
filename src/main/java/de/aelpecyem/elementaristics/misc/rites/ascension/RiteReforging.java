package de.aelpecyem.elementaristics.misc.rites.ascension;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.materials.ItemFleshLamb;
import de.aelpecyem.elementaristics.items.base.consumable.ItemNectar;
import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.networking.player.PacketMove;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class RiteReforging extends RiteBase {

    public RiteReforging() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_reforging"), 400, 0.5F, 12, Aspects.fire, Aspects.light, Aspects.body);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getPlayerAscensionStage() == 2 && player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionPotential))) {
                if (player.getHeldItemMainhand().getItem() instanceof ItemFleshLamb || player.getHeldItemOffhand().getItem() instanceof ItemFleshLamb) {
                    if (player.getHeldItemOffhand().getItem() instanceof ItemNectar || player.getHeldItemMainhand().getItem() instanceof ItemNectar) {
                        player.getHeldItemMainhand().shrink(1);
                        player.getHeldItemOffhand().shrink(1);
                        cap.setPlayerAscensionStage(3);
                        if (!nexus.world.isRemote) {
                            PacketHandler.sendTo(player, new PacketMessage("message.ascension_body.name"));
                            PacketHandler.sendTo(player, new PacketMove(0, 1, 0));
                        }
                        nexus.world.playSound(null, nexus.posX, nexus.posY, nexus.posZ, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.5F, 1.1F);
                        if (!nexus.world.isRemote) {
                            CustomAdvancements.Advancements.ASCEND.trigger((EntityPlayerMP) player);
                        }
                    }
                }

            }
        }

    }


    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                if (cap.getPlayerAscensionStage() == 2 && player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionPotential))) {
                    player.motionX = (nexus.posX - player.posX) / 20;
                    player.motionY = (nexus.posY - player.posY) / 20;
                    player.motionZ = (nexus.posZ - player.posZ) / 20;
                    player.rotationYaw += 0.1F;
                }
            }
        }
        if (nexus.world.isRemote) {
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX + nexus.world.rand.nextGaussian(), nexus.posY + 1F, nexus.posZ + nexus.world.rand.nextGaussian(), 0, 0.02 + nexus.world.rand.nextFloat() / 50, 0, Aspects.fire.getColor(), 3, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(nexus.world, nexus.posX + nexus.world.rand.nextGaussian(), nexus.posY + nexus.world.rand.nextGaussian(), nexus.posZ + nexus.world.rand.nextGaussian(), 0, 0, 0, Aspects.light.getColor(), 1, 200, 0, false, true, true, true, nexus.posX, nexus.posY, nexus.posZ));

        }

        //flame-ish particles
        //draw (closest) player to the center, but only if they are ascension stage 1 and have potential
        //add other particles considered that the player has all needed potion effects
    }

}
