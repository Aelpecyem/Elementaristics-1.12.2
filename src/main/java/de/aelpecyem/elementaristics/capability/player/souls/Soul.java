package de.aelpecyem.elementaristics.capability.player.souls;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Soul {
    private int id;
    private String name;
    private int particleColor;
    private SoulInit.EnumSoulRarity rarity;
    private float maganRegenPerTick, maxMagan, castingEfficiency; //efficiency
    private List<SpellBase> spellList = new ArrayList<>();
    private Map<SpellBase, Integer> reqMap = new HashMap<>();

    public Soul(String name, int particleColor, SoulInit.EnumSoulRarity rarity, float maxMagan, float maganRegenPerTick, float castingEfficiency) {
        this.id = SoulInit.souls.size();
        this.name = name;
        this.particleColor = particleColor;
        this.rarity = rarity;
        this.maxMagan = maxMagan;
        this.maganRegenPerTick = maganRegenPerTick;
        this.castingEfficiency = castingEfficiency;
        SoulInit.souls.add(this);
    }

    public Soul(int id, String name, int particleColor, SoulInit.EnumSoulRarity rarity) {
        this.id = id;
        this.name = name;
        this.particleColor = particleColor;
        this.rarity = rarity;
        this.maxMagan = 100;
        this.maganRegenPerTick = 0.15F;
        this.castingEfficiency = 1;
        SoulInit.souls.add(this);
    }

    public Soul(String name, int particleColor, SoulInit.EnumSoulRarity rarity) {
        this.id = SoulInit.souls.size();
        this.name = name;
        this.particleColor = particleColor;
        this.rarity = rarity;
        this.maxMagan = 100;
        this.maganRegenPerTick = 0.15F;
        this.castingEfficiency = 1;
        SoulInit.souls.add(this);
    }

    public float getCastingEfficiency() {
        return castingEfficiency;
    }

    public List<SpellBase> getSpellList() {
        return spellList;
    }

    public boolean isSpellUsable(SpellBase spell, IPlayerCapabilities capabilities) {
        return capabilities.getPlayerAscensionStage() >= reqMap.get(spell);
    }

    public void addSpellToList(SpellBase spell, int ascensionStage) {
        spellList.add(spell);
        reqMap.put(spell, ascensionStage);
    }


    public int getParticleColor() {
        return particleColor;
    }

    public float getMaxMagan() {
        return maxMagan;
    }

    public float getMaganRegenPerTick() {
        return maganRegenPerTick;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    public String getLocalizedName() {
        return I18n.format("soul." + name + ".name");
    }

    public SoulInit.EnumSoulRarity getRarity() {
        return rarity;
    }

}
