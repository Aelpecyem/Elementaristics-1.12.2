package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.misc.elements.Aspects;

import java.awt.*;
import java.util.List;

public class ColorUtil {
    public static boolean isDark(float r, float g, float b) {
        return isDark(r, g, b, 0.45F);
    }

    public static boolean isDark(float r, float g, float b, float threshold) {
        return r < threshold && g < threshold && b < threshold;
    }


    public static Color convertIntToColor(int intColor) {
        Color color = new Color(intColor);
        return color;
    }

    public static Color reverseColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    public static Color getColorForEssenceIds(List<Integer> aspectIdList) {
        int r = 0, g = 0, b = 0;
        int mult = 50;
        for (int aspect : aspectIdList) {
            r += convertIntToColor(Aspects.getElementById(aspect).getColor()).getRed() * mult;
            g += convertIntToColor(Aspects.getElementById(aspect).getColor()).getGreen() * mult;
            b += convertIntToColor(Aspects.getElementById(aspect).getColor()).getBlue() * mult;
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
