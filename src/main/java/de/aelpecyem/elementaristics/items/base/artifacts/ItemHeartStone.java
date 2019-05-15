package de.aelpecyem.elementaristics.items.base.artifacts;


import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemHeartStone extends ItemAspects {

    public ItemHeartStone() {
        super("heart_stone", 6, false, Aspects.earth);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        target.addPotionEffect(new PotionEffect(PotionInit.potionPotential, 500, 0, false, true)); //only one bound entity; more for the god ascension
        // target.getEntityData().setUniqueId("sharing_uuid", attacker.getUniqueID());
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        for (int i = 0; i < MiscUtil.getBoundEntities(playerIn).size(); i++) {
            EntityLivingBase entityLivingBase = MiscUtil.getBoundEntities(playerIn).get(i);
            Elementaristics.proxy.generateGenericParticles(entityLivingBase, Aspects.earth.getColor(), 3, 100, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(entityLivingBase, Aspects.soul.getColor(), 3, 100, 0, false, false);

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
