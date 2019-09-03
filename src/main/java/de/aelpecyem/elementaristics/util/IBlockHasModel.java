package de.aelpecyem.elementaristics.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

//the name is not exactly accurate
public interface IBlockHasModel {
    public void registerItemModel(Block itemBlock);

    public Item createItemBlock();
}
