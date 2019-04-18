package de.aelpecyem.elementaristics.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

//the name is not exactly accurate
public interface IBlockHasModel {
    public void registerItemModel(Item itemBlock);

    public Item createItemBlock();
}
