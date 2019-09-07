package de.aelpecyem.elementaristics.misc.rites;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RiteBase {
    public ResourceLocation name;
    private float ticksRequired;
    private float maganDrainedPerTick;
    private int itemPowerRequired;
    private Soul soulRequired;
    private boolean isSoulSpecific;

    private Set<Aspect> aspectsRequired = new HashSet<>(); //todo, rework that a bit, with the impending changes to altars

    /*TODO, THE REWORK
     * A few things will change with the rework:
     *   1: Rites will have a rite invoker specified, and will have a list of participants
     *   2: Rites may only be cast on an Altar if the caster is in a cult with the person to have set the Altar
     * */

    public RiteBase(ResourceLocation name, float ticksRequired, float maganDrainedPerTick, int itemPowerRequired, Aspect... aspectsRequired){
        this.name = name;
        this.ticksRequired = ticksRequired;
        this.maganDrainedPerTick = maganDrainedPerTick;
        this.itemPowerRequired = itemPowerRequired;

        List<Aspect> aspects = Arrays.asList(aspectsRequired);
        this.aspectsRequired.addAll(aspects);

        isSoulSpecific = false;
    }
    public RiteBase(ResourceLocation name, float ticksRequired, float maganDrainedPerTick, int itemPowerRequired, Soul soulRequired, Aspect... aspectsRequired){
        this.name = name;
        this.ticksRequired = ticksRequired;
        this.maganDrainedPerTick = maganDrainedPerTick;
        this.itemPowerRequired = itemPowerRequired;

        List<Aspect> aspects = Arrays.asList(aspectsRequired);
        this.aspectsRequired.addAll(aspects);

        this.soulRequired = soulRequired;
        isSoulSpecific = true;
    }

    public ResourceLocation getName() {
        return name;
    }

    public int getItemPowerRequired() {
        return itemPowerRequired;
    }

    public float getTicksRequired() {
        return ticksRequired;
    }

    public Soul getSoulRequired() {
        return soulRequired;
    }

    public boolean isSoulSpecific() {
        return isSoulSpecific;
    }

    public Set<Aspect> getAspectsRequired() {
        return aspectsRequired;
    }

    public float getMaganDrainedPerTick() {
        return maganDrainedPerTick;
    }

    public void doMagic(EntityDimensionalNexus nexus) {
    }

    public void onRitual(EntityDimensionalNexus nexus) {
    }

    public int getColor() {
        return !getAspectsRequired().iterator().hasNext() ? Aspects.vacuum.getColor() : getAspectsRequired().iterator().next().getColor();
    }

    public boolean areSoulsValid(Set<Soul> souls) {
        return !isSoulSpecific || souls.contains(getSoulRequired());
    }

    public void playSound(EntityDimensionalNexus nexus) {
        nexus.world.playSound(null, nexus.posX, nexus.posY, nexus.posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.AMBIENT, 1, 1F);
    }
}
