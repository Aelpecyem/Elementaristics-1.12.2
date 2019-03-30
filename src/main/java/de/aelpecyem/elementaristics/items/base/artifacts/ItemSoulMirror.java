package de.aelpecyem.elementaristics.items.base.artifacts;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import java.util.List;

public class
ItemSoulMirror extends ItemAspects {
    public ItemSoulMirror() {
        super("soul_mirror", Aspects.soul, 6, false);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.mirror_soul.name"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {//give stats
        if (playerIn.dimension != Config.mindDimensionId) {
            if (playerIn.getActivePotionEffects().contains(playerIn.getActivePotionEffect(PotionInit.potionTrance))){
                playerIn.changeDimension(Config.mindDimensionId, new ITeleporter() {
                    @Override
                    public void placeEntity(World world, Entity entity, float yaw) {
                        entity.setPosition(entity.posX, 36, entity.posZ);
                    }
                });
            } else {
                if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                    IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                    SoulInit.updateSoulInformation(playerIn, cap);
                    if (worldIn.isRemote){
                        if (playerIn.isSneaking()) {

                        playerIn.sendMessage(new TextComponentString(I18n.format("message.view_stats.name") + " " + playerIn.getName()));


                        if (cap.knowsSoul()) {
                            playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD +I18n.format("message.soul_creative.name") + " "
                                    + SoulInit.getSoulFromId(cap.getSoulId()).getLocalizedName()));
                        } else {
                            playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD +I18n.format("message.soul_unknown.name")));
                        }
                        playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD +I18n.format("message.max_magan.name") + " "
                                + Math.ceil(cap.getMaxMagan())));

                        playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD +I18n.format("message.regen_per_tick.name") + " "
                                + cap.getMaganRegenPerTick()));

                        playerIn.sendMessage(new TextComponentString(ChatFormatting.GOLD +I18n.format("message.ascension_stage.name") + " " + cap.getPlayerAscensionStage()));
                    }
                    playerIn.sendMessage(new TextComponentString(ChatFormatting.BLUE +I18n.format("message.current_magan.name") + " "
                            + Math.ceil(cap.getMagan())));


                }}
            }
        } else {
            playerIn.changeDimension(playerIn.getSpawnDimension(), new ITeleporter() {
                @Override
                public void placeEntity(World world, Entity entity, float yaw) {
                    if (playerIn.bedLocation != null) {
                        entity.setPosition(playerIn.bedLocation.getX(), playerIn.bedLocation.getY(), playerIn.bedLocation.getZ());
                    }else{
                        for (int i = 0; i < 250; i++)
                        playerIn.attemptTeleport(playerIn.posX, 250 - i, playerIn.posZ);
                    }
                }
            });
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);

    }
}
