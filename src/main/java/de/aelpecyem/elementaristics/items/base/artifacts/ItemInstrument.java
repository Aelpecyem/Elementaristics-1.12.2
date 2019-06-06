package de.aelpecyem.elementaristics.items.base.artifacts;


import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.ModMaterials;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.BlockNote;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemInstrument extends ItemAspects {
    public static String TAG_PITCH = "pitch";
    protected SoundEvent sound;

    public ItemInstrument(String name, int itemPower, Aspect aspect, SoundEvent sound) {
        super("tympanum_empty", itemPower, false, aspect);
        this.sound = sound;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_PITCH)) {
            if (playerIn.isSneaking()) {
                // BlockNote
                stack.getTagCompound().setFloat(TAG_PITCH, stack.getTagCompound().getFloat(TAG_PITCH) + 1 % 25);
            }
            float f = (float) Math.pow(2.0D, (double) (stack.getTagCompound().getFloat(TAG_PITCH) - 12) / 12.0D);
            if (f >= 2) {
                stack.getTagCompound().setFloat(TAG_PITCH, 0);
            }
            worldIn.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, sound, SoundCategory.AMBIENT, 4, f, true);
        } else {
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setFloat(TAG_PITCH, 0);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
