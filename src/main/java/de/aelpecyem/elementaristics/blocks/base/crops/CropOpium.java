package de.aelpecyem.elementaristics.blocks.base.crops;

import de.aelpecyem.elementaristics.init.ModItems;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class CropOpium extends BlockCropBase {
   public CropOpium() {
        super("crop_opium", ModItems.seed_herb, ModItems.petal_opium);
    }

    @Override
    protected Item getSeed() {
        return ModItems.seed_herb;
    }

    @Override
    protected Item getCrop() {
        return ModItems.petal_opium;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, ( 0.6000000238418579D / 7) * getAge(state) , 0.699999988079071D);
    }
}
