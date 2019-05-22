package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.blocks.base.*;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockCropBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossEverchanging;
import de.aelpecyem.elementaristics.blocks.base.crops.CropOpium;
import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.*;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy.BlockGeneratorCombustion;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy.BlockStorage;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityShrineBase;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.IBlockHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

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

    //PANTHEON
    public static BlockDeityShrineBase symbol_nothingness;
    public static BlockDeityShrineBase symbol_azathoth;
    public static BlockDeityShrineBase symbol_dragon_aether;
    public static BlockDeityShrineBase symbol_dragon_fire;
    public static BlockDeityShrineBase symbol_dragon_earth;
    public static BlockDeityShrineBase symbol_dragon_water;
    public static BlockDeityShrineBase symbol_dragon_air;
    public static BlockDeityShrineBase symbol_gate;
    public static BlockDeityShrineBase symbol_dreamer;
    public static BlockDeityShrineBase symbol_angel;
    public static BlockDeityShrineBase symbol_storm;
    public static BlockDeityShrineBase symbol_fighter;
    public static BlockDeityShrineBase symbol_sun;
    public static BlockDeityShrineBase symbol_harbinger;
    public static BlockDeityShrineBase symbol_queen;
    public static BlockDeityShrineBase symbol_goat;
    public static BlockDeityShrineBase symbol_moth;
    public static BlockDeityShrineBase symbol_thread;
    public static BlockDeityShrineBase symbol_mirror; //
    public static BlockDeityShrineBase symbol_dancer;
    public static BlockDeityShrineBase symbol_king;
    public static BlockDeityShrineBase symbol_mother;
    public static BlockDeityShrineBase symbol_moon;
    public static BlockDeityShrineBase symbol_witch;

    public static BlockBase manipulator_supplying;
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

        symbol_nothingness = new BlockDeityShrineBase("symbol_nothingness", Deities.deityNothingness, false);
        symbol_azathoth = new BlockDeityShrineBase("symbol_azathoth", Deities.deityAzathoth, false);
        symbol_dragon_fire = new BlockDeityShrineBase("symbol_dragon_fire", Deities.deityDragonFire, false);
        symbol_dragon_aether = new BlockDeityShrineBase("symbol_dragon_aether", Deities.deityDragonAether, false);
        symbol_dragon_air = new BlockDeityShrineBase("symbol_dragon_air", Deities.deityDragonAir, false);
        symbol_dragon_earth = new BlockDeityShrineBase("symbol_dragon_earth", Deities.deityDragonEarth, false);
        symbol_dragon_water = new BlockDeityShrineBase("symbol_dragon_water", Deities.deityDragonWater, false);
        symbol_gate = new BlockDeityShrineBase("symbol_gate", Deities.deityGateAndKey, false);
        symbol_dreamer = new BlockDeityShrineBase("symbol_dreamer", Deities.deityDreamer, false);
        symbol_angel = new BlockDeityShrineBase("symbol_angel", Deities.deityAngel, false);
        symbol_storm = new BlockDeityShrineBase("symbol_storm", Deities.deityStorm, false);
        symbol_fighter = new BlockDeityShrineBase("symbol_fighter", Deities.deityFighter, false);
        symbol_sun = new BlockDeityShrineBase("symbol_sun", Deities.deitySun, false);
        symbol_harbinger = new BlockDeityShrineBase("symbol_harbinger", Deities.deityHarbinger, false);
        symbol_queen = new BlockDeityShrineBase("symbol_queen", Deities.deityQueen, false);
        symbol_goat = new BlockDeityShrineBase("symbol_goat", Deities.deityGoat, false);
        symbol_moth = new BlockDeityShrineBase("symbol_moth", Deities.deityMoth, false);
        symbol_thread = new BlockDeityShrineBase("symbol_thread", Deities.deityThread, false);
        symbol_mirror = new BlockDeityShrineBase("symbol_mirror", Deities.deityMirror, false);
        symbol_dancer = new BlockDeityShrineBase("symbol_dancer", Deities.deityDancer, false);
        symbol_king = new BlockDeityShrineBase("symbol_king", Deities.deityKing, false);
        symbol_mother = new BlockDeityShrineBase("symbol_mother", Deities.deityMother, false);
        symbol_moon = new BlockDeityShrineBase("symbol_moon", Deities.deityMoon, false);
        symbol_witch = new BlockDeityShrineBase("symbol_witch", Deities.deityWitch, false);

        manipulator_supplying = new BlockBase(Material.ROCK, "manipulator_supplying");

        //idea: wireless redstone transmitter, uses a teensy bit of OE, now and then, and converts the powered state to the linked block
    }


    public static void register(IForgeRegistry<Block> registry) {

        for (Block blockBase : BLOCKS) {
            registry.register(blockBase);


        }

        registry.registerAll(

        );
        GameRegistry.registerTileEntity(TileEntityDeityShrine.class, "elementaristics:deity_shrine");
        for (Block block : BLOCKS) {
            if (block instanceof BlockTileEntity && ((BlockTileEntity) block).getTileEntityClass() != TileEntityDeityShrine.class) {
                GameRegistry.registerTileEntity(((BlockTileEntity) block).getTileEntityClass(), block.getRegistryName());
            }
        }

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
