package net.ritasister.wgrp.util;

import net.ritasister.wgrp.util.config.Message;
import org.jetbrains.annotations.NotNull;

public class Time {

    /*public static @NotNull String getTimeToString(int time, int depth, boolean accusative) {
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
                        Message.PluralTime_day_pluralDay1.toString(),
                        Message.PluralTime_day_pluralDay2.toString(),
                        Message.PluralTime_day_pluralDay3.toString());
                output = output + " ";
                --depth;
            } if (hour > 0 && depth > 0) {
                output = output + hour;
                output = output + " ";
                output = output + StringUtils.formatPlural(hour,
                        Message.PluralTime_hour_pluralHour1.toString(),
                        Message.PluralTime_hour_pluralHour2.toString(),
                        Message.PluralTime_hour_pluralHour3.toString());
                output = output + " ";
                --depth;
            } if (min > 0 && depth > 0) {
                output = output + min;
                output = output + " ";
                output = output + StringUtils.formatPlural(min, accusative ?
                                Message.PluralTime_minute_pluralMinute1.toString() :
                                Message.PluralTime_minute_pluralMinute2.toString(),
                        Message.PluralTime_minute_pluralMinute3.toString(),
                        Message.PluralTime_minute_pluralMinute4.toString());
                output = output + " ";
                --depth;
            } if (sec > 0 && depth > 0) {
                output = output + sec;
                output = output + " ";
                output = output + StringUtils.formatPlural(sec, accusative ?
                                Message.PluralTime_second_pluralSecond1.toString() :
                                Message.PluralTime_second_pluralSecond2.toString(),
                        Message.PluralTime_second_pluralSecond3.toString(),
                        Message.PluralTime_second_pluralSecond4.toString());
                --depth;
            }
        } else {
            output = Message.PluralTime_timeEmpty_pluralTimeEmpty.toString();
        }
        return output.trim();
    }*/
}
