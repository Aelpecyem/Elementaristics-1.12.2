package de.aelpecyem.elementaristics.misc.spell;

import de.aelpecyem.elementaristics.init.SpellInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellBase {
    protected ResourceLocation name;
    protected float maganCost;
    protected int cooldownTicks;
    protected int stuntTime;
    protected int color, color2;
    protected SpellType type;

    public SpellBase(ResourceLocation name, float maganCost, int cooldownTicks, int stuntTime, int color, int color2) {
        this.name = name;
        this.maganCost = maganCost;
        this.cooldownTicks = cooldownTicks;
        this.stuntTime = stuntTime;
        this.color = color;
        this.color2 = color2;
        this.type = SpellType.PROJECTILE;
        SpellInit.spells.put(name, this);
    }

    public SpellBase(ResourceLocation name, float maganCost, int cooldownTicks, int stuntTime, int color, int color2, SpellType type) {
        this.name = name;
        this.maganCost = maganCost;
        this.cooldownTicks = cooldownTicks;
        this.stuntTime = stuntTime;
        this.color = color;
        this.color2 = color2;
        this.type = type;
        SpellInit.spells.put(name, this);
    }

    public void setType(SpellType type) {
        this.type = type;
    }

    public SpellType getType() {
        return type;
    }

    public int getColor2() {
        return color2;
    }

    public ResourceLocation getName() {
        return name;
    }

    public float getMaganCost() {
        return maganCost;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public int getStuntTime() {
        return stuntTime;
    }

    public int getColor() {
        return color;
    }

    public void affect(RayTraceResult result, EntityLivingBase caster, World world) {

    }

    public static enum SpellType {
        PROJECTILE,
        SELF,
        EDEMA,
        WAVE
    }
}
