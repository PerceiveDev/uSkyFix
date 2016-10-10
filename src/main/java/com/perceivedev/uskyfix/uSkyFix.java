/**
 * 
 */
package com.perceivedev.uskyfix;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Rayzr
 *
 */
public class uSkyFix extends JavaPlugin {

    private Logger        logger;
    private ConfigManager manager;

    @Override
    public void onEnable() {
        logger = getLogger();
        manager = new ConfigManager(this);
        manager.load();

        if (!Bukkit.getPluginManager().isPluginEnabled("uSkyBlock")) {
            logger.severe("This pugin requires uSkyBlock! Also this plugin works better with PlaceholderAPI / MVdWPlaceholderAPI installed.");
            logger.severe("Please download them before attempting to use this.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            PAPIHook.register(this);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            MVdWHook.register(this);
        }

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
