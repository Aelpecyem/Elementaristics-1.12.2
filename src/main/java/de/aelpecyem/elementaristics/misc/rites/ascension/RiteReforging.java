package de.aelpecyem.elementaristics.misc.rites.ascension;

import com.google.common.base.Predicate;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.materials.ItemFleshLamb;
import de.aelpecyem.elementaristics.items.base.consumable.ItemNectar;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.networking.player.PacketMove;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RiteReforging extends RiteBase {

    public RiteReforging() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_reforging"), 400, 0.5F, 12, Aspects.fire, Aspects.light, Aspects.body);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        if (player != null && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getPlayerAscensionStage() == 2 && player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionPotential))) {
                if (player.getHeldItemMainhand().getItem() instanceof ItemFleshLamb || player.getHeldItemOffhand().getItem() instanceof ItemFleshLamb) {
                    if (player.getHeldItemOffhand().getItem() instanceof ItemNectar || player.getHeldItemMainhand().getItem() instanceof ItemNectar) {
                        player.getHeldItemMainhand().shrink(1);
                        player.getHeldItemOffhand().shrink(1);
                        cap.setPlayerAscensionStage(3);
                        if (!world.isRemote) {
                            PacketHandler.sendTo(player, new PacketMessage("message.ascension_body.name"));
                            PacketHandler.sendTo(player, new PacketMove(0, 1, 0));
                        }
                        world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.5F, 1.1F);
                    }
                }

            }
        }

    }


    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        EntityPlayer player = world.getClosestPlayer(altarPos.getX() + 0.5, altarPos.getY() + 2.5, altarPos.getZ() + 0.5, 20, false);
        if (player != null) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                if (cap.getPlayerAscensionStage() == 2 && player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionPotential))) {
                    player.motionX = (altarPos.getX() + 0.5 - player.posX) / 20;
                    player.motionY = (altarPos.getY() + 1.5 - player.posY) / 20;
                    player.motionZ = (altarPos.getZ() + 0.5 - player.posZ) / 20;
                    player.rotationYaw += 0.1F;
                }
            }
        }
        if (world.isRemote) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + world.rand.nextFloat(), altarPos.getY() + 1F, altarPos.getZ() + world.rand.nextFloat(), 0, 0.02 + world.rand.nextFloat() / 50, 0, Aspects.fire.getColor(), 3, 60, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, altarPos.getX() + 0.5 + world.rand.nextGaussian(), altarPos.getY() + 3 + world.rand.nextGaussian(), altarPos.getZ() + 0.5 + world.rand.nextGaussian(), 0, 0, 0, Aspects.light.getColor(), 1, 200, 0, false, true, true, true, altarPos.getX() + 0.5F, altarPos.getY() + 2.5F, altarPos.getZ() + 0.5F));

        }

        //flame-ish particles
        //draw (closest) player to the center, but only if they are ascension stage 1 and have potential
        //add other particles considered that the player has all needed potion effects
    }

}
