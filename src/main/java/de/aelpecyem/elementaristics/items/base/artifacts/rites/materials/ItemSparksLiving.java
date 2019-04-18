package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.items.base.consumable.ItemDrinkBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemSparksLiving extends ItemAspects implements IHasRiteUse {
    public ItemSparksLiving() {
        super("sparks_living", 6, true, "tooltip.sparks_living.name", Aspects.fire);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 2000;
    }
}
