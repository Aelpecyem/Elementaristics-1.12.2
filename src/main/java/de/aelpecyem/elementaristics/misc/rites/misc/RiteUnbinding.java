package de.aelpecyem.elementaristics.misc.rites.misc;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class RiteUnbinding extends RiteBase {

    public RiteUnbinding() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_unbinding"), 100, 0.8F, 4, Aspects.earth);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null) {
            List<EntityLivingBase> entities = nexus.world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                @Override
                public boolean apply(@Nullable EntityLivingBase input) {
                    return input.getEntityData().hasUniqueId("sharing_uuid") && input.getEntityData().getUniqueId("sharing_uuid").equals(player.getUniqueID());
                }
            });
            for (EntityLivingBase living : entities) {
                living.getEntityData().setUniqueId("sharing_uuid", living.getUniqueID());
            }
        }

    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, Aspects.earth.getColor(), 3, 60, 0, false, false);
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null)
            Elementaristics.proxy.generateGenericParticles(player, Aspects.earth.getColor(), 3, 100, 0, false, true);
    }

}
