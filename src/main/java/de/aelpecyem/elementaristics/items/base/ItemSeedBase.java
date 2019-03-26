package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSeedBase extends ItemSeeds implements IHasModel{
    protected String name;
    public ItemSeedBase(String name, Block crops, Block soil) {
        super(crops, soil);
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);
        ModItems.ITEMS.add(this);
    }


    @SideOnly(Side.CLIENT)
    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public void registerItemModel(Item itemBlock) {

    }


    @Override
    public Item setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
