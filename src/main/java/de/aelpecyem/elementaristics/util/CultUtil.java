package de.aelpecyem.elementaristics.util;

import baubles.api.BaublesApi;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.bauble.ItemCultBadge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CultUtil {
    public static boolean isCultMember(EntityPlayer player1, EntityPlayer player2) {
        List<EntityPlayer> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        for (EntityPlayer player : players) {
            if (player != null) {
                int b = BaublesApi.isBaubleEquipped(player1, ModItems.badge_cult);
                if (b > -1) {
                    ItemStack itemBadge = BaublesApi.getBaublesHandler(player).getStackInSlot(b);
                    if (itemBadge.getItem() instanceof ItemCultBadge) {
                        if (ModItems.badge_cult.getMemberStrings(itemBadge).contains(player.getUniqueID().toString()) || ModItems.badge_cult.getOwnerUUID(itemBadge).equals(player.getUniqueID().toString())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
