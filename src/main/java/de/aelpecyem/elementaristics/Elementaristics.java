package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.capability.CapabilityHandler;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.compat.thaumcraft.ThaumcraftCompat;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.ProtoplasmTaskInit;
import de.aelpecyem.elementaristics.events.EmotionHandler;
import de.aelpecyem.elementaristics.events.LivingEventHandler;
import de.aelpecyem.elementaristics.events.LootTableEventHandler;
import de.aelpecyem.elementaristics.events.PotionEventHandler;
import de.aelpecyem.elementaristics.init.*;
import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import de.aelpecyem.elementaristics.misc.commands.CommandElementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.poisons.PoisonInit;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.proxy.CommonProxy;
import de.aelpecyem.elementaristics.world.WorldGen;
import de.aelpecyem.elementaristics.world.structures.WorldGenAnomaly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Elementaristics.MODID, name = Elementaristics.NAME, version = Elementaristics.VERSION, useMetadata = true)
public final class Elementaristics {
    //TODO ...ASCENSION!
    //TODO more rites
    //TODO ...more!

    //todo thoughts on further ascension: each Soul (or at least the basic ones Aether for Magan) need to build their own core; godhood will require all 5 cores. Fabricating a core will sacrifice a cultist of the matching aspect. Place the core in the Mind, fight the guardian that appears, return to the Wake and be happy that you managed a good part of your ascension

    public static final String MODID = "elementaristics";
    public static final String NAME = "Elementaristics";
    public static final String VERSION = "0.0.9.7.3";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static CreativeTabs tab = new ElementaristicsTab();
    @SidedProxy(serverSide = "de.aelpecyem.elementaristics.proxy.CommonProxy", clientSide = "de.aelpecyem.elementaristics.proxy.ClientProxy")
    public static CommonProxy proxy;


    @Mod.Instance(MODID)
    public static Elementaristics instance;

    public static Configuration config;

    public static final DamageSource DAMAGE_PSYCHIC = new DamageSource("damage_psychic").setDamageBypassesArmor();
    public static final DamageSource DAMAGE_AIR = new DamageSource("damage_air").setExplosion();
    public static final DamageSource DAMAGE_AETHER = new DamageSource("damage_aether");

    //todo fancy graph with magan increase
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        LOGGER.info(NAME + " is loading");
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "elementaristics.cfg"));

        MinecraftForge.EVENT_BUS.register(new PotionInit());

        ProtoplasmTaskInit.init();
        SoulInit.init();

        PacketHandler.init();

        ModCaps.registerCapabilites();
        initElements();
        Deities.initDeities();
        PoisonInit.init();
        ModBlocks.init();
        ModItems.init();

        ModEntities.init();
        BiomeInit.registerBiomes();
        ModDimensions.init();

        GameRegistry.registerWorldGenerator(new WorldGen(), 3);
        initOreDict();
        CustomAdvancements.init();
        //Keybinds.register();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        if (Loader.isModLoaded("thaumcraft")) {
            MinecraftForge.EVENT_BUS.register(new ThaumcraftCompat());
            ThaumcraftCompat.init();
        }
        GameRegistry.registerWorldGenerator(new WorldGenAnomaly(), 10);
        MinecraftForge.EVENT_BUS.register(new SpellInit());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());
        MinecraftForge.EVENT_BUS.register(new LootTableEventHandler());
        MinecraftForge.EVENT_BUS.register(new PotionEventHandler());
        MinecraftForge.EVENT_BUS.register(new EmotionHandler());


        InitRecipes.init();
        RiteInit.init();

        initFurnaceRecipes();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SpellInit.initSpells();
        SoulInit.initSpellsForSouls();
        SoulCaps.init();
    }

    private void initOreDict() {
        // OreDictionary.registerOre("dye", Items.DYE);
        OreDictionary.registerOre("listAetherItems", Items.ENDER_PEARL);
        OreDictionary.registerOre("listAetherItems", Items.DIAMOND);
        OreDictionary.registerOre("listAetherItems", Items.ENDER_EYE);

        OreDictionary.registerOre("listAllMeat", Items.PORKCHOP);
        OreDictionary.registerOre("listAllMeat", Items.BEEF);
        OreDictionary.registerOre("listAllMeat", Items.MUTTON);
        OreDictionary.registerOre("listAllMeat", Items.FISH);

        OreDictionary.registerOre("listAllPlant", Blocks.VINE);
        OreDictionary.registerOre("listAllPlant", Blocks.GRASS);
        OreDictionary.registerOre("listAllPlant", Blocks.RED_FLOWER);
        OreDictionary.registerOre("listAllPlant", Blocks.YELLOW_FLOWER);
        OreDictionary.registerOre("listAllPlant", Blocks.TALLGRASS);
        OreDictionary.registerOre("listAllPlant", Blocks.DOUBLE_PLANT);
    }

    private void initFurnaceRecipes() {

    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandElementaristics());
    }

    private void initElements() {
        Aspects.init();
    }

    @Mod.EventBusSubscriber
    public static class RegistryHandler {

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());

        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());

        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }
    }

}

