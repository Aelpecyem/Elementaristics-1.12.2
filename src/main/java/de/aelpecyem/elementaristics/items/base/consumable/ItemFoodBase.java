package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood implements IHasModel {
    protected String name;

    public ItemFoodBase(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setCreativeTab(Elementaristics.tab);
        ModItems.ITEMS.add(this);
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public void registerItemModel(Item itemBlock) {

    }


}
