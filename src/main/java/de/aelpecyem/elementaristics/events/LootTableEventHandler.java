package de.aelpecyem.elementaristics.events;

import com.google.common.collect.ImmutableList;
import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class LootTableEventHandler {
    /*
    buildscript {
    repositories {
        jcenter()
        maven { url = "http://files.minecraftforge.net/maven" }
        maven { url "https://plugins.gradle.org/m2/" }
    }
    dependencies {
        classpath 'net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT'
        classpath 'com.wynprice.cursemaven:CurseMaven:1.2.2'
    }
}
apply plugin: 'net.minecraftforge.gradle.forge'
apply plugin: 'com.wynprice.cursemaven'
//Only edit below this line, the above code adds and enables the necessary things for Forge to be setup.


version = "0.0.9.6.1"
group = "de.aelpecyem.elementaristics" // http://maven.apache.org/guides/mini/guide-naming-conventions.html
archivesBaseName = "elementaristics"

sourceCompatibility = targetCompatibility = '1.8' // Need this here so eclipse task generates correctly.
compileJava {
    sourceCompatibility = targetCompatibility = '1.8'
}

minecraft {
    version = "1.12.2-14.23.5.2768"
    runDir = "run"

    // the mappings can be changed at any time, and must be in the following format.
    // snapshot_YYYYMMDD   snapshot are built nightly.
    // stable_#            stables are built at the discretion of the MCP team.
    // Use non-default mappings at your own risk. they may not always work.
    // simply re-run your setup task after changing the mappings to update your workspace.
    mappings = "snapshot_20171003"

    clientRunArgs += "--username=Aelpecyem"
    // makeObfSourceJar = false // an Srg named sources jar is made by default. uncomment this to disable.
}

repositories {
    flatDir {
        dirs 'libs'
    }
    maven { url = "http://dvs1.progwml6.com/files/maven" }
    maven { url = "https://minecraft.curseforge.com/api/maven" }
    maven { url "http://maven.tterrag.com" }
    maven { url "https://maven.blamejared.com" }
    maven { url "https://maven.mcmoddev.com/" }
}


dependencies {
    deobfProvided "mezz.jei:jei_1.12.2:4.15.0.268"
    deobfProvided "team.chisel.ctm:CTM:MC1.12.2-0.3.3.22"
    deobfProvided "team.chisel:Chisel:MC1.12.2-0.2.1.35"
    deobfProvided "thaumcraft:Thaumcraft:1.12.2:6.1.BETA26"
    deobfProvided "dynamictrees:DynamicTrees:1.12.2:0.9.5"
    deobfProvided "vazkii.botania:Botania:r1.10-361.69"
    deobfProvided "betteranimalsplus:betteranimalsplus:1.12.2:7.0.2"
    deobfProvided curse.resolve("rustic", "2746408")
    compile "baubles:Baubles:1.12:1.5.2"
    compile "vazkii.patchouli:Patchouli:1.0-20.99"
    // you may put jars on which you depend on in ./libs
    // or you may define them like so..
    //compile "some.group:artifact:version:classifier"
    //compile "some.group:artifact:version"

    // real examples
    //compile 'com.mod-buildcraft:buildcraft:6.0.8:dev'  // adds buildcraft to the dev env
    //compile 'com.googlecode.efficient-java-matrix-library:ejml:0.24' // adds ejml to the dev env

    // the 'provided' configuration is for optional dependencies that exist at compile-time but might not at runtime.
    //provided 'com.mod-buildcraft:buildcraft:6.0.8:dev'

    // the deobf configurations:  'deobfCompile' and 'deobfProvided' are the same as the normal compile and provided,
    // except that these dependencies get remapped to your current MCP mappings
    //deobfCompile 'com.mod-buildcraft:buildcraft:6.0.8:dev'
    //deobfProvided 'com.mod-buildcraft:buildcraft:6.0.8:dev'

    // for more info...
    // http://www.gradle.org/docs/current/userguide/artifact_dependencies_tutorial.html
    // http://www.gradle.org/docs/current/userguide/dependency_management.html

}

processResources {
    // this will ensure that this task is redone when the versions change.
    inputs.property "version", project.version
    inputs.property "mcversion", project.minecraft.version

    // replace stuff in mcmod.info, nothing else
    from(sourceSets.main.resources.srcDirs) {
        include 'mcmod.info'

        // replace version and mcversion
        expand 'version':project.version, 'mcversion':project.minecraft.version
    }

    // copy everything else except the mcmod.info
    from(sourceSets.main.resources.srcDirs) {
        exclude 'mcmod.info'
    }
}

sourceSets {
    main { output.resourcesDir = output.classesDir }
}

     */
    private static final List<String> TABLES = ImmutableList.of(
            "inject/abandoned_mineshaft",
            "inject/desert_pyramid",
            "inject/jungle_temple",
            "inject/simple_dungeon",
            "inject/stronghold_corridor",
            "inject/village_blacksmith",
            "inject/end_city_treasure",
            "inject/igloo_chest",
            "inject/nether_bridge",
            "inject/stronghold_crossing",
            "inject/stronghold_library",
            "inject/woodland_mansion"
    );

    public LootTableEventHandler() {
        for (String s : TABLES) {
            LootTableList.register(new ResourceLocation(Elementaristics.MODID, s));
        }
    }
    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {
        String prefix = "minecraft:chests/";
        String name = evt.getName().toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file) {
                case "abandoned_mineshaft":
                case "end_city_treasure":
                case "igloo_chest":
                case "nether_bridge":
                case "stronghold_crossing":
                case "stronghold_library":
                case "woodland_mansion":
                case "desert_pyramid":
                case "jungle_temple":
                case "simple_dungeon":
                case "stronghold_corridor":
                case "village_blacksmith": evt.getTable().addPool(getInjectPool(file)); break;
                default: break;
            }
        }
    }

    private LootPool getInjectPool(String entryName) {
        return new LootPool(new LootEntry[]{getInjectEntry(entryName, 1)}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "elementaristics_inject_pool");
    }

    private LootEntryTable getInjectEntry(String name, int weight) {
        return new LootEntryTable(new ResourceLocation(Elementaristics.MODID, "inject/" + name), weight, 0, new LootCondition[0], "elementaristics_inject_entry");
    }

}
