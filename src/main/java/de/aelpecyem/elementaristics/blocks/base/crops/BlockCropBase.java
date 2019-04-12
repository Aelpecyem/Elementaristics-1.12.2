package de.aelpecyem.elementaristics.blocks.base.crops;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.init.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
public class BlockCropBase extends BlockCrops {
    protected String name;
    Item seed, crop;

    public BlockCropBase(String name, Item seed, Item crop) {
        this.name = name;
        this.seed = seed;
        this.crop = crop;

        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);
        ModBlocks.BLOCKS.add(this);
    }


    @Override
    protected Item getSeed() {
        return seed;
    }

    @Override
    protected Item getCrop() {
        return crop;
    }
}
