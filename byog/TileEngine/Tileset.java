package byog.TileEngine;

import java.awt.Color;
import java.io.Serializable;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 * <p>
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 * <p>
 * Ex:
 * world[x][y] = Tileset.FLOOR;
 * <p>
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset implements Serializable {
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('*', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', new Color(0,51,102), new Color(0,51,102), "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile SKULL = new TETile(' ', Color.green,
            Color.black, "skull", "./byog/image/skull-old.png");
    public static final TETile PU = new TETile(' ', Color.green,
            Color.black, "player", "./byog/image/PacmanUp.png");
    public static final TETile PD = new TETile(' ', Color.green,
            Color.black, "player", "./byog/image/PacmanDown.png");
    public static final TETile PL = new TETile(' ', Color.green,
            Color.black, "player", "./byog/image/PacmanLeft.png");
    public static final TETile PR = new TETile(' ', Color.green,
            Color.black, "player", "./byog/image/PacmanRight.png");
    public static final TETile BOMB = new TETile(' ', Color.green,
            Color.black, "bomb", "./byog/image/bomb.png");
    public static final TETile FIRE = new TETile(' ', Color.green,
            Color.black, "fire", "./byog/image/fire.png");
    public static final TETile BRICK = new TETile(' ', Color.green,
            Color.black, "fire", "./byog/image/brick.png");
    static TETile PLAYER = new TETile('@', Color.white, Color.black, "player");


}


