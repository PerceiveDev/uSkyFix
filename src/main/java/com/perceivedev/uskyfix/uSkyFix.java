/**
 * 
 */
package com.perceivedev.uskyfix;

import static com.perceivedev.uskyfix.mirror.Mirror.$;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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

    private Logger logger;
    private ConfigManager manager;

    @Override
    public void onEnable() {
        logger = getLogger();
        manager = new ConfigManager(this);
        manager.load();

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

        new BukkitRunnable() {
            public void run() {
                wrapCommands();
            }
        }.runTaskLater(this, uSkyBlock.getAPI().getConfig().getLong("init.initDelay", 50L) + 2L);

        logger.info(versionText() + " enabled");
    }

    @Override
    public void onDisable() {
        manager.save();
        logger.info(versionText() + " disabled");
    }

    public void reload() {
        manager.save();
        manager.load();
        wrapCommands();
    }

    public void wrapCommands() {

        final PluginCommand command = Bukkit.getPluginCommand("uSkyBlock:island");
        command.setExecutor(new CommandExecutor() {
            CommandExecutor original = command.getExecutor();

            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (!(sender instanceof Player)) {
                    return original.onCommand(sender, command, label, args);
                }

                StringBuilder raw = new StringBuilder("/").append(label);
                for (String str : args) {
                    raw.append(" ").append(str);
                }

                PlayerCommandPreprocessEvent evt = new PlayerCommandPreprocessEvent((Player) sender, raw.toString());
                Bukkit.getPluginManager().callEvent(evt);

                if (evt.isCancelled()) {
                    return true;
                }

                return original.onCommand(sender, command, label, args);
            }

        });

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
