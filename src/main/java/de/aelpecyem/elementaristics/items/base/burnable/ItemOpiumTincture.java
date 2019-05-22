package de.aelpecyem.elementaristics.items.base.burnable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public class ItemOpiumTincture extends ItemBurnableAffectingBase {

    public ItemOpiumTincture() {
        super("tincture_opium");
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.opium.name"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void affect(EntityItem itemIn, EntityPlayer player) {
        for (int y = -1; y < 3; y++) {
            for (int x = -3; x < 3; x++) {
                for (int z = -3; z < 3; z++) {
                    BlockPos pos = new BlockPos(x + itemIn.getPosition().getX(), y + itemIn.getPosition().getY(), z + itemIn.getPosition().getZ());
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 623319, 3F, 200, 0, true, true);
                }
            }
        }
        player.addPotionEffect(new PotionEffect(PotionInit.potionTrance, 3500, 0, true, true));
        super.affect(itemIn, player);
    }

}
