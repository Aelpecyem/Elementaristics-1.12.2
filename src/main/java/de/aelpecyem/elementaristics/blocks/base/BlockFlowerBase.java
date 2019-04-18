package de.aelpecyem.elementaristics.blocks.base;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.PotionEmotion;
import de.aelpecyem.elementaristics.util.IBlockHasModel;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class BlockFlowerBase extends BlockBush implements IBlockHasModel {
    protected String name;
    protected PotionEmotion emotion;

    public BlockFlowerBase(String name, PotionEmotion emotion) {
        super(Material.PLANTS);
        this.name = name;
        setSoundType(SoundType.PLANT);
        setUnlocalizedName(name);
        setRegistryName(name);
        this.emotion = emotion;
        this.setCreativeTab(Elementaristics.tab);
        ModBlocks.BLOCKS.add(this);
    }

    public BlockFlowerBase(String name, float lightLevel, PotionEmotion emotion) {
        super(Material.PLANTS);
        setHardness(6);
        setResistance(6);
        setLightLevel(lightLevel);
        this.name = name;
        this.emotion = emotion;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);
        ModBlocks.BLOCKS.add(this);
    }

    public PotionEmotion getEmotion() {
        return emotion;
    }

    public void registerItemModel(Item itemBlock) {
        Elementaristics.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    @Override
    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    /*public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }*/

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public BlockFlowerBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

}
