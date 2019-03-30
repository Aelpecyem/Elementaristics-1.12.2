package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.blocks.base.BlockBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockCropBase;
import de.aelpecyem.elementaristics.blocks.base.BlockEssence;
import de.aelpecyem.elementaristics.blocks.base.OreDroppingBase;
import de.aelpecyem.elementaristics.blocks.base.crops.CropOpium;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.*;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.Block;
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
    public static Map<Integer, BlockEssence> ESSENCES_CONCENTRATED = new HashMap<>();

    //CROPS
    public static BlockCropBase crop_opium;
    //"DECOR"
    public static BlockBase stone_enriched;
    //ORES
    public static OreDroppingBase ore_helium;
    public static OreDroppingBase ore_hydrogen;
    public static OreDroppingBase ore_prismarine;

    //Others
    public static BlockPedestal pedestal;
    public static BlockPurifier purifier;
    public static BlockLightningPedestal pedestalLightning;
    public static BlockSoulIdentifier soul_identifier;
    public static BlockConcentrator concentrator;
    public static BlockTunneler tunneler_top;
    public static BlockFilterHolder filter_holder;
    public static BlockReactor reactor;
    public static BlockBase essence_casing;
    public static BlockAltar altar;
    public static BlockBase fabric_reason;
    public static BlockBase fabric_passion;

    public static void init() {
        for (Block blockBase : BLOCKS) {
            if (blockBase instanceof BlockEssence) {
                BLOCKS.remove(blockBase);
            }
        }
        crop_opium = new CropOpium();
        stone_enriched = new BlockBase(Material.ROCK, "stone_enriched");
        ore_helium = new OreDroppingBase("ore_helium", ModItems.helium_bubble, 1, 2);
        ore_hydrogen = new OreDroppingBase("ore_hydrogen", ModItems.hydrogen_bubble, 1, 2);
        ore_prismarine = new OreDroppingBase("ore_prismarine", Items.PRISMARINE_SHARD, 1, 2);
        pedestal = new BlockPedestal();
        purifier = new BlockPurifier();
        pedestalLightning = new BlockLightningPedestal();
        soul_identifier = new BlockSoulIdentifier();
        concentrator = new BlockConcentrator();
        tunneler_top = new BlockTunneler();
        filter_holder = new BlockFilterHolder();
        reactor = new BlockReactor();
        essence_casing = new BlockBase(Material.ROCK, "casing_essence");
        altar = new BlockAltar();
        fabric_reason = new BlockBase(Material.ROCK, "fabric_reason");
        fabric_passion = new BlockBase(Material.ROCK, "fabric_passion");
        for (Aspect a : Aspects.getElements()) {
            ESSENCES_CONCENTRATED.put(a.getId(), new BlockEssence("block_essence_" + a.getName(), a.getId()));
        }

    }


    public static void register(IForgeRegistry<Block> registry) {

        for (Block blockBase : BLOCKS) {
            if (!(blockBase instanceof BlockEssence)) {
                registry.register(blockBase);
            }

        }
        for (int i = 0; i < Aspects.getElements().size(); i++) {

            registry.register(ESSENCES_CONCENTRATED.get(i));
        }


        registry.registerAll(

        );
        GameRegistry.registerTileEntity(pedestal.getTileEntityClass(), pedestal.getRegistryName().toString());
        GameRegistry.registerTileEntity(purifier.getTileEntityClass(), purifier.getRegistryName().toString());
        GameRegistry.registerTileEntity(pedestalLightning.getTileEntityClass(), pedestalLightning.getRegistryName().toString());
        GameRegistry.registerTileEntity(soul_identifier.getTileEntityClass(), soul_identifier.getRegistryName().toString());
        GameRegistry.registerTileEntity(concentrator.getTileEntityClass(), concentrator.getRegistryName().toString());
        GameRegistry.registerTileEntity(tunneler_top.getTileEntityClass(), tunneler_top.getRegistryName().toString());
        GameRegistry.registerTileEntity(filter_holder.getTileEntityClass(), filter_holder.getRegistryName().toString());
        GameRegistry.registerTileEntity(reactor.getTileEntityClass(), reactor.getRegistryName().toString());
        GameRegistry.registerTileEntity(altar.getTileEntityClass(), altar.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {

        for (Block blockBase : BLOCKS) {
            if (!(blockBase instanceof BlockEssence)) {
                if (blockBase instanceof BlockBase)
                registry.register(((BlockBase) blockBase).createItemBlock());
            }
        }
        for (int i = 0; i < Aspects.getElements().size(); i++) {

            registry.register(ESSENCES_CONCENTRATED.get(i).createItemBlock());
        }

        registry.registerAll(

        );
    }

    public static void registerModels() {
        for (Block blockBase : BLOCKS) {
            if (!(blockBase instanceof BlockEssence)) {
                if (blockBase instanceof IHasModel) {
                    ((IHasModel) blockBase).registerItemModel(Item.getItemFromBlock(blockBase));
                }
            }
        }
        for (int i = 0; i < Aspects.getElements().size(); i++) {

            ESSENCES_CONCENTRATED.get(i).registerItemModel(Item.getItemFromBlock(ESSENCES_CONCENTRATED.get(i)));
        }
    }

    public static Block getElementEssenceFromName(String name) {
        return ESSENCES_CONCENTRATED.get(Aspects.getElementByName(name).getId());
    }
}
