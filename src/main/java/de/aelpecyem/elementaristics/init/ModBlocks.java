package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.blocks.base.*;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockCropBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossEverchanging;
import de.aelpecyem.elementaristics.blocks.base.crops.CropOpium;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.*;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy.BlockGeneratorCombustion;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy.BlockStorage;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.IBlockHasModel;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModBlocks {

    public static List<Block> BLOCKS = new ArrayList<>();

    //CROPS
    public static BlockCropBase crop_opium;
    //"DECOR"
    public static BlockBase stone_enriched;
    public static BlockPedestal pedestal;

    //MATTER MANIPULATION
    public static BlockPurifier purifier;
    public static BlockConcentrator concentrator;
    public static BlockTunneler tunneler_top;
    public static BlockFilterHolder filter_holder;
    public static BlockReactor reactor;
    public static BlockAltar altar;
    public static BlockBasin basin;

    //OTHERS
    public static BlockBase essence_casing;
    public static BlockBase fabric_reason;
    public static BlockBase fabric_passion;
    public static BlockSilverThread block_silver_thread;

    //ENERGY
    public static BlockGeneratorCombustion generator_combustion;
    public static BlockStorage energy_storage;

    //PLANTS
    public static BlockBush flower_ecstasy;
    public static BlockBush flower_contentment;
    public static BlockBush flower_dread;
    public static BlockBush flower_fear;
    public static BlockBush flower_laughter;
    public static BlockBush flower_silence;

    public static BlockMossBase mossBase;
    public static BlockMossBase mossEverchanging;

    public static BlockBush mushroom_intoxicating;

    public static void init() {
        crop_opium = new CropOpium();
        stone_enriched = new BlockBase(Material.ROCK, "stone_enriched");
        pedestal = new BlockPedestal();
        purifier = new BlockPurifier();
        concentrator = new BlockConcentrator();
        tunneler_top = new BlockTunneler();
        filter_holder = new BlockFilterHolder();
        reactor = new BlockReactor();
        essence_casing = new BlockBase(Material.ROCK, "casing_essence");
        altar = new BlockAltar();
        basin = new BlockBasin();
        fabric_reason = new BlockBase(Material.ROCK, "fabric_reason");
        fabric_passion = new BlockBase(Material.ROCK, "fabric_passion");
        block_silver_thread = new BlockSilverThread();

        flower_ecstasy = new BlockFlowerBase("flower_ecstasy", PotionInit.ecstasy);
        flower_contentment = new BlockFlowerBase("flower_contentment", PotionInit.contentment);
        flower_dread = new BlockFlowerBase("flower_dread", PotionInit.dread);
        flower_fear = new BlockFlowerBase("flower_fear", PotionInit.fear);
        flower_laughter = new BlockFlowerBase("flower_laughter", PotionInit.laughter);
        flower_silence = new BlockFlowerBase("flower_silence", PotionInit.silence);

        mossBase = new BlockMossBase("moss", true);
        mossEverchanging = new BlockMossEverchanging();

        generator_combustion = new BlockGeneratorCombustion();
        energy_storage = new BlockStorage();
        //TC
        mushroom_intoxicating = new BlockMushroomIntoxicating();
    }


    public static void register(IForgeRegistry<Block> registry) {

        for (Block blockBase : BLOCKS) {
            registry.register(blockBase);


        }

        registry.registerAll(

        );
        GameRegistry.registerTileEntity(pedestal.getTileEntityClass(), pedestal.getRegistryName());
        GameRegistry.registerTileEntity(purifier.getTileEntityClass(), purifier.getRegistryName());
        GameRegistry.registerTileEntity(concentrator.getTileEntityClass(), concentrator.getRegistryName());
        GameRegistry.registerTileEntity(tunneler_top.getTileEntityClass(), tunneler_top.getRegistryName());
        GameRegistry.registerTileEntity(filter_holder.getTileEntityClass(), filter_holder.getRegistryName());
        GameRegistry.registerTileEntity(reactor.getTileEntityClass(), reactor.getRegistryName());
        GameRegistry.registerTileEntity(altar.getTileEntityClass(), altar.getRegistryName());
        GameRegistry.registerTileEntity(basin.getTileEntityClass(), basin.getRegistryName());

        GameRegistry.registerTileEntity(generator_combustion.getTileEntityClass(), generator_combustion.getRegistryName());
        GameRegistry.registerTileEntity(energy_storage.getTileEntityClass(), energy_storage.getRegistryName());

    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {

        for (Block blockBase : BLOCKS) {
            if (blockBase instanceof IBlockHasModel)
                registry.register(((IBlockHasModel) blockBase).createItemBlock());

        }

        registry.registerAll(

        );
    }

    public static void registerModels() {
        for (Block blockBase : BLOCKS) {
            if (blockBase instanceof IBlockHasModel) {
                ((IBlockHasModel) blockBase).registerItemModel(Item.getItemFromBlock(blockBase));
            }
        }
    }

}
