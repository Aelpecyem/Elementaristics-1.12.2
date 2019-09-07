package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.bauble.ItemCultBadge;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class RitePact extends RiteBase {

    public RitePact() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_pact"), 400, 0.1F, 6, Aspects.mind, Aspects.soul);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityItem> targets = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(4), e -> e.getItem().getItem() instanceof ItemCultBadge && !((ItemCultBadge) e.getItem().getItem()).getOwnerUUID(e.getItem()).isEmpty());
        if (targets.size() > 1) {
            EntityItem badge1 = targets.get(0);
            EntityItem badge2 = targets.get(1);

            if (!ModItems.badge_cult.getMemberStrings(badge1.getItem()).contains(ModItems.badge_cult.getOwnerUUID(badge2.getItem()))) {
                ModItems.badge_cult.setMemberString(badge1.getItem(), ModItems.badge_cult.getMembersString(badge1.getItem()) + ModItems.badge_cult.getOwnerUUID(badge2.getItem()) + ";");
            }
            if (!ModItems.badge_cult.getMemberStrings(badge2.getItem()).contains(ModItems.badge_cult.getOwnerUUID(badge1.getItem()))) {
                ModItems.badge_cult.setMemberString(badge2.getItem(), ModItems.badge_cult.getMembersString(badge2.getItem()) + ModItems.badge_cult.getOwnerUUID(badge1.getItem()) + ";");
            }
            if (ModItems.badge_cult.getOwner(nexus.world, badge1.getItem()) != null) {
                ItemHandlerHelper.giveItemToPlayer(ModItems.badge_cult.getOwner(nexus.world, badge1.getItem()), badge1.getItem());
                badge1.setDead();
            }
            if (ModItems.badge_cult.getOwner(nexus.world, badge2.getItem()) != null) {
                ItemHandlerHelper.giveItemToPlayer(ModItems.badge_cult.getOwner(nexus.world, badge2.getItem()), badge2.getItem());
                badge2.setDead();
            }
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        List<Entity> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(4), p -> p instanceof EntityItem && ((EntityItem) p).getItem().getItem() instanceof ItemCultBadge && !((ItemCultBadge) ((EntityItem) p).getItem().getItem()).getOwnerUUID(((EntityItem) p).getItem()).isEmpty());
        for (Entity entity : entities) {
            nexus.suckInEntity(entity, entity.height / 2);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.mana.getColor(), 1, 60, 0, false, false);
        }
    }
}
