package de.aelpecyem.elementaristics.items.base.artifacts;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class
ItemSoulMirror extends ItemAspects {
    public static final String TIMER_TAG = "timer";
    public ItemSoulMirror() {
        super("mirror_soul", 6, false, Aspects.soul);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.mirror_soul.name"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {//give statselse {
        if (handIn == EnumHand.MAIN_HAND){
            if (playerIn.getHeldItem(EnumHand.OFF_HAND).isItemEqual(new ItemStack(ModItems.gem_arcane)) && worldIn.provider.isDaytime() && !worldIn.isRaining()){
                ItemStack stackThis = playerIn.getHeldItem(handIn);//.getItem()//.getDefaultInstance();
                if (stackThis.hasTagCompound() && stackThis.getTagCompound().hasKey(TIMER_TAG)){
                    if (playerIn.getActivePotionEffects().contains(playerIn.getActivePotionEffect(PotionInit.laughter)) && MaganUtil.drainMaganFromPlayer(playerIn, 0.8F, 100, false)) {
                        stackThis.getTagCompound().setInteger(TIMER_TAG, stackThis.getTagCompound().getInteger(TIMER_TAG) + 1);
                        if (worldIn.isRemote){
                            for (int i = 0; i < 3; i++)
                                Elementaristics.proxy.generateGenericParticles(worldIn, playerIn.posX + (playerIn.width / 2) + worldIn.rand.nextGaussian() * 3, playerIn.posY + (playerIn.height / 2) + worldIn.rand.nextGaussian() * 3, playerIn.posZ + (playerIn.width / 2) + worldIn.rand.nextGaussian() * 3, 0, 0, 0, Aspects.light.getColor(), 1F + worldIn.rand.nextFloat(), 40, 0, false, true, true, (float) playerIn.posX + playerIn.width / 2, (float) playerIn.posY + playerIn.height / 2, (float) playerIn.posZ + playerIn.width / 2);
                        }
                        if (stackThis.getTagCompound().getInteger(TIMER_TAG) > 40){
                            playerIn.getHeldItem(EnumHand.OFF_HAND).shrink(1);
                            ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(ModItems.reflection_sun), 0);
                            stackThis.getTagCompound().setInteger(TIMER_TAG, 0);
                            playerIn.removePotionEffect(PotionInit.laughter);
                        }
                        return ActionResult.newResult(EnumActionResult.SUCCESS, stackThis);
                    }
                    else stackThis.getTagCompound().setInteger(TIMER_TAG, 0);
                }else{
                    stackThis.setTagCompound(new NBTTagCompound());
                    stackThis.getTagCompound().setInteger(TIMER_TAG, 0);
                }
            }
        }
        if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            SoulInit.updateSoulInformation(playerIn, cap);
            if (!worldIn.isRemote){
                CustomAdvancements.Advancements.ASCEND.trigger((EntityPlayerMP) playerIn);
            }
            if (worldIn.isRemote) {
                if (playerIn.isSneaking()) {
                    playerIn.sendMessage(new TextComponentString(I18n.format("message.view_stats.name") + " " + playerIn.getName()));

                    if (cap.knowsSoul()) {
                        playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.soul_creative.name") + " "
                                + SoulInit.getSoulFromId(cap.getSoulId()).getLocalizedName()));
                    } else {
                        playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.soul_unknown.name")));
                    }
                    playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.max_magan.name") + " "
                            + Math.ceil(cap.getMaxMagan())));

                    playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.regen_per_tick.name") + " "
                            + cap.getMaganRegenPerTick()));

                    playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.ascension_stage.name") + " " + cap.getPlayerAscensionStage()));

                    playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.cultist_count.name") + " " + cap.getCultistCount()));
                }
                playerIn.sendMessage(new TextComponentString(ChatFormatting.BLUE + I18n.format("message.current_magan.name") + " "
                        + Math.ceil(cap.getMagan())));


            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);

    }
}
