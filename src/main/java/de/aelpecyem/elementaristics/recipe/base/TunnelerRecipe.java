package de.aelpecyem.elementaristics.recipe.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TunnelerRecipe {
    public final ResourceLocation name;
    public final Ingredient input;
    public final ItemStack output;
    public final List<ItemStack> filterItems;

    public TunnelerRecipe(ResourceLocation name, Ingredient input, ItemStack filterItem, ItemStack filterItem2, ItemStack filterItem3, ItemStack output) {
        this.name = name;
        this.input = input;
        List<ItemStack> itemList = new ArrayList<>();
        itemList.add(filterItem);
        itemList.add(filterItem2);
        itemList.add(filterItem3);
        this.filterItems = itemList;
        this.output = output;
    }
}
