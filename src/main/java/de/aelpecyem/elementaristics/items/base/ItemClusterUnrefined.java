package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemClusterUnrefined extends ItemBase {
    private static final String NBTKEY_WATER = "watertime";
    private ItemStack itemResult;

    public ItemClusterUnrefined(String name, ItemStack itemResult) {
        super(name);
        this.itemResult = itemResult;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem.isInWater()) {
            Elementaristics.proxy.generateGenericParticles(entityItem, Aspects.water.getColor(), 1, 100, 0, true, true);
            if (entityItem.getItem().hasTagCompound()) {
                if (entityItem.getItem().getTagCompound().hasKey(NBTKEY_WATER)) {
                    entityItem.getItem().getTagCompound().setInteger(NBTKEY_WATER, entityItem.getItem().getTagCompound().getInteger(NBTKEY_WATER) + 1);
                    if (entityItem.getItem().getTagCompound().getInteger(NBTKEY_WATER) > 100) {
                        entityItem.setItem(new ItemStack(itemResult.getItem(), entityItem.getItem().getCount(), itemResult.getMetadata()));
                    }
                } else {
                    entityItem.getItem().getTagCompound().setInteger(NBTKEY_WATER, 0);
                }
            } else {
                entityItem.getItem().setTagCompound(new NBTTagCompound());
                entityItem.getItem().getTagCompound().setInteger(NBTKEY_WATER, 0);
            }
        }
        return super.onEntityItemUpdate(entityItem);
    }
}
