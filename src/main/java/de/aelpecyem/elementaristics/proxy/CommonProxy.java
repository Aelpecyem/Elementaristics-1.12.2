package de.aelpecyem.elementaristics.proxy;

import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.IntFunction;

public class CommonProxy {

    public void registerRenderers() {
    }


    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void generateGenericParticles(Entity entityIn, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateGenericParticles(World world, double x, double y, double z, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateGenericParticles(World world, double x, double y, double z, double velX, double velY, double velZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateGenericParticles(ParticleGeneric particleGeneric) {
    }


    public void generateGenericParticles(World world, BlockPos pos, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateParticleRay(World world, float xFrom, float yFrom, float zFrom, float xTo, float yTo, float zTo, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade){

    }

    public void registerKeyBinds() {

    }

}
