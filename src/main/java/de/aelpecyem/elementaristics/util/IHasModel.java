package de.aelpecyem.elementaristics.util;

import net.minecraft.item.Item;

public interface IHasModel {
    public void registerItemModel();
    public void registerItemModel(Item itemBlock);

}
