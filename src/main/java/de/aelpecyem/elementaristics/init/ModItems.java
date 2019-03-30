package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.items.base.*;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemHammerHeat;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemSoulChanger;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemSoulMirror;
import de.aelpecyem.elementaristics.items.base.burnable.ItemHerbBundle;
import de.aelpecyem.elementaristics.items.base.thaumagral.*;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static List<Item> ITEMS = new ArrayList<>();

    public static ItemBase helium_bubble = new ItemBase("bubble_helium");
    public static ItemBase hydrogen_bubble = new ItemBase("bubble_hydrogen");
    public static ItemBase bundle_herbs = new ItemHerbBundle();

    public static ItemBase petal_opium;
    public static ItemSeedBase seed_herb;
    public static ItemBase ash;
    public static ItemBase chaotic_matter;
    public static ItemBase maganized_matter;
    public static ItemBase protoplasm;
    public static ItemBase vibrant_quartz;
    public static ItemBase matter_accelerating_module;

    public static ItemBase cluster_aether;
    public static ItemBase cluster_air;
    public static ItemBase cluster_earth;
    public static ItemBase cluster_water;
    public static ItemBase cluster_fire;

    public static ItemBase essence_blank;

    public static ItemAspects catalyst_entropizing;
    public static ItemSoulChanger soul_changer;
    public static ItemSoulMirror soul_mirror;

    public static ItemHammerHeat hammer_heat; //might make that an item with nice functions
    public static ItemThaumagral thaumagral_iron;

    public static IncantationBase incantation_chaos = new IncantationBase("incantation_chaos", RiteInit.riteChaos, Aspects.chaos, 4);
    public static IncantationBase incantation_light = new IncantationBase("incantation_light",RiteInit.riteKnowledge, Aspects.light, 4);
    public static IncantationBase incantation_feast = new IncantationBase("incantation_feast",RiteInit.riteFeast, Aspects.life, 4);
    public static IncantationBase incantation_conflagration = new IncantationBase("incantation_conflagration", RiteInit.riteConflagration, Aspects.fire, 4);
    public static IncantationBase incantation_wind = new IncantationBase("incantation_wind", RiteInit.riteShredding, Aspects.air, 4);
    public static IncantationBase incantation_gaia = new IncantationBase("incantation_gaia", RiteInit.riteGaiasGaze, Aspects.earth, 4);
    public static IncantationBase incantation_stars = new IncantationBase("incantation_stars", RiteInit.riteSpaceExilation, Aspects.aether, 4);
    public static IncantationBase incantation_depths = new IncantationBase("incantation_depths", RiteInit.riteDrowningAstral, Aspects.water, 4);
    public static ItemAspects sands_soul;
    public static ItemAspects wine_redmost;

   /* public static ItemArmor hood_cultist = new RobesCultist("hood_cultist",1, EntityEquipmentSlot.HEAD);
    public static ItemArmor garb_cultist = new RobesCultist("garb_cultist",1, EntityEquipmentSlot.CHEST);
    public static ItemArmor legwear_cultist = new RobesCultist("legwear_cultist",2, EntityEquipmentSlot.LEGS);
    public static ItemArmor boots_cultist = new RobesCultist("boots_cultist",1, EntityEquipmentSlot.FEET);
*/

    public static ItemEssence essence = new ItemEssence();


    public static void init() {

        petal_opium = new ItemBase("petal_opium");
        seed_herb = new ItemSeedBase("seeds_opium", ModBlocks.crop_opium, Blocks.FARMLAND);

        vibrant_quartz = new ItemBase("quartz_vibrant");
        chaotic_matter = new ItemBase("matter_chaotic");
        maganized_matter = new ItemBase("matter_maganized"); //1 of each primal essence 1 chaotic matter = 8 Maganized

        cluster_aether = new ItemBase("cluster_aether");
        cluster_earth = new ItemBase("cluster_earth");
        cluster_air = new ItemBase("cluster_air");
        cluster_water = new ItemBase("cluster_water");
        cluster_fire = new ItemBase("cluster_fire");

        essence_blank = new ItemBase("essence_blank");


        matter_accelerating_module = new ItemBase("module_matter_accelerating");

        catalyst_entropizing = new ItemAspects("catalyst_entropizing", Aspects.chaos, 8, true); //1 aether essence 3 chaotic matter pieces
        soul_changer = new ItemSoulChanger();
        soul_mirror = new ItemSoulMirror();
        ash = new ItemBase("ash");
        protoplasm = new ItemBase("protoplasm");

        //book_liber_elementium = new LiberElementiumItem();
        hammer_heat = new ItemHammerHeat();

        sands_soul = new ItemAspects("powder_soul", Aspects.soul, 6, true);
        wine_redmost = new ItemAspects("wine_redmost", Aspects.body, 6, true);

        thaumagral_iron = new ItemThaumagral("thaumagral_iron", Item.ToolMaterial.IRON, 0, 0);
    }

    public static void register(IForgeRegistry<Item> registry) {
        for (Item item : ITEMS) {
            Item toRegister = (Item) item;
            registry.register(toRegister);
        }

    }

    public static void registerModels() {
        for (Item item : ITEMS) {
                if (item instanceof IHasModel){
                    ((IHasModel) item).registerItemModel();
                }
        }

    }
}
