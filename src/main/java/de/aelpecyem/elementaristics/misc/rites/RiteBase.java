package de.aelpecyem.elementaristics.misc.rites;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.souls.Soul;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    private Set<Aspect> aspectsRequired = new HashSet<>();

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

    public abstract void doMagic(World world, BlockPos pos,EntityPlayer player);
    public abstract void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount);
   /* public boolean process(EntityPlayer player, int itemPower, float maganUsed){
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)){
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (itemPower >= itemPowerRequired){
                if (maganUsed >= ticksRequired){
                    if (isSoulSpecific){
                        if (cap.getSoulId() >= soulRequired.getId()){
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
            }
        }
        return false;
    }*/
}
