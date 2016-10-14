/**
 * 
 */
package com.perceivedev.uskyfix;

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
    }

}
