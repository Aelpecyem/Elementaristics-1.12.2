package de.aelpecyem.elementaristics.items.base.burnable;

import de.aelpecyem.elementaristics.items.base.ItemBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;

public class ItemBurnableAffectingBase extends ItemBase {

    public ItemBurnableAffectingBase(String name) {
        super(name);
    }

    public void affect(EntityItem itemIn, EntityPlayer player) {

    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem.isBurning()) {
            List<EntityPlayer> players = entityItem.getEntityWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(new BlockPos(entityItem.getPosition().getX() - 5, entityItem.getPosition().getY() - 2, entityItem.getPosition().getZ() - 5), new BlockPos(entityItem.getPosition().getX() + 5, entityItem.getPosition().getY() + 5, entityItem.getPosition().getZ() + 5)));
            if (players.size() > 0) {
                Iterator var3 = players.iterator();
                while (var3.hasNext()) {
                    EntityPlayer e = (EntityPlayer) var3.next();
                    affect(entityItem, e);
                }
            }
            entityItem.setDead();
        }
        return super.onEntityItemUpdate(entityItem);
    }
}
