package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEssence extends ItemBase{

    public ItemEssence() {
        super("essence");
        this.setCreativeTab(Elementaristics.tab_essences);
        setHasSubtypes(true);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemModel() {
        for (int i = 0; i < ElementInit.getElements().size(); i++) {
            Elementaristics.proxy.registerItemRenderer(this, i, name);
        }
        super.registerItemModel();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "_" +ElementInit.getElementById(stack.getItemDamage()).getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)){
            for (int i = 0; i < ElementInit.getElements().size(); i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
