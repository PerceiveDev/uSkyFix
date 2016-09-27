/**
 * 
 */
package com.perceivedev.uskyfix;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
    private ConfigManager     manager;

    @Override
    public void onEnable() {
        logger = getLogger();
        manager = new ConfigManager(this);
        manager.load();

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

        new BukkitRunnable() {

            public void run() {

                manager.update();

            }

        }.runTaskTimer(this, 0L, 3 * 60 * 20);

        getServer().getPluginManager().registerEvents(new SignListener(this), this);
        getCommand("uskyfix").setExecutor(new CommandHandler(this));

        logger.info(versionText() + " enabled");
    }

    @Override
    public void onDisable() {
        manager.save();
        logger.info(versionText() + " disabled");
    }

    public String versionText() {
        return getName() + " v" + getDescription().getVersion();
    }

    /**
     * @return
     */
    public ConfigManager getConfigManager() {
        return manager;
    }

}
