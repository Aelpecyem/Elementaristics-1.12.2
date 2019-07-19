package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMossEverchanging extends ItemAspects implements IHasRiteUse {
    public static final String CONSUMED_MOSS = "consumed_moss";
    public ItemMossEverchanging() {
        super("moss_everchanging", 6, true, Aspects.fire);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (!(target instanceof EntitySheep)) return false;
        if (target.getEntityData().hasKey(CONSUMED_MOSS) && !target.getEntityData().getBoolean(CONSUMED_MOSS)){
            target.getEntityData().setBoolean(CONSUMED_MOSS, true);
            playerIn.getHeldItem(hand).shrink(1);
        }
        if(playerIn.world.isRemote){
            for (int i = 0; i < 20; i++)
            Elementaristics.proxy.generateGenericParticles(target, Aspects.life.getColor(), 2, 200, 0, true, false);
        }
        return true;
    }
}
