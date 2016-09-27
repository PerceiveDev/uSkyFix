/**
 * 
 */
package com.perceivedev.uskyfix;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Rayzr
 *
 */
public class SignListener implements Listener {

    private uSkyFix       plugin;
    private ConfigManager cm;

    /**
     * @param uSkyFix
     */
    public SignListener(uSkyFix plugin) {
        this.plugin = plugin;
        cm = plugin.getConfigManager();
    }

    @EventHandler
    public void onSignPlace(SignChangeEvent e) {
        if (!Signs.isLeaderboardLine(e.getLine(0))) {
            return;
        }

        if (!e.getPlayer().hasPermission(cm.permissionSignPlace)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(cm.messageNoPermission);
            return;
        }

        Block block = e.getBlock();
        if (!cm.addSign(block.getLocation())) {
            return;
        }

        e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&9" + e.getLine(0)));

        Block head = e.getBlock().getRelative(BlockFace.UP);
        if (head != null && head.getType() == Material.AIR) {
            head.setType(Material.SKULL);
            head.setData(direction(block));
            Skull skull = (Skull) head.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.update();
        }

        new BukkitRunnable() {
            public void run() {
                cm.update();
            };
        }.runTaskLater(plugin, 1);

        e.getPlayer().sendMessage(cm.messageSignPlaced);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.SKULL) {
            Block below = e.getBlock().getRelative(BlockFace.DOWN);
            if (below != null && Signs.isLeaderboardSign(below)) {
                e.setCancelled(true);
            }
        }
        if (!Signs.isLeaderboardSign(e.getBlock())) {
            return;
        }

        if (!e.getPlayer().hasPermission(cm.permissionSignBreak)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(cm.messageNoPermission);
            return;
        }

        Block up = e.getBlock().getRelative(BlockFace.UP);
        if (up != null && up.getType() == Material.SKULL) {
            up.setType(Material.AIR);
        }

        if (!plugin.getConfigManager().removeSign(e.getBlock().getLocation())) {
            e.getPlayer().sendMessage(cm.messageError);
        }

        e.getPlayer().sendMessage(cm.messageSignBroken);

    }

    private byte direction(Block sign) {
        if (sign.getType() == Material.SIGN_POST) {
            return (byte) 0;
        } else {
            return sign.getData();
        }
    }

}
