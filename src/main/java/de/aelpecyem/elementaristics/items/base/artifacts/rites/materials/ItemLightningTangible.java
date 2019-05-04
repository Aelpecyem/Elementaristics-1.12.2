package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.consumable.ItemFoodBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemLightningTangible extends ItemBase implements IHasRiteUse {
    public ItemLightningTangible() {
        super("lightning_tangible");
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        stack.shrink(1);
        double x = playerIn.posX + worldIn.rand.nextInt(3) * (worldIn.rand.nextBoolean() ? 1 : -1);
        double z = playerIn.posZ + worldIn.rand.nextInt(3) * (worldIn.rand.nextBoolean() ? 1 : -1);
        worldIn.spawnEntity(new EntityLightningBolt(worldIn, x, playerIn.posY, z, false));
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public List<Aspect> getAspects() {
        List<Aspect> aspects = new ArrayList<>();
        aspects.add(Aspects.electricity);
        return aspects;
    }

    @Override
    public int getPower() {
        return 6;
    }

    @Override
    public boolean isConsumed() {
        return true;
    }
}
