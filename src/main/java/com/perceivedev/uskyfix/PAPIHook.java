/**
 * 
 */
package com.perceivedev.uskyfix;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import us.talabrek.ultimateskyblock.uSkyBlock;
import us.talabrek.ultimateskyblock.api.IslandInfo;

/**
 * @author Rayzr
 *
 */
public class PAPIHook {

    private static Method IS_LOCKED;

    public static void register(Plugin plugin) {
        new EZPlaceholderHook(plugin, "uskyfix") {

            @Override
            public String onPlaceholderRequest(Player p, String identifier) {
                if (p == null || !p.isOnline()) {
                    return "";
                }
                if (identifier.equals("island_level")) {
                    return String.format("%.2f", uSkyBlock.getAPI().getIslandLevel(p));
                } else if (identifier.equals("island_global")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p.getLocation());
                    return info == null ? "None" : String.format("%.2f", info.getLevel());
                } else if (identifier.equals("island_leader")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p.getLocation());
                    return info == null ? "None" : info.getLeader();
                } else if (identifier.equals("island_biome")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p);
                    return info == null ? "None" : info.getBiome();
                } else if (identifier.equals("island_members")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p);
                    return (info == null || info.getMembers().size() < 1) ? "0" : "" + info.getMembers().size();
                } else if (identifier.equals("island_togglewarp")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p);
                    return info == null ? "None" : (info.getWarpLocation() == null ? "Inactive" : "Active");
                } else if (identifier.equals("island_lock")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p);
                    return info == null ? "None" : isLocked(info) ? "Locked" : "Unlocked";
                }
                return null;
            }

        }.hook();
    }

    public static boolean isLocked(IslandInfo info) {
        try {
            if (IS_LOCKED == null) {
                IS_LOCKED = info.getClass().getMethod("isLocked");
            }
            return Boolean.valueOf(IS_LOCKED.invoke(info).toString()).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
