package byog.Core;

import java.io.Serializable;

class Monster extends AbstractObject implements Serializable {

    Monster(RandomGen rand, int[][] worldmap, int[][] worldmapCharacter) {
        do {
            posX = rand.random(WorldGenerator.WIDTH);
            posY = rand.random(WorldGenerator.HEIGHT);
        } while (worldmap[posX][posY] != 0);
        worldmapCharacter[posX][posY] = 3;
        gendirection(rand);
    }

    void gendirection(RandomGen rand) {
        direction = rand.random(4);
    }

    void move(int[][] worldmap, int[][] worldmapCharacter, RandomGen rand) {
        worldmapCharacter[posX][posY] = 3;
        //move forward if not hitting the wall
        if ((worldmap[posX - 1][posY] == 0
                || worldmap[posX - 1][posY] == 2) && direction == 0) {
            posX -= 1;
            gendirection(rand);
            return;
        } else if ((worldmap[posX][posY + 1] == 0
                || worldmap[posX][posY + 1] == 2) && direction == 1) {
            posY += 1;
            gendirection(rand);
            return;
        } else if ((worldmap[posX + 1][posY] == 0
                || worldmap[posX + 1][posY] == 2) && direction == 2) {
            posX += 1;
            gendirection(rand);
            return;
        } else if ((worldmap[posX][posY - 1] == 0
                || worldmap[posX][posY - 1] == 2) && direction == 3) {
            posY -= 1;
            gendirection(rand);
            return;
        } else if ((worldmap[posX - 1][posY] == 1 && direction == 0)
                || (worldmap[posX][posY + 1] == 1 && direction == 1)
                || (worldmap[posX + 1][posY] == 1 && direction == 2)
                || (worldmap[posX][posY - 1] == 1 && direction == 3)) {
            gendirection(rand);
            move(worldmap, worldmapCharacter, rand);
        }
    }

    boolean kill(Player player, int[][] worldmapCharacter) {
        if (posX == player.posX && posY == player.posY) {
            System.out.println("Got hit once");
            player.health -= 1;
            if(player.health == 0){
                System.out.println("killed");
                return true;
            }
            return false;
        }
        return false;

    }

}
