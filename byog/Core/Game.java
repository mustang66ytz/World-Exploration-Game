package byog.Core;


import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class Game implements Serializable {

    static String playCommand;
    long seed;
    long savedSeed;
    int playStyle;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        /* main menu entrance */
        playStyle = 0;
        // show the black canvas
        StdDraw.setCanvasSize((WorldGenerator.WIDTH+10) * 16, WorldGenerator.HEIGHT* 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        StdDraw.pause(1000);

        displayMainMenu();

        // display the main menu
        String seedString = "";
        StdDraw.clear(Color.black);

        if (StdDraw.hasNextKeyTyped()) {
            char typedKey = StdDraw.nextKeyTyped();
            if (typedKey== 'n') {
                //StdDraw.pause(1000);
                do {
                    StdDraw.text(WorldGenerator.WIDTH/2+5, 25, "Please enter a sequence of numbers,");
                    StdDraw.pause(1000);
                    StdDraw.enableDoubleBuffering();
                    while (true) {
                        StdDraw.clear(Color.black);
                        StdDraw.text(WorldGenerator.WIDTH/2+5, 30, "Magic Key:");
                        StdDraw.text(WorldGenerator.WIDTH/2+5, 25, "press 'c' to confirm");
                        //char current = StdDraw.nextKeyTyped();
                        //System.out.println(current);
                        char current;
                        if (StdDraw.hasNextKeyTyped()) {
                            current = StdDraw.nextKeyTyped();
                            if (current == 'c') {
                                break;
                            }
                            seedString += current;
                        }
                        StdDraw.text(WorldGenerator.WIDTH/2+5, 20, seedString);
                        StdDraw.show();
                        //StdDraw.pause(1000);
                    }
                    //StdDraw.pause(4000);
                } while (seedString.equals(""));
                StdDraw.clear(Color.black);
                StdDraw.text(WorldGenerator.WIDTH/2+5, 25, "Generating a new world!");
                StdDraw.show();
                StdDraw.pause(2000);
                seed = Long.parseLong(seedString);
                transitScreen();
                WorldGenerator newWorld = new WorldGenerator(seed, playStyle);

            } else if (typedKey == 'l') {
                StdDraw.text(WorldGenerator.WIDTH/2+5, 25, "Loading your world!");
                StdDraw.show();
                transitScreen();
                WorldGenerator savedWorld = new WorldGenerator(playStyle);
            } else if (typedKey == 'q') {
                StdDraw.text(WorldGenerator.WIDTH/2+5, 25, "Quit and Saving your World!");
                StdDraw.show();
                System.exit(0);
            }
        }
    }
        public void transitScreen() {
        Font font1 = new Font("Monaco", Font.BOLD, 60);
        Font fontHeadUp = new Font("Monaco", Font.ITALIC, 10);
        StdDraw.setFont(font1);
        int loadingTime = 2500;
        int i = 10;

        while (loadingTime > 0) {
            StdDraw.clear(Color.black);
            StdDraw.text( i,25, ">>>>");
            StdDraw.rectangle(WorldGenerator.WIDTH/2+5, 25, 24, 1);
            i += 10;
            StdDraw.show();
            StdDraw.pause(500);
            loadingTime -= 500;
        }
        StdDraw.enableDoubleBuffering();
    }

        public void displayMainMenu() {
        do {
            StdDraw.enableDoubleBuffering();
            StdDraw.clear(Color.black);
            Font font = new Font("TimesRoman", Font.BOLD, 35);
            StdDraw.setFont(font);
            StdDraw.setXscale(0, WorldGenerator.WIDTH+10);
            StdDraw.setYscale(0, WorldGenerator.HEIGHT);
            StdDraw.setPenColor(Color.orange);
            StdDraw.text(WorldGenerator.WIDTH/2+5, 35, "FRY THE MONSTER");
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(WorldGenerator.WIDTH/2+5, 30, "New Game (n)");
            StdDraw.text(WorldGenerator.WIDTH/2+5, 25, "Load Game (l)");
            StdDraw.text(WorldGenerator.WIDTH/2+5, 20, "Quite and Exit (q)");
            StdDraw.show();
            StdDraw.pause(200);
        } while (!StdDraw.hasNextKeyTyped());
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInpu tString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {

        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        String seedString = "";
        int seedend = 0;
        playStyle = 1;

        if (input.charAt(0) == 'l') {
            try {
                playCommand = input.substring(1, input.length());
                WorldGenerator savedWorld = new WorldGenerator(playStyle);
                return savedWorld.returnTile();
            } catch (RuntimeException e) {
                System.out.println("No savedGame found");
            }

        } else if (input.substring(0, 1).equals(":q")) {
            System.out.println("Game quited");
            System.exit(0);
        } else if (input.charAt(0) == 'n') {
            for (int i = 1; i < input.length(); i++) {
                if (Character.isDigit(input.charAt(i))) {
                    seedString += input.charAt(i);
                }
                if (input.charAt(i) == 's') {
                    savedSeed = Long.parseLong(seedString);
                    seedend = i;
                }
            }
            playCommand = input.substring(seedend + 1, input.length());
            seed = Long.parseLong(seedString);
            WorldGenerator newWorld = new WorldGenerator(seed, playStyle);
            return newWorld.returnTile();
        }
        System.out.println("Parsing failure");
        return null;
    }
}
