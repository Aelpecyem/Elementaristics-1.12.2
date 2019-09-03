package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.base.*;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockCropBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossBase;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossEverchanging;
import de.aelpecyem.elementaristics.blocks.base.crops.CropOpium;
import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.*;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy.*;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityShrineBase;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityWitch;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.IBlockHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public class ModBlocks {

    public static List<Block> BLOCKS = new ArrayList<>();

    //CROPS
    public static BlockCropBase crop_opium;
    //"DECOR"
    public static BlockBase stone_enriched;
    public static BlockPedestal pedestal;
    public static BlockBase stone_runed;

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

    public static BlockGoldenThread block_golden_thread;
    //ENERGY
    public static BlockGeneratorCombustion generator_combustion;
    public static BlockStorage energy_storage;
    public static BlockCreativeStorage energy_storage_creative;

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
    public static BlockDeityShrineBase symbol_mirror;
    public static BlockDeityShrineBase symbol_dancer;
    public static BlockDeityShrineBase symbol_king;
    public static BlockDeityShrineBase symbol_mother;
    public static BlockDeityShrineBase symbol_moon;
    public static BlockDeityShrineBase symbol_witch;

    public static BlockDeityShrineBase shrine_nothingness;
    public static BlockDeityShrineBase shrine_azathoth;
    public static BlockDeityShrineBase shrine_dragon_aether;
    public static BlockDeityShrineBase shrine_dragon_fire;
    public static BlockDeityShrineBase shrine_dragon_earth;
    public static BlockDeityShrineBase shrine_dragon_water;
    public static BlockDeityShrineBase shrine_dragon_air;
    public static BlockDeityShrineBase shrine_gate;
    public static BlockDeityShrineBase shrine_dreamer;
    public static BlockDeityShrineBase shrine_angel;
    public static BlockDeityShrineBase shrine_storm;
    public static BlockDeityShrineBase shrine_fighter;
    public static BlockDeityShrineBase shrine_sun;
    public static BlockDeityShrineBase shrine_harbinger;
    public static BlockDeityShrineBase shrine_queen;
    public static BlockDeityShrineBase shrine_goat;
    public static BlockDeityShrineBase shrine_moth;
    public static BlockDeityShrineBase shrine_thread;
    public static BlockDeityShrineBase shrine_mirror;
    public static BlockDeityShrineBase shrine_dancer;
    public static BlockDeityShrineBase shrine_king;
    public static BlockDeityShrineBase shrine_mother;
    public static BlockDeityShrineBase shrine_moon;
    public static BlockDeityShrineBase shrine_witch;

    public static BlockBase manipulator_supplying;
    public static BlockBase manipulator_passive;
    public static BlockRedstoneTransmitter block_transmitter_redstone;
    public static BlockRedstoneEmulator block_emulator_redstone;

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

        block_golden_thread = new BlockGoldenThread();

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
        energy_storage_creative = new BlockCreativeStorage();
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
        symbol_fighter = new BlockDeityShrineBase("symbol_knight", Deities.deityKnight, false);
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
        symbol_witch = new BlockDeityWitch(false);

        shrine_nothingness = new BlockDeityShrineBase("shrine_nothingness", Deities.deityNothingness, true);
        shrine_azathoth = new BlockDeityShrineBase("shrine_azathoth", Deities.deityAzathoth, true);
        shrine_dragon_fire = new BlockDeityShrineBase("shrine_dragon_fire", Deities.deityDragonFire, true);
        shrine_dragon_aether = new BlockDeityShrineBase("shrine_dragon_aether", Deities.deityDragonAether, true);
        shrine_dragon_air = new BlockDeityShrineBase("shrine_dragon_air", Deities.deityDragonAir, true);
        shrine_dragon_earth = new BlockDeityShrineBase("shrine_dragon_earth", Deities.deityDragonEarth, true);
        shrine_dragon_water = new BlockDeityShrineBase("shrine_dragon_water", Deities.deityDragonWater, true);
        shrine_gate = new BlockDeityShrineBase("shrine_gate", Deities.deityGateAndKey, true);
        shrine_dreamer = new BlockDeityShrineBase("shrine_dreamer", Deities.deityDreamer, true);
        shrine_angel = new BlockDeityShrineBase("shrine_angel", Deities.deityAngel, true);
        shrine_storm = new BlockDeityShrineBase("shrine_storm", Deities.deityStorm, true);
        shrine_fighter = new BlockDeityShrineBase("shrine_knight", Deities.deityKnight, true);
        shrine_sun= new BlockDeityShrineBase("shrine_sun", Deities.deitySun, true);
        shrine_harbinger = new BlockDeityShrineBase("shrine_harbinger", Deities.deityHarbinger, true);
        shrine_queen = new BlockDeityShrineBase("shrine_queen", Deities.deityQueen, true);
        shrine_goat = new BlockDeityShrineBase("shrine_goat", Deities.deityGoat, true);
        shrine_moth = new BlockDeityShrineBase("shrine_moth", Deities.deityMoth, true);
        shrine_thread = new BlockDeityShrineBase("shrine_thread", Deities.deityThread, true);
        shrine_mirror = new BlockDeityShrineBase("shrine_mirror", Deities.deityMirror, true);
        shrine_dancer = new BlockDeityShrineBase("shrine_dancer", Deities.deityDancer, true);
        shrine_king = new BlockDeityShrineBase("shrine_king", Deities.deityKing, true);
        shrine_mother = new BlockDeityShrineBase("shrine_mother", Deities.deityMother, true);
        shrine_moon = new BlockDeityShrineBase("shrine_moon", Deities.deityMoon, true);
        shrine_witch = new BlockDeityWitch(true);

        manipulator_supplying = new BlockPowerableBase(Material.ROCK, "manipulator_supplying");
        manipulator_passive = new BlockPowerableBase(Material.ROCK, "manipulator_passive");
        block_transmitter_redstone = new BlockRedstoneTransmitter();
        block_emulator_redstone = new BlockRedstoneEmulator();

        stone_runed = new BlockBase(Material.ROCK, "stone_runed");
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
                ((IBlockHasModel) blockBase).registerItemModel(blockBase);
            }
        }
    }

    public static void registerCustomItemblock(Block b, String path) {
        registerCustomItemblock(b, 1, i -> path);
    }

    public static void registerCustomItemblock(Block b, int maxExclusive, IntFunction<String> metaToPath) {
        Item item = Item.getItemFromBlock(b);
        for (int i = 0; i < maxExclusive; i++) {
            ModelLoader.setCustomModelResourceLocation(
                    item, i,
                    new ModelResourceLocation(Elementaristics.MODID + ":itemblock/" + metaToPath.apply(i), "inventory")
            );
        }
    }

}
