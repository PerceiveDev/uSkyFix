/**
 * 
 */
package com.perceivedev.uskyfix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

/**
 * @author Rayzr
 *
 */
public class Signs {

    private static Pattern regex         = Pattern.compile("\\[usb([1-9]|10)\\]");
    private static Pattern numberMatcher = Pattern.compile("([0-9]{1,}).*");

    public static boolean isLeaderboardSign(Block sign, ConfigManager cm) {
        return sign.getState() instanceof Sign && (isLeaderboardLine(((Sign) sign.getState()).getLine(0)) || cm.isSign(sign));
    }

    public static boolean isLeaderboardLine(String text) {
        return ChatColor.stripColor(text.toLowerCase()).matches(regex.pattern());
    }

    public static int getPlace(Block block) {

        return getPlace(((Sign) block.getState()).getLine(1));

    }

    public static int getPlace(String text) {

        text = ChatColor.stripColor(text);
        Matcher matcher = numberMatcher.matcher(text);
        if (!matcher.matches()) {
            return -1;
        }

        return Integer.parseInt(matcher.group(1));
    }

    public static int getPlaceOld(String text) {
        text = ChatColor.stripColor(text.toLowerCase());
        Matcher matcher = regex.matcher(text);
        if (!matcher.matches()) {
            return -1;
        }

        return Integer.parseInt(matcher.group(1));
    }

    public static String suffix(int number) {
        switch (number) {
        case 1:
            return "st";
        case 2:
            return "nd";
        case 3:
            return "rd";
        default:
            return "th";
        }
    }

    public static String[] setLines(Sign sign, int place, String line2, String line3) {
        sign.setLine(1, color("&5" + place + suffix(place) + " place:"));
        sign.setLine(2, color("&4" + line2));
        sign.setLine(3, color("&c&l" + line3));
        sign.update();
        return sign.getLines();
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
