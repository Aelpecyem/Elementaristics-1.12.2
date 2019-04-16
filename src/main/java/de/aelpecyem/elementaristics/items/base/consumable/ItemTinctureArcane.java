package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTinctureArcane extends ItemDrinkBase {
    public ItemTinctureArcane() {
        super("tincture_arcane");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (!worldIn.isRemote) {
            entityLiving.addPotionEffect(new PotionEffect(PotionInit.potionIntoxicated, 400, 0));
        }
        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
