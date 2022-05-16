package net.ritasister.util;

import net.ritasister.wgrp.WorldGuardRegionProtect;

public class Time {

    public static String getTimeToString(int time, int depth, boolean accusative) {
        String output = "";
        if (time > 0) {
            int day = time / 86400;
            int hour = (time - day * 86400) / 3600;
            int min = (time - (day * 86400 + hour * 3600)) / 60;
            int sec = time - (day * 86400 + hour * 3600 + min * 60);
            if (day > 0 && depth > 0) {
                output = String.valueOf(day);
                output = output + " ";
                output = output + StringUtils.formatPlural(day,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralDay1,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralDay2,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralDay3);
                output = output + " ";
                --depth;
            }

            if (hour > 0 && depth > 0) {
                output = output + hour;
                output = output + " ";
                output = output + StringUtils.formatPlural(hour,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralHour1,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralHour2,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralHour3);
                output = output + " ";
                --depth;
            }

            if (min > 0 && depth > 0) {
                output = output + min;
                output = output + " ";
                output = output + StringUtils.formatPlural(min, accusative ?
                                WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralMinute1 :
                                WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralMinute2,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralMinute3,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralMinute4);
                output = output + " ";
                --depth;
            }

            if (sec > 0 && depth > 0) {
                output = output + sec;
                output = output + " ";
                output = output + StringUtils.formatPlural(sec, accusative ?
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralSecond1 :
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralSecond2,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralSecond3,
                        WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralSecond4);
                --depth;
            }
        } else {
            output = WorldGuardRegionProtect.getInstance().getUtilConfigMessage().pluralTimeEmpty;
        }

        return output.trim();
    }
}
