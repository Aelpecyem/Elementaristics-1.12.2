package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.pantheon.DeityBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemDye;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DeityGoat extends DeityBase {

    public DeityGoat() {
        super(TimeUtil.getTickTimeStartForHour(15), new ResourceLocation(Elementaristics.MODID, "deity_goat"), 7819310);
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.getEnergyStored() > 80){
            super.symbolEffect(te);
            if (growStuffAtRandomLoc(te, te.getWorld())){
                te.storage.extractEnergy(80, false);
            }
        }
    }
    public boolean growStuffAtRandomLoc(TileEntityDeityShrine te, World world){
        Random rand = world.rand;
        for (int i = 0; i < 1 + world.rand.nextInt(4); i++) {
            BlockPos pos = new BlockPos(te.getPos().getX() + rand.nextInt(16) - 8, te.getPos().getY() + rand.nextInt(16) - 8, te.getPos().getZ() + rand.nextInt(16) - 8);
            IBlockState iblockstate = world.getBlockState(pos);

            if (iblockstate.getBlock() instanceof IGrowable) {
                IGrowable igrowable = (IGrowable) iblockstate.getBlock();

                if (igrowable.canGrow(world, pos, iblockstate, world.isRemote)) {
                    if (!world.isRemote) {
                        if (igrowable.canUseBonemeal(world, world.rand, pos, iblockstate)) {
                            igrowable.grow(world, world.rand, pos, iblockstate);
                            for (int j = 0; j < 20 + world.rand.nextInt(3); j++)
                                Elementaristics.proxy.generateGenericParticles(world, pos.add(0, 1, 0), getColor(), 1 + (float) rand.nextGaussian(), 80 + rand.nextInt(20), 0.1F, true, true);
                        }

                    }
                    return true;
                }
            }
        }
        return false;
    }
}


