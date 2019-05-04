package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.compat.thaumcraft.ThaumcraftCompat;
import de.aelpecyem.elementaristics.capability.CapabilityHandler;
import de.aelpecyem.elementaristics.events.EventHandler;
import de.aelpecyem.elementaristics.events.HUDRenderHandler;
import de.aelpecyem.elementaristics.events.LootTableEventHandler;
import de.aelpecyem.elementaristics.gui.GuiHandler;
import de.aelpecyem.elementaristics.init.*;
import de.aelpecyem.elementaristics.misc.ItemColorHandler;
import de.aelpecyem.elementaristics.misc.commands.CommandElementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.proxy.CommonProxy;
import de.aelpecyem.elementaristics.init.InitRecipes;
import de.aelpecyem.elementaristics.util.ClientTickHandler;
import de.aelpecyem.elementaristics.util.Keybinds;
import de.aelpecyem.elementaristics.util.RenderHandler;
import de.aelpecyem.elementaristics.world.WorldGen;
import de.aelpecyem.elementaristics.world.structures.WorldGenAnomaly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Elementaristics.MODID, name = Elementaristics.NAME, version = Elementaristics.VERSION, dependencies = "required-after:baubles")
public final class Elementaristics {

    //TODO add doc to most aspects of this mod, let it be directly in categories or indirectly in Furtive Stories
    //TODO might remove ores
    //TODO ...ASCENSION!
    //TODO spellcasting
    //TODO more rites
    //TODO ...more!

    public static final String MODID = "elementaristics";
    public static final String NAME = "Elementaristics";
    public static final String VERSION = "0.0.1";

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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info(NAME + " is loading");
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "elementaristics.cfg"));
        de.aelpecyem.elementaristics.config.Config.readConfig();
        MinecraftForge.EVENT_BUS.register(new PotionInit());

        SoulInit.init();

        PacketHandler.init();

        ModCaps.registerCapabilites();
        initElements();
        ModBlocks.init();
        ModItems.init();

        ModEntities.init();
        RenderHandler.registerEntityRenderers();
        BiomeInit.registerBiomes();
        ModDimensions.init();
        proxy.registerRenderers();

        GameRegistry.registerWorldGenerator(new WorldGen(), 3);
        initOreDict();
        Keybinds.register();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemColorHandler(), ModItems.essence);
        if (Loader.isModLoaded("thaumcraft")) {
            ThaumcraftCompat.init();
        }
        GameRegistry.registerWorldGenerator(new WorldGenAnomaly(), 10);// todo gonna continue with this later on
        MinecraftForge.EVENT_BUS.register(new SpellInit());
        MinecraftForge.EVENT_BUS.register(new PotionInit());
        MinecraftForge.EVENT_BUS.register(new HUDRenderHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new LootTableEventHandler());

        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        InitRecipes.init();
        RiteInit.init();
        proxy.registerKeyBinds();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());


    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SpellInit.initSpells();
        SoulInit.initSpellsForSouls();
        SoulCaps.init();
    }

    private void initOreDict() {
        // OreDictionary.registerOre("dye", Items.DYE);
        OreDictionary.registerOre("listAllMeat", Items.PORKCHOP);
        OreDictionary.registerOre("listAllMeat", Items.BEEF);
        OreDictionary.registerOre("listAllMeat", Items.MUTTON);

        OreDictionary.registerOre("listAllPlant", Blocks.VINE);
        OreDictionary.registerOre("listAllPlant", Blocks.RED_FLOWER);
        OreDictionary.registerOre("listAllPlant", Blocks.YELLOW_FLOWER);
        OreDictionary.registerOre("listAllPlant", Blocks.TALLGRASS);
        OreDictionary.registerOre("listAllPlant", Blocks.DOUBLE_PLANT);

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

