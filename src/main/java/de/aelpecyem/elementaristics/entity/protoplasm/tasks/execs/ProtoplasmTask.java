package de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs;

import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ProtoplasmTask {
    protected String name;

    public ProtoplasmTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean execute(EntityProtoplasm.AIPerformTasks slimeAI);

    public abstract boolean continueExecuting(EntityProtoplasm.AIPerformTasks slimeAI);

    public abstract boolean isFinished(EntityProtoplasm.AIPerformTasks slimeAI);

    public abstract ProtoplasmTask applyAttributes(String[] taskParts);

    public abstract String writeAsString();

    @SideOnly(Side.CLIENT)
    public String getHudDescription(){
        return name;
    }

    public ActionResult<ItemStack> setTask(ItemStack stack, ItemThaumagral item, World worldIn, EntityPlayer playerIn, EnumHand handIn){
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    public EnumActionResult setTask(ItemStack stack, ItemThaumagral item, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        return EnumActionResult.PASS;
    }

    public void getParticles(int indexAt, List<ProtoplasmTask> taskList, @Nullable ProtoplasmTask prevTask, @Nullable ProtoplasmTask nextTask, World world, EntityPlayer player, ItemStack heldItem, ItemThaumagral item) {

    }
}
