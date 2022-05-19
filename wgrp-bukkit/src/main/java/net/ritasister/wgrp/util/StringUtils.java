package net.ritasister.wgrp.util;

import org.bukkit.ChatColor;

public class StringUtils {

    public static String formatPlural(int count, String str1, String str2, String str3) {
        return switch(count % 10 == 1 && count % 100 != 11 ? 0 : (count % 10 < 2 || count % 10 > 4 || count % 100 >= 10 && count % 100 < 20 ? 2 : 1)) {
            case 1 -> str2;
            case 2 -> str3;
            default -> str1;
        };
    }

    public static String getSpace(String symbol, int length) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < length; ++i) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    public static String getCenterText(String base, String text) {
        String base_no_color = ChatColor.stripColor(base);
        String color_code = base.substring(0, base.length() - base_no_color.length());
        String symbol = base.substring(base_no_color.length() - 1);
        int i = (base_no_color.length() - (ChatColor.stripColor(text).length() + 2)) / 2;
        String output = color_code + base_no_color.substring(0, i) + " " + text + " " + color_code + base_no_color.substring(base_no_color.length() - i);
        if (output.length() < base_no_color.length()) {
            output = output + symbol;
        }
        return output;
    }
}

