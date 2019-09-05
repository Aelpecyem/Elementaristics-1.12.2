package de.aelpecyem.elementaristics.items.base.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCultBadge extends ItemBase implements IBauble {
    protected final String OWNER = "owner";
    protected final String MEMBERS = "members";

    /*Todo: Cults
        As of now, this does not have any effect;
        however, it will work like this:
        A cult badge will have an expandable list of members; each member on the badge provides their cult services also for the person with the badge.
        The rite for the cult's expansion will take the Badges of both participants, and will add the owner of each to the other badge.
     */
    public ItemCultBadge() {
        super("badge_cult");
        maxStackSize = 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(ChatFormatting.GOLD + I18n.format("tooltip.badge.owner") + " " + ChatFormatting.DARK_PURPLE + PlayerUtil.getUsernameFromUUID(getOwnerUUID(stack)));
        if (!getMemberStrings(stack).isEmpty()) {
            tooltip.add(ChatFormatting.GOLD + I18n.format("tooltip.badge.members"));
            for (String member : getMemberStrings(stack)) {
                tooltip.add(ChatFormatting.LIGHT_PURPLE + "-" + PlayerUtil.getUsernameFromUUID(member));
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (getOwnerUUID(playerIn.getHeldItem(handIn)).isEmpty()) {
            setOwner(playerIn, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.TRINKET;
    }

    public boolean setUp(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (!stack.getTagCompound().hasKey(OWNER)) {
            stack.getTagCompound().setString(OWNER, "");
        }
        if (!stack.getTagCompound().hasKey(MEMBERS)) {
            stack.getTagCompound().setString(MEMBERS, "");
        }
        return true;
    }

    public void setOwner(EntityPlayer player, ItemStack stack) {
        if (setUp(stack)) {
            stack.getTagCompound().setString(OWNER, player.getUniqueID().toString());
        }
    }

    public String getOwnerUUID(ItemStack stack) {
        if (setUp(stack)) {
            return stack.getTagCompound().getString(OWNER);
        }
        return "";
    }

    public EntityPlayer getOwner(World world, ItemStack stack) {
        return world.getPlayerEntityByUUID(UUID.fromString(getOwnerUUID(stack)));
    }

    public List<String> getMemberStrings(ItemStack stack) {
        List<String> uuids = new ArrayList<>();
        String memberString = getMembersString(stack);
        for (String member : memberString.split(";")) {
            if (!member.isEmpty()) {
                uuids.add(member);
            }
        }
        return uuids;
    }

    public void setMemberString(ItemStack stack, EntityPlayer... players) {
        if (setUp(stack)) {
            for (EntityPlayer player : players) {
                stack.getTagCompound().setString(MEMBERS, getMembersString(stack) + player.getUniqueID().toString() + ";");
            }
        }
    }

    public void setMemberString(ItemStack stack, String string) {
        if (setUp(stack)) {
            stack.getTagCompound().setString(MEMBERS, string);
        }
    }

    public String getMembersString(ItemStack stack) {
        if (setUp(stack)) {
            return stack.getTagCompound().getString(MEMBERS);
        }
        return "";
    }
}
