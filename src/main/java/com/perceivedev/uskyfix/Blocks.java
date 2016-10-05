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
            return dataFromSignpost(sign.getData());
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
            return BlockFace.NORTH;
        }
    }

    private static byte dataFromSignpost(int data) {
        switch (data) {
        case 0:
            return (byte) 3;
        case 4:
            return (byte) 4;
        case 8:
            return (byte) 2;
        case 12:
            return (byte) 5;
        default:
            return (byte) 5;
        }
    }

}
