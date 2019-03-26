package de.aelpecyem.elementaristics.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class OreBase extends BlockBase {
    public OreBase(String name, int harvestLevel) {
        super(Material.ROCK, name);

        setHarvestLevel("pickaxe", harvestLevel);
        setHardness(3f);
        setResistance(5f);

    }

    @Override
    public OreBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
