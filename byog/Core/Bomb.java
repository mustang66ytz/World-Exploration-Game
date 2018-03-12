package byog.Core;

import java.io.Serializable;
import java.util.Queue;

class Bomb extends AbstractObject implements Serializable {
    int fireLength = 5;
    int duration = 10;

    Bomb(int posx, int posy) {
        this.posX = posx;
        this.posY = posy;
    }

    void countdown() {
        duration--;
        System.out.println("Beeeeeeeee");
    }

    void show(int[][] worldmapCharacter) {
        worldmapCharacter[posX][posY] = 6;
    }

    void fireinthehole(int[][] worldmapCharacter, int[][] worldmap, Queue<Bomb> bombQueue, Monster[] monsters) {

        if (duration == 0) {
            for (int i = posX; i >= posX - fireLength; i--) {
                int j = posY;
                if (worldmap[i][j] == 1) {
                    break;
                }
                monsterkilled(monsters, i, j);
                worldmapCharacter[i][j] = 7;
            }
            for (int i = posX; i <= posX + fireLength; i++) {
                int j = posY;
                if (worldmap[i][j] == 1) {
                    break;
                }
                monsterkilled(monsters, i, j);
                worldmapCharacter[i][j] = 7;
            }
            for (int j = posY; j >= posY - fireLength; j--) {
                int i = posX;
                if (worldmap[i][j] == 1) {
                    break;
                }
                monsterkilled(monsters, i, j);
                worldmapCharacter[i][j] = 7;
            }
            for (int j = posY; j <= posY + fireLength; j++) {
                int i = posX;
                if (worldmap[i][j] == 1) {
                    break;
                }
                monsterkilled(monsters, i, j);
                worldmapCharacter[i][j] = 7;
            }
            System.out.println("boommmmmmmmm");
            bombQueue.remove();
        }
    }
    void monsterkilled(Monster[] monsters, int i, int j) {
        for (int k = 0; k < monsters.length; k++ ) {
            if (monsters[k] == null) {
                continue;
            }
            if (monsters[k].posX == i && monsters[k].posY == j) {
                monsters[k] = null;
            }
        }
    }
}



