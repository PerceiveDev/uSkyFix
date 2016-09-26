/**
 * 
 */
package com.perceivedev.uskyfix;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import us.talabrek.ultimateskyblock.uSkyBlock;
import us.talabrek.ultimateskyblock.api.IslandInfo;

/**
 * @author Rayzr
 *
 */
public class uSkyFix extends JavaPlugin {

    private Logger            logger;
    private EZPlaceholderHook hook;

    @Override
    public void onEnable() {
        logger = getLogger();

        if (Bukkit.getPluginManager().getPlugin("uSkyBlock") == null || Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.severe("This pugin requres both PlaceholerAPI and uSkyBlock!");
            logger.severe("Please download them before attempting to use this.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        hook = new EZPlaceholderHook(this, "uskyfix") {

            @Override
            public String onPlaceholderRequest(Player p, String identifier) {
                if (p == null) {
                    return "";
                }
                if (identifier.equals("island_level")) {
                    return String.format("%.2f", uSkyBlock.getAPI().getIslandLevel(p));
                } else if (identifier.equals("island_leader")) {
                    IslandInfo info = uSkyBlock.getAPI().getIslandInfo(p.getLocation());
                    return info == null ? "None" : info.getLeader();
                }
                return null;
            }

        };
        hook.hook();

        logger.info(versionText() + " enabled");
    }

    @Override
    public void onDisable() {
        logger.info(versionText() + " disabled");
    }

    public String versionText() {
        return getName() + " v" + getDescription().getVersion();
    }

}
