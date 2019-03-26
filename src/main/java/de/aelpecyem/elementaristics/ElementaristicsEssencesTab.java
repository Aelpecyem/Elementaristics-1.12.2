package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import scala.xml.Elem;

public class ElementaristicsEssencesTab extends CreativeTabs {
    public ElementaristicsEssencesTab() {
        super(Elementaristics.MODID + "_essences");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.essence, 1, ElementInit.magan.getId());
    }

}
