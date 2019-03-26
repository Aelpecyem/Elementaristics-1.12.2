package de.aelpecyem.elementaristics.compat.thaumcraft.focus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.NodeSetting;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.tiles.crafting.TileInfusionMatrix;

public class FocusEffectStabilize extends FocusEffect {

    public FocusEffectStabilize() {
    }

    public static boolean stabilizeObject(World world, BlockPos pos, float power) {
        for (int x = -2; x < 2; x++) {
            for (int y = -2; y < 2; y++) {
                for (int z = -2; z < 2; z++) {
                    BlockPos position = new BlockPos(x + Math.floor(pos.getX()), y + Math.floor(pos.getY()), z + Math.floor(pos.getZ()));
                    if (world.getTileEntity(position) instanceof TileInfusionMatrix) {
                        TileInfusionMatrix matrix = (TileInfusionMatrix) world.getTileEntity(position);
                        matrix.stabilityReplenish += power;
                        System.out.println("STABILIZING");
                        matrix.stability += power;
                    }

                }  //EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
            }
        }


        return true;
    }

    public String getResearch() {
        return "FOCUSSTABILIZE";
    }

    public String getKey() {
        return "elementaristics.STABILIZE";
    }

    public Aspect getAspect() {
        return Aspect.ORDER;
    }

    public int getComplexity() {
        return 7 + (this.getSettingValue("power") + 2) * 3;
    }

    public boolean execute(RayTraceResult target, Trajectory trajectory, float finalPower, int num) {
        stabilizeObject(this.getPackage().world, target.getBlockPos(), this.getSettingValue("power"));
        return true;
    }

    public NodeSetting[] createSettings() {
        int[] stabilization = new int[]{1, 2, 4, 6, 8};
        String[] stabiDesc = new String[]{"1", "2", "3", "4", "5"};
        return new NodeSetting[]{new NodeSetting("power", "focus.stabilization.power", new NodeSetting.NodeSettingIntList(stabilization, stabiDesc))};
    }

    @SideOnly(Side.CLIENT)
    public void renderParticleFX(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        FXGeneric fb = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        fb.func_187114_a(16 + world.rand.nextInt(16));
        fb.setParticles(384 + world.rand.nextInt(16), 1, 1);
        fb.setSlowDown(0.75D);
        fb.setAlphaF(new float[]{1.0F, 0.0F});
        fb.setScale(new float[]{(float) (0.699999988079071D + world.rand.nextGaussian() * 0.30000001192092896D)});
        fb.func_70538_b(0.0F, 0.0F, 1.0F);
        fb.setRandomMovementScale(0.01F, 0.01F, 0.01F);
        ParticleEngine.addEffectWithDelay(world, fb, 0);
    }

    public void onCast(Entity caster) {
        caster.world.playSound((EntityPlayer) null, caster.getPosition(), SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 0.2F, 0.7F);
    }
}
