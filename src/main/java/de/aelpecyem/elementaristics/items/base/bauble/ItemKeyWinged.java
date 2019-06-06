package de.aelpecyem.elementaristics.items.base.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemKeyWinged extends ItemBase implements IBauble {
    protected final String CHARGE_KEY = "charge";

    public ItemKeyWinged() {
        super("key_winged");
        maxStackSize = 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.CHARM;
    }



    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        player.fallDistance *= 0.8F;
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(CHARGE_KEY)) {
            if (itemstack.getTagCompound().getFloat(CHARGE_KEY) < 10 && player.onGround) {
                itemstack.getTagCompound().setFloat(CHARGE_KEY, 10);
            }
            if (itemstack.getTagCompound().getFloat(CHARGE_KEY) > 0.1F) {
                if (player.world.isRemote) {
                    if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) && Minecraft.getMinecraft().currentScreen == null) {
                        player.motionY = 0;
                        player.fallDistance -= 10;
                        player.jumpMovementFactor += 0.1;
                        float yaw = player.rotationYaw;
                        float pitch = -36.000065F;
                        float f = 0.8F;// + (entityLivingBaseIn instanceof EntityPlayer ? ((EntityPlayer) entityLivingBaseIn).capabilities.getFlySpeed() : 0);
                        double motionY, motionX, motionZ;
                        if (player.isSneaking()) {
                            motionX = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
                            motionZ = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
                        } else {
                            motionX = player.motionX;
                            motionZ = player.motionZ;
                        }
                        motionY = player.motionY + (double) (-MathHelper.sin((pitch) / 180.0F * (float) Math.PI) * f);
                        player.setVelocity(motionX, motionY, motionZ);
                        itemstack.getTagCompound().setFloat(CHARGE_KEY, itemstack.getTagCompound().getFloat(CHARGE_KEY) - 1F);
                        Elementaristics.proxy.generateGenericParticles(player, Aspects.air.getColor(), 1, 40, 0, true, true);
                    }
                }
            }
        } else {
            if (!itemstack.hasTagCompound())
                itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setInteger(CHARGE_KEY, 0);
        }
    }


}
