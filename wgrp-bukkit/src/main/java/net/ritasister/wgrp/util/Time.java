package net.ritasister.wgrp.util;

import net.ritasister.wgrp.util.config.Message;
import org.jetbrains.annotations.NotNull;

public class Time {

    public static @NotNull String getTimeToString(int time, int depth, boolean accusative) {
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
                        Message.pluralDay1.toString(),
                        Message.pluralDay2.toString(),
                        Message.pluralDay3.toString());
                output = output + " ";
                --depth;
            } if (hour > 0 && depth > 0) {
                output = output + hour;
                output = output + " ";
                output = output + StringUtils.formatPlural(hour,
                        Message.pluralHour1.toString(),
                        Message.pluralHour2.toString(),
                        Message.pluralHour3.toString());
                output = output + " ";
                --depth;
            } if (min > 0 && depth > 0) {
                output = output + min;
                output = output + " ";
                output = output + StringUtils.formatPlural(min, accusative ?
                                Message.pluralMinute1.toString() :
                                Message.pluralMinute2.toString(),
                        Message.pluralMinute3.toString(),
                        Message.pluralMinute4.toString());
                output = output + " ";
                --depth;
            } if (sec > 0 && depth > 0) {
                output = output + sec;
                output = output + " ";
                output = output + StringUtils.formatPlural(sec, accusative ?
                                Message.pluralSecond1.toString() :
                                Message.pluralSecond2.toString(),
                        Message.pluralSecond3.toString(),
                        Message.pluralSecond4.toString());
                --depth;
            }
        } else {
            output = Message.pluralTimeEmpty.toString();
        }
        return output.trim();
    }
}
