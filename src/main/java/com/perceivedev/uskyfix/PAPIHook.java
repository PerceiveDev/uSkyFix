/**
 * 
 */
package com.perceivedev.uskyfix;

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

    public static void register(Plugin plugin) {
        new EZPlaceholderHook(plugin, "uskyfix") {

            @Override
            public String onPlaceholderRequest(Player p, String identifier) {
                if (p == null) {
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
                }
                return null;
            }

        }.hook();
    }

}
