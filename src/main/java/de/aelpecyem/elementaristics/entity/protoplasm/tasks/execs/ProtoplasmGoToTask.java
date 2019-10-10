package de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ProtoplasmGoToTask extends ProtoplasmTask {
    BlockPos posTo;

    public ProtoplasmGoToTask() {
        super("pos");
    }

    public ProtoplasmGoToTask(BlockPos posTo) {
        super("pos");
        this.posTo = posTo;
    }

    @Override
    public boolean execute(EntityProtoplasm.AIPerformTasks slimeAI) {
        if (slimeAI.slime.getNavigator() != null && posTo != null) {
            slimeAI.slime.getNavigator().tryMoveToXYZ(posTo.getX(), posTo.getY(), posTo.getZ(), 1.0D);
        }
        return true;
    }


    @Override
    public boolean continueExecuting(EntityProtoplasm.AIPerformTasks slimeAI) {
        if (slimeAI.slime.getNavigator() != null && posTo != null) {
            slimeAI.slime.getNavigator().tryMoveToXYZ(posTo.getX(), posTo.getY(), posTo.getZ(), 1.0D);
        }
        return true;
    }

    @Override
    public boolean isFinished(EntityProtoplasm.AIPerformTasks slimeAI) { //change the box to be more centered
        boolean finished = slimeAI.slime.world.getEntitiesWithinAABB(EntityProtoplasm.class, new AxisAlignedBB(posTo.getX() + slimeAI.slime.width / 4, posTo.getY() + slimeAI.slime.height / 4, posTo.getZ() + slimeAI.slime.width / 4, posTo.getX() + 1 + slimeAI.slime.width / 4, posTo.getY() + 1 + slimeAI.slime.height / 4, posTo.getZ() + 1 + slimeAI.slime.width / 4)).contains(slimeAI.slime);//posTo.equals(slimeAI.slime.getPosition());//slimeAI.slime.world.getEntitiesWithinAABB(EntityProtoplasm.class, new AxisAlignedBB(xTo - 0.5, yTo - 0.5, zTo - 0.5, xTo + 0.5, yTo + 0.5, zTo + 0.5)).contains(slimeAI.slime);
        if (finished) {
            slimeAI.slime.getNavigator().clearPath();
        }
        return finished;
    }

    @Override
    public ProtoplasmTask applyAttributes(String[] taskParts) {
        return new ProtoplasmGoToTask(BlockPos.fromLong(Long.valueOf(taskParts[1])));
    }

    @Override
    public String writeAsString() {
        return name + "," + posTo.toLong() + ";";
    }


    @Override
    public EnumActionResult setTask(ItemStack stack, ItemThaumagral item, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        item.appendTask(stack, new ProtoplasmGoToTask(pos.offset(facing)), player);
        return EnumActionResult.SUCCESS;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getHudDescription() {
        return I18n.format("hud.goto") + " X: " + posTo.getX() + " Y: " + posTo.getY() + " Z: " + posTo.getZ();
    }

    public BlockPos getPosTo() {
        return posTo;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getParticles(int indexAt, List<ProtoplasmTask> taskList, @Nullable ProtoplasmTask prevTask, @Nullable ProtoplasmTask nextTask, World world, EntityPlayer player, ItemStack heldItem, ItemThaumagral item) {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posTo.getX() + 0.5 + (world.rand.nextGaussian() / 8), posTo.getY() + 0.5 + (world.rand.nextGaussian() / 8), posTo.getZ() + 0.5 + (world.rand.nextGaussian() / 8), 0, 0, 0, nextTask instanceof ProtoplasmWaitTask ? 2555985 : 16740608, prevTask instanceof ProtoplasmWaitTask ? world.rand.nextFloat() + 0.5F : world.rand.nextFloat() + 0.1F, 100, 0, false, false, true, false, 0, 0, 0));
        super.getParticles(indexAt, taskList, prevTask, nextTask, world, player, heldItem, item);
    }

}
