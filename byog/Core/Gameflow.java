package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class Gameflow implements Serializable {

    private Player player1;
    private Queue<Bomb> bombQueue = new LinkedList<>();
    Monster[] monster;
    int dotleft;
    int winorlose;
    int killedMonster;

    Gameflow(RandomGen rand, int[][] worldmap, int[][] worldmapCharacter,
             TETile[][] worldtile, TERenderer
                     terR, int playstyle, int loadornew) {

        if (loadornew == 0) {
            //load player and bomb and monster
            loadcharacter();
        } else {
            player1 = new Player(rand, worldmap, worldmapCharacter);
            monster = new Monster[10];
            for (int i = 0; i < monster.length; i++) {
                monster[i] = new Monster(rand, worldmap, worldmapCharacter);
            }
        }

        double mouseX = 0;
        double mouseY = 0;
        while (true) {
            char key = 'f';
            if (playstyle == 0) {
                if (StdDraw.hasNextKeyTyped()) {
                    key = StdDraw.nextKeyTyped();
                }
                if (StdDraw.isMousePressed()){
                    mouseX = StdDraw.mouseX();
                    mouseY = StdDraw.mouseY();
                }
            } else if (playstyle == 1) {
                for (int i = 0; i < Game.playCommand.length(); i++) {
                    if (Character.isLetter(Game.playCommand.charAt(i))) {
                        key = Game.playCommand.charAt(i);
                    }
                    if (Game.playCommand.charAt(i) == ':'
                            && Game.playCommand.charAt(i + 1) == 'q') {
                        //save
                        save(rand, worldtile, worldmap, worldmapCharacter);
                        return;
                    }

                }
            }


            //check if all the monsters are killed
            for (Monster x : monster) {
                if (x == null) {
                    killedMonster++;
                    continue;
                }
            }
            if (killedMonster == monster.length) {
                drawCanvas(worldmap, worldmapCharacter, worldtile, terR, mouseX, mouseY);
                winorlose = 3;
                return;// win by killing all monsters
            }
            killedMonster = 0;

            for (Monster x : monster) {
                if (x == null) {
                    continue;
                }

                boolean finish = x.kill(player1, worldmapCharacter);
                if (finish) {
                    //StdDraw.pause(2000);
                    drawCanvas(worldmap, worldmapCharacter, worldtile, terR, mouseX, mouseY);
                    winorlose = 0;
                    return; // lose
                }
                System.out.println("The length is " + monster.length );
                x.move(worldmap, worldmapCharacter, rand);
            }
            if (key == 'q') {
                save(rand, worldtile, worldmap, worldmapCharacter);
                winorlose = 1;
                return; // quit and save
            }
            player1.move(worldmap, worldmapCharacter, key, bombQueue);

            //check bombs status and kill monster if possible
            if (!bombQueue.isEmpty()) {
                bombQueue.peek().fireinthehole(worldmapCharacter, worldmap, bombQueue, monster);
                for (Bomb x : bombQueue) {
                    x.countdown();
                    x.show(worldmapCharacter);
                }
            }

            drawCanvas(worldmap, worldmapCharacter, worldtile, terR, mouseX, mouseY);
            if (dotleft == 0) {
                drawCanvas(worldmap, worldmapCharacter, worldtile, terR, mouseX, mouseY);
                winorlose = 2;
                return; // win by eating all dots
            }
            //reinitializtion helper
            dotleft = 0;
            for (int i = 0; i < WorldGenerator.WIDTH; i++) {
                for (int j = 0; j < WorldGenerator.HEIGHT; j++) {
                    worldmapCharacter[i][j] = 0;
                }
            }
        }
    }


    void drawCanvas(int[][] worldmap, int[][] worldmapCharacter,
                    TETile[][] worldtile, TERenderer terR, double mouseX, double mouseY) {
        int lowsetwall = 0;
        int doornumber = 1;
        for (int j = 0; j < WorldGenerator.HEIGHT; j++) {
            for (int i = 0; i < WorldGenerator.WIDTH; i++) {
                if (worldmap[i][j] == 0) {
                    worldtile[i][j] = Tileset.FLOOR;
                    dotleft++;
                }
                if (worldmap[i][j] == 2) {
                    worldtile[i][j] = Tileset.NOTHING;
                }
                if (worldmap[i][j] == 3) {
                    worldtile[i][j] = Tileset.SAND;
                }
                if (worldmap[i][j] == 1 && lowsetwall == 0) {
                    worldtile[i][j] = Tileset.WALL;
                    if (doornumber != 0) {
                        lowsetwall = 1;
                    }
                    continue;
                }
                if (worldmap[i][j] == 1 && lowsetwall == 1 && doornumber != 0) {
                    worldtile[i][j] = Tileset.LOCKED_DOOR;
                    lowsetwall = 0;
                    doornumber--;
                    continue;
                }
                if (worldmapCharacter[i][j] == 7) {
                    worldtile[i][j] = Tileset.FIRE;
                    continue;
                }

                if (worldmapCharacter[i][j] == 6) {
                    worldtile[i][j] = Tileset.BOMB;
                    continue;
                }
                if (worldmapCharacter[i][j] == 4 && player1.getDirection() == 0) {
                    worldtile[i][j] = Tileset.PL;
                    continue;
                }
                if (worldmapCharacter[i][j] == 4 && player1.getDirection() == 1) {
                    worldtile[i][j] = Tileset.PU;
                    continue;
                }
                if (worldmapCharacter[i][j] == 4 && player1.getDirection() == 2) {
                    worldtile[i][j] = Tileset.PR;
                    continue;
                }
                if (worldmapCharacter[i][j] == 4 && player1.getDirection() == 3) {
                    worldtile[i][j] = Tileset.PD;
                    continue;
                }
                if (worldmapCharacter[i][j] == 4 && player1.getDirection() == 4) {
                    // the case when there is no keyboard input
                    if (player1.getPreDirection() == 0) {
                        worldtile[i][j] = Tileset.PL;
                    } else if (player1.getPreDirection() == 1) {
                        worldtile[i][j] = Tileset.PU;
                    } else if (player1.getPreDirection() == 2) {
                        worldtile[i][j] = Tileset.PR;
                    } else if (player1.getPreDirection() == 3) {
                        worldtile[i][j] = Tileset.PD;
                    }
                }
                if (worldmapCharacter[i][j] == 3) {
                    worldtile[i][j] = Tileset.SKULL;
                    continue;
                }
                continue;
            }
        }
        terR.renderFrame(worldtile, player1.bombs(), player1.health, mouseX, mouseY);
    }

    void save(RandomGen rand, TETile[][] worldtile,
              int[][] worldmap, int[][] worldmapCharacter) {
        DataStoraging savedGame = new DataStoraging(rand,
                worldtile, worldmap, worldmapCharacter, player1, bombQueue, monster);
        String filename = "savedGame.txt";
        try {
            // Saving of object in a file
            FileOutputStream file = null;
            ObjectOutputStream out = null;
            try {
                file = new FileOutputStream(filename);
            } catch (IOException ex) {
                System.out.println("FileOutputStream is Wrong");
            }
            try {
                out = new ObjectOutputStream(file);
            } catch (IOException ex) {
                System.out.println("ObjectOutputStream is Wrong");
            }
            // Method for serialization of object
            try {
                out.writeObject(savedGame);
            } catch (IOException ex) {
                System.out.println("writeObject is Wrong");
            }


            out.close();
            file.close();

            System.out.println("Object has been serialized");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    void loadcharacter() {
        DataStoraging savedGame = null;
        String filename = "savedGame.txt";
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            savedGame = (DataStoraging) in.readObject();

            in.close();
            file.close();
            System.out.println("Object has been deserialized");
            player1 = savedGame.player1;
            monster = savedGame.monster;
            bombQueue = savedGame.bombQueue;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + " is caught");
        }
    }
}
