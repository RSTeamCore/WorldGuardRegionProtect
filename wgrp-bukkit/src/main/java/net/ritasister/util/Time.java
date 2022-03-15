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
                        WorldGuardRegionProtect.utilConfigMessage.pluralDay1,
                        WorldGuardRegionProtect.utilConfigMessage.pluralDay2,
                        WorldGuardRegionProtect.utilConfigMessage.pluralDay3);
                output = output + " ";
                --depth;
            }

            if (hour > 0 && depth > 0) {
                output = output + hour;
                output = output + " ";
                output = output + StringUtils.formatPlural(hour,
                        WorldGuardRegionProtect.utilConfigMessage.pluralHour1,
                        WorldGuardRegionProtect.utilConfigMessage.pluralHour2,
                        WorldGuardRegionProtect.utilConfigMessage.pluralHour3);
                output = output + " ";
                --depth;
            }

            if (min > 0 && depth > 0) {
                output = output + min;
                output = output + " ";
                output = output + StringUtils.formatPlural(min, accusative ?
                                WorldGuardRegionProtect.utilConfigMessage.pluralMinute1 :
                                WorldGuardRegionProtect.utilConfigMessage.pluralMinute2,
                        WorldGuardRegionProtect.utilConfigMessage.pluralMinute3,
                        WorldGuardRegionProtect.utilConfigMessage.pluralMinute4);
                output = output + " ";
                --depth;
            }

            if (sec > 0 && depth > 0) {
                output = output + sec;
                output = output + " ";
                output = output + StringUtils.formatPlural(sec, accusative ?
                        WorldGuardRegionProtect.utilConfigMessage.pluralSecond1 :
                        WorldGuardRegionProtect.utilConfigMessage.pluralSecond2,
                        WorldGuardRegionProtect.utilConfigMessage.pluralSecond3,
                        WorldGuardRegionProtect.utilConfigMessage.pluralSecond4);
                --depth;
            }
        } else {
            output = WorldGuardRegionProtect.utilConfigMessage.pluralTimeEmpty;
        }

        return output.trim();
    }
}
