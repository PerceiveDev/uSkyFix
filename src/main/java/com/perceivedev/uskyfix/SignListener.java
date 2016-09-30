/**
 * 
 */
package com.perceivedev.uskyfix;

import static com.perceivedev.uskyfix.Blocks.direction;
import static com.perceivedev.uskyfix.Blocks.face;
import static com.perceivedev.uskyfix.Signs.getPlaceOld;
import static com.perceivedev.uskyfix.Signs.isLeaderboardLine;
import static com.perceivedev.uskyfix.Signs.isLeaderboardSign;
import static com.perceivedev.uskyfix.Signs.setLines;

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
        if (!isLeaderboardLine(e.getLine(0))) {
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

        int place = getPlaceOld(e.getLine(0));

        e.setLine(1, setLines((Sign) e.getBlock().getState(), place, "&4None", "")[1]);

        e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&1[Skyblock]"));

        Block head = e.getBlock().getRelative(BlockFace.UP);
        if (head != null) {
            head = head.getRelative(face(direction(block)));
        }

        if (head != null && head.getType() == Material.AIR) {
            head.setType(Material.SKULL);
            head.setData((byte) 1);
            Skull skull = (Skull) head.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.setRotation(face(direction(block)).getOppositeFace());
            skull.update(true);
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
            if (below != null && isLeaderboardSign(below, cm)) {
                e.setCancelled(true);
            }
        }
        if (!isLeaderboardSign(e.getBlock(), cm)) {
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

}
