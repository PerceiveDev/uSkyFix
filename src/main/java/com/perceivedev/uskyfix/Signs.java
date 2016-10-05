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
    private static String  signColor1;
    private static String  signColor2;
    private static String  signColor3;
    private static String  signColor4;

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
        sign.setLine(0, color(signColor1 + "[Skyblock]"));
        sign.setLine(1, color(signColor2 + place + suffix(place) + " place:"));
        sign.setLine(2, color(signColor3 + line2));
        sign.setLine(3, color(signColor4 + line3));
        sign.update();
        return sign.getLines();
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * @param signColor1
     * @param signColor2
     * @param signColor3
     * @param signColor4
     */
    public static void setSignColors(String signColor1, String signColor2, String signColor3, String signColor4) {
        Signs.signColor1 = signColor1;
        Signs.signColor2 = signColor2;
        Signs.signColor3 = signColor3;
        Signs.signColor4 = signColor4;
    }

}
