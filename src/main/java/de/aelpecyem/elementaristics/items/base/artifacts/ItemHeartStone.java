package de.aelpecyem.elementaristics.items.base.artifacts;


import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.materials.ItemWineRedmost;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.cap.SpawnBoundParticles;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

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
        if (target instanceof EntityVillager && attacker instanceof EntityPlayer && attacker.getHeldItemOffhand().getItem() instanceof ItemWineRedmost) {
            target.attackEntityFrom(DamageSource.GENERIC, 100);
            stack.shrink(1);
            ItemHandlerHelper.giveItemToPlayer((EntityPlayer) attacker, new ItemStack(ModItems.heart_human, 1), ((EntityPlayer) attacker).inventory.getSlotFor(stack));
            attacker.getHeldItemOffhand().shrink(1);
            target.world.playSound(null, attacker.getPosition(), SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.AMBIENT, 1, 1);
            return true;
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            for (EntityLivingBase entityLivingBase : MiscUtil.getBoundEntities(playerIn)) {
                PacketHandler.sendTo(playerIn, new SpawnBoundParticles((float) entityLivingBase.posX, (float) entityLivingBase.posY, (float) entityLivingBase.posZ, entityLivingBase.width, entityLivingBase.height));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
