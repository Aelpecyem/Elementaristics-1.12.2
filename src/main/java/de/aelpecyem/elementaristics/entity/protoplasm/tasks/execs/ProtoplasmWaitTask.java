package de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProtoplasmWaitTask extends ProtoplasmTask{
    int time;
    public ProtoplasmWaitTask() {
        super("wait");
    }

    public ProtoplasmWaitTask(int time) {
        super("wait");
        this.time = time;
    }

    @Override
    public boolean execute(EntityProtoplasm.AIPerformTasks slimeAI) {
        slimeAI.timer++;
        return true;
    }


    @Override
    public boolean continueExecuting(EntityProtoplasm.AIPerformTasks slimeAI) {
        slimeAI.timer++;
        return true;
    }

    @Override
    public boolean isFinished(EntityProtoplasm.AIPerformTasks slimeAI) {
        if (slimeAI.timer >= time){
            slimeAI.timer = 0;
            return true;
        }
        return false;
    }

    @Override
    public ProtoplasmTask applyAttributes(String[] taskParts) {
        return new ProtoplasmWaitTask(Integer.valueOf(taskParts[1]));
    }

    @Override
    public ActionResult<ItemStack> setTask(ItemStack stack, ItemThaumagral item, World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        try{
            if (playerIn.isSneaking()){
                int currentTimer = Integer.valueOf(item.getWIPTask(stack));
                item.setWIPTask(stack, String.valueOf(currentTimer + 20));
                if (worldIn.isRemote) {
                    playerIn.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + String.valueOf(Integer.valueOf(item.getWIPTask(stack)) / 20) +"s"), true);//PacketHandler.sendTo(playerIn, new PacketMessage("message.task_timer"));
                }
            }else if (Integer.valueOf(item.getWIPTask(stack)) > 0){
                item.appendTask(stack, new ProtoplasmWaitTask(Integer.valueOf(item.getWIPTask(stack))), playerIn);
                item.setWIPTask(stack, "0");
            }else{
                if (!worldIn.isRemote) {
                    PacketHandler.sendTo(playerIn, new PacketMessage("message.task_timer_cant_add"));
                }
            }
        }
        catch (NumberFormatException e){
            item.setWIPTask(stack, "0");
        }

        return super.setTask(stack, item, worldIn, playerIn, handIn);
    }

    @Override
    public String writeAsString() {
        return name + "," + time +";";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getHudDescription() {
        return I18n.format("hud.wait") + " " + time/20 + "s";
    }
}
