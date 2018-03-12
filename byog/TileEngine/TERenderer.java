package byog.TileEngine;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.Serializable;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the player or something similar.
 */
public class TERenderer implements Serializable {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;

    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     *
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
     * width and height of the world in number of tiles. If the TETile[][] array that you
     * pass to renderFrame is smaller than this, then extra blank space will be left
     * on the right and top edges of the frame. For example, if you select w = 60 and
     * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
     * you then subsequently call renderFrame with a TETile[50][25] array, it will
     * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
     * you want to leave extra space on the left or bottom instead, use the other
     * initializatiom method.
     *
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.
     * <p>
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.
     * <p>
     * positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     * <p>
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     * ...    ......  |  ......  |  ......  | .... | ......
     * startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     * startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     * startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     * <p>
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     *
     * @param world the 2D TETile[][] array to render
     */
    public void renderFrame(TETile[][] world, int bombnumber, int health, double mouseX, double mouseY) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }

        // below is for displaying the HUD
        Font fontHeadUp = new Font("Monaco", Font.ITALIC, 10);
        Font hud = new Font("Monaco", Font.ITALIC,15);
        StdDraw.setFont(fontHeadUp);
        StdDraw.setPenColor(Color.WHITE);

        //show bomb number
        StdDraw.text(55, 46, "You have " + String.valueOf(bombnumber) + " bombs");
        for (int i=0; i<bombnumber; i++){
            StdDraw.setFont(hud);
            StdDraw.setPenColor(Color.yellow);
            StdDraw.text(54+i, 44, "▢");
        }
        StdDraw.setFont(fontHeadUp);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(55, 42, "Press e to plant a bomb ");
        StdDraw.text(55, 40, "You earn one bomb after");
        StdDraw.text(55, 39, "collecting ten dots");

        //show heart number
        StdDraw.text(55, 33, "You have " + String.valueOf(health) + " lives");
        for (int i=0; i<health; i++){
            StdDraw.setFont(hud);
            StdDraw.setPenColor(Color.red);
            StdDraw.text(54+i, 31, "♠");
        }

        StdDraw.setFont(fontHeadUp);
        StdDraw.setPenColor(Color.WHITE);
        if(mouseX ==0 && mouseY == 0){
            StdDraw.text(55, 20, "INFORMATION");
            StdDraw.text(55, 22, "click on the tile");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.SAND){
            StdDraw.text(55, 20, "This is the sea");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.FLOOR){
            StdDraw.text(55, 20, "This is a floor");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.WALL){
            StdDraw.text(55, 20, "This is a wall");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.SKULL){
            StdDraw.text(55, 20, "This is a skull monster!!");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.PU){
            StdDraw.text(55, 20, "Heads upward");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.PD){
            StdDraw.text(55, 20, "Heads downward");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.PL){
            StdDraw.text(55, 20, "Heads leftward");
        }
        else if(world[(int)Math.round(mouseX)][(int)Math.round(mouseY)] == Tileset.PR){
            StdDraw.text(55, 20, "Heads rightward");
        }
        StdDraw.show();
        StdDraw.pause(200);
    }
}
