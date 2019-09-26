package de.aelpecyem.elementaristics.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;
import java.util.List;

public class MiscUtil {
    public static List<EntityLivingBase> getBoundEntities(EntityLivingBase player) {
        List<EntityLivingBase> livingsShared = player.world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase input) {
                return input.getEntityData().hasUniqueId("sharing_uuid") && input.getEntityData().getUniqueId("sharing_uuid").equals(player.getUniqueID()) && !(input.isEntityEqual(player) || !input.isNonBoss());
            }
        });
        return livingsShared;
    }

    //TODO permit more bound entities if the player is a G O D
    public static boolean addEntityToBoundEntities(EntityLivingBase player, EntityLivingBase entityToBind) {
        if (!player.world.isRemote) {
            if (getBoundEntities(player).size() < 1) {
                entityToBind.getEntityData().setUniqueId("sharing_uuid", player.getUniqueID());
                return true;
            } else {
                entityToBind.getEntityData().setUniqueId("sharing_uuid", player.getUniqueID());
                player.world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                    @Override
                    public boolean apply(@Nullable EntityLivingBase input) {
                        if (input.getEntityData().hasUniqueId("sharing_uuid") && input.getEntityData().getUniqueId("sharing_uuid").equals(player.getUniqueID())) {
                            if (input.ticksExisted > entityToBind.ticksExisted && !(getBoundEntities(player).size() < 1)) {
                                input.getEntityData().setUniqueId("sharing_uuid", input.getUniqueID());
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
        return false;
    }
}
