package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.spell.*;
import de.aelpecyem.elementaristics.misc.spell.damage.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

public class SpellInit {
    public static Map<ResourceLocation, SpellBase> spells = new HashMap<>();

    public static SpellBase spell_attack_mana;
    public static SpellBase spell_attack_magan;
    public static SpellBase spell_attack_air;
    public static SpellBase spell_attack_earth;
    public static SpellBase spell_attack_water;
    public static SpellBase spell_attack_fire;
    public static SpellBase spell_attack_aether;
    public static SpellBase spell_attack_generic;
    public static SpellBase spell_attack_nullifying;
    public static SpellBase spell_attack_dragon;
    public static SpellBase spell_attack_ancient;

    public static SpellBase spell_form_gaseous;
    public static SpellBase spell_protection_crystal;
    public static SpellBase spell_blink;
    public static SpellBase spell_cleanse;
    public static SpellBase spell_fireball_charge;

    public static SpellBase spell_activate;
    public static SpellBase spell_counterspell;

    public static void initSpells() {
        spell_attack_mana = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_mana"), 10, 10, 40, DamageSource.MAGIC, 6, Aspects.mana.getColor());
        spell_attack_magan = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_magan"), 15, 14, 40, DamageSource.MAGIC, 5, Aspects.magan.getColor());
        spell_attack_air = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_air"), 20, 16, 40, Elementaristics.DAMAGE_AIR, 5, Aspects.air.getColor());
        spell_attack_earth = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_earth"), 20, 16, 40, DamageSource.GENERIC, 5, Aspects.earth.getColor());
        spell_attack_water = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_water"), 20, 16, 40, DamageSource.DROWN, 5, Aspects.water.getColor());
        spell_attack_fire = new DamageSpellFire();
        spell_attack_aether = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_aether"), 20, 16, 40, Elementaristics.DAMAGE_AETHER, 5, Aspects.aether.getColor());
        spell_attack_generic = new DamageSpellBase(new ResourceLocation(Elementaristics.MODID, "damage_generic"), 12, 15, 20, DamageSource.GENERIC, 5, Aspects.order.getColor(), SoulInit.soulBalanced.getParticleColor());
        spell_attack_nullifying = new DamageSpellImmutable();
        spell_attack_dragon = new DamageSpellDragon();
        spell_attack_ancient = new DamageSpellAncient();

        spell_form_gaseous = new SpellFormGaseous();
        spell_protection_crystal = new SpellCrystalProtection();
        spell_blink = new SpellBlink();
        spell_cleanse = new SpellCleanse();
        spell_fireball_charge = new SpellFireballCharge();

        spell_activate = new SpellActivate();
        spell_counterspell = new SpellCounterspell();
    }
}
