package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ElementaristicsEssencesTab extends CreativeTabs {
    public ElementaristicsEssencesTab() {
        super(Elementaristics.MODID + "_essences");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.essence, 1, Aspects.magan.getId());
    }

}
