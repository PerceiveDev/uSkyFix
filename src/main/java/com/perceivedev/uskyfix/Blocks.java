/**
 * 
 */
package com.perceivedev.uskyfix;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * @author Rayzr
 *
 */
public class Blocks {

    public static byte direction(Block sign) {
        if (sign.getType() == Material.SIGN_POST) {
            return (byte) (sign.getData() % 4 - 1);
        } else {
            return sign.getData();
        }
    }

    public static BlockFace face(byte direction) {
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
