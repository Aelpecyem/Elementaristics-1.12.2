package de.aelpecyem.elementaristics.items.base.artifacts;


import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.capability.chunk.ChunkCapProvider;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.materials.ItemWineRedmost;
import de.aelpecyem.elementaristics.items.base.consumable.ItemFoodBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.cap.SpawnBoundParticles;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemHeartHuman extends ItemFoodBase implements IHasRiteUse {

    public ItemHeartHuman() {
        super("heart_human", 10, 10, true);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        super.onUsingTick(stack, player, count);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (MaganUtil.drainMaganFromPlayer(playerIn, 40, 60, true)) {
            if (worldIn.getChunkFromBlockCoords(playerIn.getPosition()).hasCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null)) {
                if (worldIn.isRemote) {
                    if (worldIn.getChunkFromBlockCoords(playerIn.getPosition()).getCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null).getInfluence() != null) {
                        playerIn.sendMessage(new TextComponentString(ChatFormatting.DARK_RED + I18n.format("message.sense_influence") + " " + worldIn.getChunkFromBlockCoords(playerIn.getPosition()).getCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null).getInfluence().getLocalizedName() + " " + ChatFormatting.DARK_RED + I18n.format("message.sense_influence_end")));
                    } else {
                        playerIn.sendMessage(new TextComponentString(ChatFormatting.DARK_RED + I18n.format("message.sense_influence_none")));
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public List<Aspect> getAspects() {
        List<Aspect> aspects = new ArrayList<>();
        aspects.add(Aspects.body);
        return aspects;
    }

    @Override
    public int getPower() {
        return 4;
    }

    @Override
    public boolean isConsumed() {
        return false;
    }


}