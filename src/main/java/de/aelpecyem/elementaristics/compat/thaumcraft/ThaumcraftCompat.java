package de.aelpecyem.elementaristics.compat.thaumcraft;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.compat.thaumcraft.focus.FocusEffectRiftCloser;
import de.aelpecyem.elementaristics.compat.thaumcraft.focus.FocusEffectStabilize;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.Registrar;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanBlock;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.blocks.BlockTC;

public class ThaumcraftCompat {
    static ResourceLocation elementaristicsGroup = new ResourceLocation("elementaristics");

    public static void init() {
        ResearchCategories.registerCategory("ELEMENTARISTICS", "UNLOCKALCHEMY",
                new AspectList(),
                new ResourceLocation("elementaristics", "textures/items/book_elementaristics.png"),
                new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_6.jpg"),
                new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation(Elementaristics.MODID, "tc/research/elementaristics"));
        registerRecipes();
        registerGolemMaterials();
        addScannables();

    }

    @SubscribeEvent
    public void aspectRegistrationEvent(AspectRegistryEvent event) {
        //todo continue with that
        event.register.registerObjectTag(new ItemStack(ModItems.poison_glassblood), (new AspectList()).add(Aspect.ALCHEMY, 10).add(Aspect.DEATH, 12).add(Aspect.CRYSTAL, 4).add(Aspect.FLUX, 2));
        event.register.registerObjectTag(new ItemStack(ModItems.poison_wintersbreath), (new AspectList()).add(Aspect.ALCHEMY, 10).add(Aspect.DEATH, 12).add(Aspect.COLD, 6));
        event.register.registerObjectTag(new ItemStack(ModItems.sandthroat_concentrated), (new AspectList()).add(Aspect.ALCHEMY, 10).add(Aspect.DEATH, 6));
    }


    public static void registerRecipes() {

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("elementaristics:essence_blank"), new ShapedArcaneRecipe(elementaristicsGroup, "ESSENCECREATION@2", 5, (new AspectList()).add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(ModItems.essence_blank, 2), new Object[]{" I ", "I I", " I ", 'I', "gemQuartz"}));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_fire"), new CrucibleRecipe("CREATION_FIRE@2", new ItemStack(ModItems.essence, 1, Aspects.fire.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.FIRE, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_water"), new CrucibleRecipe("CREATION_WATER@2", new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.WATER, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_earth"), new CrucibleRecipe("CREATION_EARTH@2", new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.EARTH, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_air"), new CrucibleRecipe("CREATION_AIR@2", new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.AIR, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_aether"), new CrucibleRecipe("CREATION_AETHER@2", new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5)));

        /*ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_light"), new CrucibleRecipe("CREATION_LIGHT@2", new ItemStack(ModItems.essence, 1, Aspects.light.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.FIRE, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_ice"), new CrucibleRecipe("CREATION_ICE@2", new ItemStack(ModItems.essence, 1, Aspects.ice.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.COLD, 10)));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_crystal"), new CrucibleRecipe("CREATION_CRYSTAL@2", new ItemStack(ModItems.essence, 1, Aspects.crystal.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.CRYSTAL, 10)));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_life"), new CrucibleRecipe("CREATION_LIFE@2", new ItemStack(ModItems.essence, 1, Aspects.life.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.LIFE, 10)));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_order"), new CrucibleRecipe("CREATION_ORDER@2", new ItemStack(ModItems.essence, 1, Aspects.order.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.ORDER, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("elementaristics:tc_essence_chaos"), new CrucibleRecipe("CREATION_CHAOS@2", new ItemStack(ModItems.essence, 1, Aspects.chaos.getId()), new ItemStack(ModItems.essence_blank), (new AspectList()).add(Aspect.ENTROPY, 10)));
        */
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("elementaristics:tc_mushroom_intoxicating"), new InfusionRecipe("MUSHROOM_INTOXICATING@3", new ItemStack(ModBlocks.mushroom_intoxicating), 1, (new AspectList()).add(Aspect.MAGIC, 15).add(Aspect.MIND, 20).add(Aspect.ALCHEMY, 20).add(Aspect.PLANT, 40).add(Aspect.AURA, 10), new ItemStack(BlocksTC.vishroom), new ItemStack(ModItems.bundle_herbs), new ItemStack(ModItems.petal_opium), new ItemStack(ModItems.essence, 1, Aspects.mind.getId()), new ItemStack(ModBlocks.flower_ecstasy)));


        FocusEngine.registerElement(FocusEffectStabilize.class, new ResourceLocation(Elementaristics.MODID, "tc/textures/stabilize.png"), 3106205);
        FocusEngine.registerElement(FocusEffectRiftCloser.class, new ResourceLocation(Elementaristics.MODID, "tc/textures/close_rift.png"), 4989494);


    }

    public static void registerGolemMaterials() {
        GolemMaterial.register(new GolemMaterial("MAGANIZED_MATTER", new String[]{"MATSTUDMAGANIZED_MATTER"}, new ResourceLocation(
                Elementaristics.MODID, "textures/entity/tc/mat_matter_maganized.png"), 14186240, 14, 8, 3, new ItemStack(ModItems.maganized_matter, 2), new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[]{EnumGolemTrait.LIGHT}));

        GolemMaterial.register(new GolemMaterial("PROTOPLASM", new String[]{"MATSTUDPROTOPLASM"}, new ResourceLocation(
                Elementaristics.MODID, "textures/entity/tc/mat_protoplasm.png"), 1716026, 12, 3, 4, new ItemStack(ModItems.protoplasm, 1), new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[]{EnumGolemTrait.REPAIR, EnumGolemTrait.CLIMBER}));


    }

    public static void addScannables() {
        ScanningManager.addScannableThing(
                new ScanItem("f_MAGANIZED_MATTER", new ItemStack(ModItems.maganized_matter, 1)));
        ScanningManager.addScannableThing(
                new ScanItem("f_PROTOPLASM", new ItemStack(ModItems.protoplasm, 1)));


        ScanningManager.addScannableThing(new ScanBlock("f_PASSION", ModBlocks.fabric_passion));
        ScanningManager.addScannableThing(new ScanBlock("f_REASON", ModBlocks.fabric_reason));
        ScanningManager.addScannableThing(new ScanBlock("f_SHROOM", BlocksTC.vishroom));
    }


}
