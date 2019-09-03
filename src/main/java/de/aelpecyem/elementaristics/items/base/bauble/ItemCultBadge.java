package de.aelpecyem.elementaristics.items.base.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import net.minecraft.item.ItemStack;

public class ItemCultBadge extends ItemBase implements IBauble {
    protected final String MEMBERS = "members";

    public ItemCultBadge() {
        super("badge_cult");
        maxStackSize = 1;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.TRINKET;
    }
}
