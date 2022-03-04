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
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralDay1(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralDay2(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralDay3());
                output = output + " ";
                --depth;
            }

            if (hour > 0 && depth > 0) {
                output = output + hour;
                output = output + " ";
                output = output + StringUtils.formatPlural(hour,
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralHour1(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralHour1(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralHour1());
                output = output + " ";
                --depth;
            }

            if (min > 0 && depth > 0) {
                output = output + min;
                output = output + " ";
                output = output + StringUtils.formatPlural(min, accusative ?
                                WorldGuardRegionProtect.instance.utilConfigMessage.getPluralMinute1() :
                                WorldGuardRegionProtect.instance.utilConfigMessage.getPluralMinute2(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralMinute3(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralMinute4());
                output = output + " ";
                --depth;
            }

            if (sec > 0 && depth > 0) {
                output = output + sec;
                output = output + " ";
                output = output + StringUtils.formatPlural(sec, accusative ?
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralSecond1() :
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralSecond2(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralSecond3(),
                        WorldGuardRegionProtect.instance.utilConfigMessage.getPluralSecond4());
                --depth;
            }
        } else {
            output = WorldGuardRegionProtect.instance.utilConfigMessage.getPluralTimeEmpty();
        }

        return output.trim();
    }
}
