package de.aelpecyem.elementaristics.util;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;
import java.awt.*;
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
    public static Color convertIntToColor(int intColor) {
        Color color = new Color(intColor);
        return color;
    }

    public static Color reverseColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    public static int coverColorToInt(Color color) {
        int colorDec = (color.getRed() << 16) + (color.getGreen() << 8) + (color.getBlue());
        return colorDec;
    }

    public static Color getColorForEssenceIds(List<Integer> aspectIdList) {
        int r = 0, g = 0, b = 0;
        int relevance = 0;
        int mult = 100 + relevance * 30;
        for (int aspect : aspectIdList) {
            r += convertIntToColor(Aspects.getElementById(aspect).getColor()).getRed() * mult;
            g += convertIntToColor(Aspects.getElementById(aspect).getColor()).getGreen() * mult;
            b += convertIntToColor(Aspects.getElementById(aspect).getColor()).getBlue() * mult;
            relevance++;
        }
        if (aspectIdList.size() > 0) {
            r /= aspectIdList.size() * mult;
            g /= aspectIdList.size() * mult;
            b /= aspectIdList.size() * mult;
        } else {
            r = 255;
            g = 255;
            b = 255;
        }
        return new Color(r, g, b);
    }

    public static Color blend(Color c0, Color c1, double weightOne, double weightTwo) {
        // double totalAlpha = c0.getAlpha() + c1.getAlpha();
        double weight0 = weightOne;
        double weight1 = weightTwo;

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c0.getAlpha(), c1.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
    }
}
