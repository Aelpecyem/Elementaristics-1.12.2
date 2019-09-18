package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.render.models.pantheon.ModelGoat;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.pantheon.DeityBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.block.IGrowable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DeityGoat extends DeityBase {

    public DeityGoat() {
        super(TimeUtil.getTickTimeStartForHour(15), new ResourceLocation(Elementaristics.MODID, "deity_goat"), 7819310);
    }


    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Elementaristics.MODID, "textures/blocks/shrines/goat.png");
    }

    @Override
    public ModelBase getModel() {
        return new ModelGoat();
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.getEnergyStored() > 90) {
            super.statueEffect(te);
            if (te.tickCount % 100 == 0 && growStuffAtRandomLoc(te, te.getWorld())) {
                te.storage.extractEnergy(80, false);
            }
        }
    }

    @Override
    public void statueEffect(TileEntityDeityShrine te) {
        if (te.storage.getEnergyStored() > 20) {
            super.statueEffect(te);
            if (te.tickCount % 60 == 0) {
                for (int i = 0; i < 1 + te.getWorld().rand.nextInt(5); i++) {
                    if (te.storage.getEnergyStored() > 20 && growStuffAtRandomLoc(te, te.getWorld()))
                        te.storage.extractEnergy(20, false);
                }
            }
        }
    }

    public boolean growStuffAtRandomLoc(TileEntityDeityShrine te, World world) {
        int size = te.isStatue ? 16 : 8;
        List<BlockPos> blocks = StreamSupport.stream(BlockPos.getAllInBox(new BlockPos(te.getPos().getX() - size, te.getPos().getY() - size, te.getPos().getZ() - size), new BlockPos(te.getPos().getX() + size, te.getPos().getY() + size, te.getPos().getZ() + size)).spliterator(), true).filter(b -> world.getBlockState(b).getBlock() instanceof IGrowable && ((IGrowable) world.getBlockState(b).getBlock()).canGrow(world, b, world.getBlockState(b), world.isRemote)).collect(Collectors.toList());
        Collections.shuffle(blocks);
        for (BlockPos blockIn : blocks) {
            IGrowable igrowable = (IGrowable) world.getBlockState(blockIn).getBlock();
            if (!world.isRemote) {
                if (igrowable.canUseBonemeal(world, world.rand, blockIn, world.getBlockState(blockIn))) {
                    igrowable.grow(world, world.rand, blockIn, world.getBlockState(blockIn));
                    for (int j = 0; j < 20 + world.rand.nextInt(3); j++)
                        Elementaristics.proxy.generateGenericParticles(world, blockIn.add(0, 1, 0), getColor(), 1 + (float) world.rand.nextGaussian(), 140 + world.rand.nextInt(20), 0.1F, true, true);
                }

            }
            return true;
        }
        return false;
    }
}


