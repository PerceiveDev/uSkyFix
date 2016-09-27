/**
 * 
 */
package com.perceivedev.uskyfix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Rayzr
 *
 */
public class CommandHandler implements CommandExecutor {

    private uSkyFix       plugin;
    private ConfigManager cm;

    public CommandHandler(uSkyFix plugin) {
        this.plugin = plugin;
        cm = this.plugin.getConfigManager();
    }

    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {

        if (!p.hasPermission(cm.permissionCommand)) {
            p.sendMessage(cm.messageNoPermission);
            return true;
        }

        if (args.length < 1) {
            p.sendMessage(cm.messageVersion.replace("{version}", plugin.versionText()));
            return true;
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) {
            cm.save();
            cm.load();
            p.sendMessage(cm.messageConfigReloaded);
        } else if (sub.equals("refresh")) {
            cm.update();
            p.sendMessage(cm.messageRefreshed);
        } else {
            p.sendMessage(cm.messageCommandUsage);
        }

        return true;
    }

}
