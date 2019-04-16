package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemDrinkBase extends ItemFood implements IHasModel {
    protected String name;

    public ItemDrinkBase(String name) {
        super(0, 0, false);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setCreativeTab(Elementaristics.tab);
        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public void registerItemModel(Item itemBlock) {

    }

}
