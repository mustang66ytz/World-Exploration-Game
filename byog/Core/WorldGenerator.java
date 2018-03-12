package byog.Core;
import edu.princeton.cs.introcs.StdDraw;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.awt.Font;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


class WorldGenerator implements Serializable {

    static final int WIDTH = 50;
    static final int HEIGHT = 50;
    RandomGen random;
    int loadOrnew;
    TERenderer ter;
    TETile[][] worldTile;
    int[][] worldMap = new int[WIDTH][HEIGHT];
    int[][] worldMapCharacter = new int[WIDTH][HEIGHT];
    private long seedInuse;
    private int initX;
    private int initY;

    // Generator for new game
    WorldGenerator(long number, int playstyle) {
        loadOrnew = 1;
        ter = new TERenderer();
        ter.initialize(WIDTH + 10, HEIGHT, 0,0);
        worldTile = new TETile[WIDTH][HEIGHT];
        seedInuse = number;
        random = new RandomGen(seedInuse);
        initialMap(worldMap);
        AbstractNode initialRoom;
        do {
            initialXY(random);
            initialRoom = new RoomGenerator(initX, initY, random);
        } while (!initialRoom.checkboundary(worldMap));
        initialRoom.fillup(worldMap);
        generation(initialRoom, random);
        Gameflow gameflow = new Gameflow(random, worldMap, worldMapCharacter,
                worldTile, ter, playstyle, loadOrnew);
        ending(gameflow.winorlose);
    }

    // Generator for loaded game
    WorldGenerator(int playstyle) {
        loadOrnew = 0;
        ter = new TERenderer();
        ter.initialize(WIDTH + 10, HEIGHT, 0,0);
        // load serialization
        loadrandmap();
        Gameflow gameflow = new Gameflow(random, worldMap, worldMapCharacter,
                worldTile, ter, playstyle, loadOrnew);
        ending(gameflow.winorlose);
    }

    private void initialXY(RandomGen rand) {
        initX = rand.randomInitx();
        initY = rand.randomInity();
    }

    private void initialMap(int[][] worldmap) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                worldmap[i][j] = 3;
            }
        }
    }

    private void generation(AbstractNode initialRoom, RandomGen rand) {
        mapGrowing(initialRoom, rand);
    }

    private void mapGrowing(AbstractNode preNode, RandomGen rand) {

        int branchNumber = rand.branchNumber() + 1;
        while (branchNumber != 0) {
            AbstractNode nextNode;
            if (rand.featureSelector() == 0 && (preNode.width() == 3
                    || preNode.height() == 3)) {
                nextNode = new RoomGenerator(preNode, rand);
                branchNumber--;
            } else if (rand.featureSelector() == 1) {
                nextNode = new HallWayGenerator(preNode, rand);
                branchNumber--;
            } else {
                continue;
            }
            if (nextNode.checkboundary(worldMap) && nextNode.checkcollision(worldMap, preNode)) {
                nextNode.fillup(worldMap);
                mapGrowing(nextNode, rand);
            }
        }
    }

    TETile[][] returnTile() {
        return worldTile;
    }

    void loadrandmap() {
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
            random = savedGame.random;
            worldMap = savedGame.worldMap;
            worldMapCharacter = savedGame.worldMapCharacter;
            worldTile = savedGame.worldTile;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + " is caught");
        }
    }


    void ending(int endingway) {
        // 0 for losing, 1 for quiting, 2 for winning by eating all dots, 3 for wining by killing all the monsters
        switch (endingway) {
            case 0 : showending("Hey Loser!");break;
            case 1 : showending("Why Quiting?  Game Saved...");break;
            case 2 : showending("Winning! All Dots Eaten");break;
            case 3 : showending("Winning! All Monsters Killed");break;
            default : break;
        }

    }
    void showending(String msg) {
        Font font = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(WorldGenerator.WIDTH/2+5,25,msg);
        StdDraw.show();
        StdDraw.pause(3000);
        StdDraw.clear(Color.black);
        StdDraw.show();
        System.exit(0);
    }
}
