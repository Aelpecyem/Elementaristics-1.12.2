package de.aelpecyem.elementaristics.blocks.base;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockEssence extends BlockBase {

    private int id;

    public BlockEssence(String name, int id) {
        super(Material.ROCK, name);

        this.name = name;
        this.id = id;
        setLightLevel(10);

        this.setCreativeTab(Elementaristics.tab_essences);

    }

    public void registerItemModel(Item itemBlock) {
        Elementaristics.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    public int getId() {
        return id;
    }

    @Override
    public BlockEssence setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
