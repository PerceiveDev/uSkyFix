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

    private static Pattern regex = Pattern.compile("\\[usb([1-9]|10)\\]");

    public static boolean isLeaderboardSign(Block sign) {
        return sign.getState() instanceof Sign && isLeaderboardLine(((Sign) sign.getState()).getLine(0));
    }

    public static boolean isLeaderboardLine(String text) {
        return ChatColor.stripColor(text.toLowerCase()).matches(regex.pattern());
    }

    public static int getPlace(Block block) {

        return getPlace(((Sign) block.getState()).getLine(0));

    }

    public static int getPlace(String text) {

        Matcher matcher = regex.matcher(ChatColor.stripColor(text.toLowerCase()));
        if (!matcher.matches()) {
            return -1;
        }

        return Integer.parseInt(matcher.group(1));
    }

}
