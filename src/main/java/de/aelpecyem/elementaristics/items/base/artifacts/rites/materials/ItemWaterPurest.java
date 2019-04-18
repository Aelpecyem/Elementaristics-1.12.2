package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.consumable.ItemDrinkBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemWaterPurest extends ItemDrinkBase implements IHasRiteUse {
    public ItemWaterPurest() {
        super("water_purest");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        entityLiving.heal(2);
        PotionEffect[] effects = new PotionEffect[]{

        };
        effects = entityLiving.getActivePotionEffects().toArray(effects);
        for (PotionEffect effect : effects) {
            if (effect.getPotion().isBadEffect()) {
                entityLiving.removePotionEffect(effect.getPotion());
            }
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      /*  if (extraLore != null){
            tooltip.add(I18n.format(extraLore));
            tooltip.add(" ");
        }*/
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
        aspects.add(Aspects.water);
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
