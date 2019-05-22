package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.player.EntityPlayer;

public class TimeUtil {
    public static int getHourForTimeBegin(long dayTime) {
        long hour = 6 + dayTime / 1000;
        if (hour > 24) {
            hour = hour - 24;
        }
        return (int) hour;
    }

    public static long getTickTimeStartForHour(int hour) {
        //0 = 18000; 6 = 0
        long tickTime = 18000 + (hour * 1000);
        //  tickTime += 6*1000; //6h = 0t ; 0h =-6000
        if (tickTime >= 24000) {
            tickTime = tickTime - 24000;
        }
        return tickTime;
    }

    public static long getTimeUnfalsified(long dayTime) {
        if (dayTime >= 24000) {
            dayTime = dayTime - 24000 * (dayTime / 24000);
        }
        return dayTime;
    }
}
