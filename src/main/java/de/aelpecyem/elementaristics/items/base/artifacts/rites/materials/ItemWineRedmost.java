package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.consumable.ItemDrinkBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemWineRedmost extends ItemDrinkBase implements IHasRiteUse {
    public ItemWineRedmost() {
        super("wine_redmost");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        entityLiving.heal(2);
        entityLiving.addPotionEffect(new PotionEffect(PotionInit.ecstasy, 1800, 2, true, true));
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_RED + I18n.format("tooltip.wine_redmost.name"));
        tooltip.add(I18n.format("tooltip.aspect_tool.power") + " " + getPower());

        tooltip.add(I18n.format("tooltip.aspect_tool.aspects"));
        for (Aspect aspect : this.getAspects()) {
            tooltip.add("-" + aspect.getLocalizedName());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public List<Aspect> getAspects() {
        List<Aspect> aspects = new ArrayList<>();
        aspects.add(Aspects.body);
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
