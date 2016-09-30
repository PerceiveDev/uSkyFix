/**
 * 
 */
package com.perceivedev.uskyfix;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
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

        int place = Signs.getPlaceOld(e.getLine(0));
        System.out.println("Place: " + place);
        e.setLine(1, Signs.setLines((Sign) e.getBlock().getState(), place, "&4None", "")[1]);

        e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&1[Skyblock]"));

        Block head = e.getBlock().getRelative(BlockFace.UP);
        if (head != null) {
            head = head.getRelative(face(direction(block)));
        }

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
            Block below = e.getBlock().getRelative(BlockFace.DOWN).getRelative(face(e.getBlock().getData()).getOppositeFace());
            if (below != null && Signs.isLeaderboardSign(below, cm)) {
                e.setCancelled(true);
            }
        }
        if (!Signs.isLeaderboardSign(e.getBlock(), cm)) {
            return;
        }

        if (!e.getPlayer().hasPermission(cm.permissionSignBreak)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(cm.messageNoPermission);
            return;
        }

        Block up = e.getBlock().getRelative(BlockFace.UP);
        if (up != null) {
            up = up.getRelative(face(direction(e.getBlock())));
        }
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
            return (byte) (sign.getData() % 4 - 1);
        } else {
            return sign.getData();
        }
    }

    private BlockFace face(byte direction) {
        switch (direction) {
        case 2:
            return BlockFace.SOUTH;
        case 3:
            return BlockFace.NORTH;
        case 4:
            return BlockFace.EAST;
        case 5:
            return BlockFace.WEST;
        default:
            return BlockFace.SELF;
        }
    }

}
