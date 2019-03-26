package de.aelpecyem.elementaristics.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class OreDroppingBase extends BlockBase {
    private Item itemDropped;
    private int quantity;

    public OreDroppingBase(String name, Item itemDropped, int quantity, int harvestLevel) {
        super(Material.ROCK, name);
        this.quantity = quantity;
        this.itemDropped = itemDropped;
        setHarvestLevel("pickaxe", harvestLevel);
        setHardness(3f);
        setResistance(5f);

    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
        super.dropXpOnBlockBreak(worldIn, pos, 5);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return itemDropped;

    }

    @Override
    public int quantityDropped(Random random) {
        return quantity + random.nextInt(2);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0) {
            int j = random.nextInt(fortune) - 1;

            if (j < 0) {
                j = 0;
            }

            return quantityDropped(random) * (j + 1);
        } else {
            return quantityDropped(random);
        }
    }

    @Override
    public OreDroppingBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
