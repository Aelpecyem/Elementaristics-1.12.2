package de.aelpecyem.elementaristics.capability.souls;

import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.client.resources.I18n;

public class Soul {
    private int id;
    private String name;
    private int particleColor;
    private SoulInit.EnumSoulRarity rarity;
    private float maganRegenPerTick, maxMagan;

    public Soul(String name, int particleColor,SoulInit.EnumSoulRarity rarity, float maxMagan, float maganRegenPerTick) {
        this.id = SoulInit.souls.size();
        this.name = name;
        this.particleColor = particleColor;
        this.rarity = rarity;
        this.maxMagan = maxMagan;
        this.maganRegenPerTick = maganRegenPerTick;
        SoulInit.souls.add(this);
    }

    public Soul(int id, String name, int particleColor, SoulInit.EnumSoulRarity rarity) {
        this.id = id;
        this.name = name;
        this.particleColor = particleColor;
        this.rarity = rarity;
        this.maxMagan = 100;
        this.maganRegenPerTick = 0.15F;
        SoulInit.souls.add(this);
    }

    public Soul(String name, int particleColor, SoulInit.EnumSoulRarity rarity) {
        this.id = SoulInit.souls.size();
        this.name = name;
        this.particleColor = particleColor;
        this.rarity = rarity;
        this.maxMagan = 100;
        this.maganRegenPerTick = 0.15F;
        SoulInit.souls.add(this);
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Really, only call this Client-Side
     * @return
     */
    public String getLocalizedName() {
        return I18n.format("soul." + name + ".name");
    }

    public SoulInit.EnumSoulRarity getRarity() {
        return rarity;
    }

}
