package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.Queue;

class DataStoraging implements Serializable {
    RandomGen random;
    TETile[][] worldTile;
    int[][] worldMap;
    int[][] worldMapCharacter;
    Player player1;
    Queue<Bomb> bombQueue;
    Monster[] monster;

    DataStoraging(RandomGen random, TETile[][] worldTile, int[][] worldMap,
                  int[][] worldMapCharacter, Player player1,
                  Queue<Bomb> bombQueue, Monster[] monster) {
        this.random = random;
        this.worldTile = worldTile;
        this.worldMap = worldMap;
        this.worldMapCharacter = worldMapCharacter;
        this.player1 = player1;
        this.bombQueue = bombQueue;
        this.monster = monster;
    }
}
