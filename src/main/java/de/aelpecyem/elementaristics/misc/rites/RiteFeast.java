package de.aelpecyem.elementaristics.misc.rites;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public class RiteFeast extends RiteBase {

    public RiteFeast() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_feast"), 400, 0.2F, 6, ElementInit.body, ElementInit.life);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 10F, pos.getY() - 4F, pos.getZ() - 10F),
                new BlockPos(pos.getX() + 10F, pos.getY() + 8F, pos.getZ() + 10F)));
        for (EntityPlayer playerAffected : targets) {
            playerAffected.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 5);
            playerAffected.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + 5);
            playerAffected.addPotionEffect(new PotionEffect(Potion.getPotionById(23), 8000, 1, false, false));
        }
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
        if (tickCount % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, 12863556, 3, 60, 0, false, false);
            for (EntityPlayer player : players) {
                Elementaristics.proxy.generateGenericParticles(player, 12863556, 1, 100, 0, false, true);
            }
        }
    }
}
