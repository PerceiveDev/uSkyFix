/**
 * 
 */
package com.perceivedev.uskyfix;

import static com.perceivedev.uskyfix.mirror.Mirror.$;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.perceivedev.uskyfix.mirror.ReflectedClass;

import us.talabrek.ultimateskyblock.uSkyBlock;
import us.talabrek.ultimateskyblock.menu.SkyBlockMenu;

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

        try {

            ReflectedClass<SkyBlockMenu> menu = $(uSkyBlock.getInstance().getMenu());
            menu.setField("sign", new ItemStack(Material.SIGN));
            menu.setField("biome", new ItemStack(Material.SAPLING, 1, (short) 3));
            menu.setField("lock", new ItemStack(Material.IRON_FENCE));
            menu.setField("warptoggle", new ItemStack(Material.EYE_OF_ENDER));

        } catch (Exception e) {
            logger.severe("Something went wrong while trying to load uSkyFix!");
            logger.severe("Please show the following error to Rayzr522:");
            e.printStackTrace();
            logger.severe("This plugin will now be disabled");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        registerPlaceholders();

        startSignRefresh();

        getServer().getPluginManager().registerEvents(new SignListener(this), this);
        getCommand("uskyfix").setExecutor(new CommandHandler(this));

        logger.info(versionText() + " enabled");
    }

    private void startSignRefresh() {
        new BukkitRunnable() {
            public void run() {
                manager.update();
            }
        }.runTaskTimer(this, 0L, 3 * 60 * 20);
    }

    private void registerPlaceholders() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            PAPIHook.register(this);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            MVdWHook.register(this);
        }
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
