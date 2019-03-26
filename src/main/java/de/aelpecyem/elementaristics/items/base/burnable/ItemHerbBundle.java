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

public class ItemHerbBundle extends ItemBurnableAffectingBase {

    public ItemHerbBundle() {
        super("bundle_herbs");
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.root"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void affect(EntityItem itemIn, EntityPlayer player) {
        for (int y = -1; y < 5; y++) {
            for (int x = -5; x < 5; x++) {
                for (int z = -5; z < 5; z++) {
                    BlockPos pos = new BlockPos(x + itemIn.getPosition().getX(), y + itemIn.getPosition().getY(), z + itemIn.getPosition().getZ());
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 65280, 3F, 200, 0, true, true);
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 255, 3F, 200, 0, true, true);
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 16711680, 3F, 200, 0, true, true);
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 16776960, 3F, 200, 0, true, true);
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 11272428, 3F, 200, 0, true, true);
                    Elementaristics.proxy.generateGenericParticles(itemIn.getEntityWorld(), pos, 16777215, 3F, 200, 0, true, true);
                }
            }
        }
        player.addPotionEffect(new PotionEffect(PotionInit.potionIntoxicated, 3500, 0, true, true));
        if (!player.world.isRemote)
            player.sendStatusMessage(new TextComponentString(I18n.format("message.drugs")), false);

        super.affect(itemIn, player);
    }

}
