package de.aelpecyem.elementaristics.compat.thaumcraft.focus;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.NodeSetting;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.entities.EntityFluxRift;
import thaumcraft.common.items.casters.foci.FocusEffectRift;

import java.util.Iterator;
import java.util.List;

public class FocusEffectRiftCloser extends FocusEffect {

    public FocusEffectRiftCloser() {

    }

    public static boolean closeRift(World world, BlockPos pos, int power, int cleanliness) {
        boolean affected = false;
        for (int x = -2; x < 2; x++) {
            if (affected) {
                break;
            }
            for (int y = -2; y < 2; y++) {
                for (int z = -2; z < 2; z++) {
                    BlockPos position = new BlockPos(x + Math.floor(pos.getX()), y + Math.floor(pos.getY()), z + Math.floor(pos.getZ()));
                    List<EntityFluxRift> targets = world.getEntitiesWithinAABB(EntityFluxRift.class, new AxisAlignedBB(new BlockPos(position.getX() - 1.5F, position.getY() - 1F, position.getZ() - 1.5F), new BlockPos(position.getX() + 1.5F, position.getY() + 2F, position.getZ() + 1F)));
                    //List<EntityFluxRift> targets = world.getCollisionBoxes(EntityFluxRift.class, (new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1))).func_186662_g(8.0D))this.field_145850_b.func_72872_a(EntityFluxRift.class, (new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1))).func_186662_g(8.0D));
                    if (targets.size() > 0) {
                        Iterator var3 = targets.iterator();
                        while (var3.hasNext()) {
                            EntityFluxRift e = (EntityFluxRift) var3.next();
                            if (e.getRiftSize() <= power) {
                                AuraHelper.polluteAura(world, pos, 10 - cleanliness, true);
                                e.setRiftSize(e.getRiftSize() - 10);
                                affected = true;
                            }

                        }
                    }
                }
            }
        }


        return true;
    }

    public String getResearch() {
        return "FOCUSRIFTCLOSER";
    }

    public String getKey() {
        return "elementaristics.RIFTCLOSER";
    }

    public Aspect getAspect() {
        return Aspect.ENTROPY;
    }

    public int getComplexity() {
        return (int) (7 + Math.floor(this.getSettingValue("power") / 20 + this.getSettingValue("cleanliness") / 2));
    }

    public boolean execute(RayTraceResult target, Trajectory trajectory, float finalPower, int num) {
        closeRift(this.getPackage().world, target.getBlockPos(), this.getSettingValue("power"), this.getSettingValue("cleanliness"));
        return true;
    }

    public NodeSetting[] createSettings() {
        int[] cleanliness = new int[]{2, 4, 6, 8, 10};
        int[] power = new int[]{30, 50, 100, 150, 250};
        String[] powerDesc = new String[]{"1", "2", "3", "4", "5"};
        String[] cleanlinessDesc = new String[]{"focus.rift_closer.cleanliness.1", "focus.rift_closer.cleanliness.2",
                "focus.rift_closer.cleanliness.3", "focus.rift_closer.cleanliness.4", "focus.rift_closer.cleanliness.5"};
        return new NodeSetting[]{new NodeSetting("power", "focus.rift_closer.power", new NodeSetting.NodeSettingIntList(power, powerDesc)),
                new NodeSetting("cleanliness", "focus.rift_closer.cleanliness", new NodeSetting.NodeSettingIntList(cleanliness, cleanlinessDesc))};
    }

    @SideOnly(Side.CLIENT)
    public void renderParticleFX(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        FXGeneric fb = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        fb.func_187114_a(16 + world.rand.nextInt(16));
        fb.setParticles(384 + world.rand.nextInt(16), 1, 1);
        fb.setSlowDown(0.75D);
        fb.setAlphaF(new float[]{1.0F, 0.0F});
        fb.setScale(new float[]{(float) (0.699999988079071D + world.rand.nextGaussian() * 0.30000001192092896D)});
        fb.func_70538_b(0.1F, 0.05F, 0.1F);
        fb.setRandomMovementScale(0.01F, 0.01F, 0.01F);
        ParticleEngine.addEffectWithDelay(world, fb, 0);
    }

    public void onCast(Entity caster) {
        caster.world.playSound((EntityPlayer) null, caster.getPosition(), SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 0.2F, 0.7F);
    }
}
