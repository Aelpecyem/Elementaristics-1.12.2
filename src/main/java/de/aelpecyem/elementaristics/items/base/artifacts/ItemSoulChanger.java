package de.aelpecyem.elementaristics.items.base.artifacts;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSoulChanger extends ItemBase {
    public ItemSoulChanger() {
        super("changer_soul");
        setMaxStackSize(1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).normalize(playerIn, cap);
            if (playerIn.isSneaking()) {
                if (worldIn.isRemote) {
                    playerIn.sendMessage(new TextComponentString(I18n.format("message.soul_creative.name") + " "
                            + SoulInit.getSoulFromId(cap.getSoulId()).getLocalizedName()));
                }
            }

            if (!playerIn.isSneaking()) {
                if (cap.getSoulId() > 0) {
                    cap.setSoulId(cap.getSoulId() - 1);
                } else if (cap.getSoulId() == 0) {
                    cap.setSoulId(SoulInit.souls.size() - 1);
                }
                if(worldIn.isRemote) {
                    playerIn.sendMessage(new TextComponentString(I18n.format("message.soul_switch_creative.name") + " "
                            + SoulInit.getSoulFromId(cap.getSoulId()).getLocalizedName()));
                }
            }

            SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).normalize(playerIn, cap);
            SoulInit.updateSoulInformation(playerIn, cap);
        }


        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
