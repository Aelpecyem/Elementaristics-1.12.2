package de.aelpecyem.elementaristics.blocks.base.crops;

import de.aelpecyem.elementaristics.init.ModItems;
import net.minecraft.item.Item;

public class CropOpium extends BlockCropBase {
    public CropOpium() {
        super("crop_opium", ModItems.seed_herb, ModItems.petal_opium, 4);
    }

    @Override
    protected Item getSeed() {
        return ModItems.seed_herb;
    }

    @Override
    protected Item getCrop() {
        return ModItems.petal_opium;
    }
}
