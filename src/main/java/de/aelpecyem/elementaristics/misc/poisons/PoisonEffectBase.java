package de.aelpecyem.elementaristics.misc.poisons;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PoisonEffectBase {
    protected int id;
    protected int color;

    public PoisonEffectBase(int id, int color) {
        this.id = id;
        this.color = color;
        PoisonInit.poisons.put(id, this);
    }

    public void drinkEffect(World world, EntityLivingBase player) {
        performEffect(world, player);
    }

    public void performEffect(World world, EntityLivingBase player){

    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }
}
