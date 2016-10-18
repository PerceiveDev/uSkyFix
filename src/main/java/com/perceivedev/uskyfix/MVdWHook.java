/**
 * 
 */
package com.perceivedev.uskyfix;

import java.lang.reflect.Method;

import org.bukkit.plugin.Plugin;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import us.talabrek.ultimateskyblock.uSkyBlock;
import us.talabrek.ultimateskyblock.api.IslandInfo;

/**
 * @author Rayzr
 *
 */
public class MVdWHook {

    private static Method IS_LOCKED;

    public static void register(Plugin plugin) {
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_level", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                return String.format("%.2f", uSkyBlock.getAPI().getIslandLevel(e.getPlayer()));
            }
        });
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_global", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                IslandInfo info = uSkyBlock.getAPI().getIslandInfo(e.getPlayer().getLocation());
                return info == null ? "None" : String.format("%.2f", info.getLevel());
            }
        });
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_leader", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                IslandInfo info = uSkyBlock.getAPI().getIslandInfo(e.getPlayer().getLocation());
                return info == null ? "None" : info.getLeader();
            }
        });
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_biome", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                IslandInfo info = uSkyBlock.getAPI().getIslandInfo(e.getPlayer());
                return info == null ? "None" : info.getBiome();
            }
        });
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_members", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                IslandInfo info = uSkyBlock.getAPI().getIslandInfo(e.getPlayer());
                return (info == null || info.getMembers().size() < 1) ? "0" : "" + info.getMembers().size();
            }
        });
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_togglewarp", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                IslandInfo info = uSkyBlock.getAPI().getIslandInfo(e.getPlayer());
                return info == null ? "None" : (info.getWarpLocation() == null ? "Inactive" : "Active");
            }
        });
        PlaceholderAPI.registerPlaceholder(plugin, "uskyfix_island_lock", new PlaceholderReplacer() {
            public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                IslandInfo info = uSkyBlock.getAPI().getIslandInfo(e.getPlayer());
                return info == null ? "None" : isLocked(info) ? "Locked" : "Unlocked";
            }
        });
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
