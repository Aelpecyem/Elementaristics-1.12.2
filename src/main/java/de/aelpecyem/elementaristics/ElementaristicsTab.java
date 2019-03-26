package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ElementaristicsTab extends CreativeTabs {
    public ElementaristicsTab() {
        super(Elementaristics.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.cluster_aether);
    }

}
