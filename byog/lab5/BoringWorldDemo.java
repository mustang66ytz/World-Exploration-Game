package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static final Random RANDOM = new Random();

    public static void addHexagon(TETile[][] tiles, int size, int px, int py, Random RANDOM) {
        int position_x = px;
        int position_y = py;
        for (int y = position_y; y < position_y + 2; y += 1) {
            for (int x = position_x; x < position_x + size + 2 * (size - 1); x += 1) {
                tiles[x][y] = Tileset.FLOWER;
            }
        }
        for (int y = position_y + 2; y < position_y + 1 + size; y += 1) {
            for (int x = position_x + (y - position_y - 1); x < position_x + size + 2 * (size - 1) - (y - position_y - 1); x += 1) {
                tiles[x][y] = Tileset.FLOWER;
            }
        }
        for (int y = position_y - size + 1; y < position_y; y += 1) {
            for (int x = position_x - (y - position_y); x < position_x + size + 2 * (size - 1) + (y - position_y); x += 1) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 30; x < 60; x += 1) {
            for (int y = 5; y < 30; y += 1) {
                world[x][y] = Tileset.WATER;
            }
        }

        // add some hexagons
        //addHexagon(world, 2);
        //addHexagon(world, 4, 10, 10, RANDOM);
        //addHexagon(world, 4, 10-2*(4-1), 10+4, RANDOM);
        // draws the world to the screen
        int i = 1;
        ter.renderFrame(world, i, 0,0,0);
    }


}
