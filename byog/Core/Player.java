package byog.Core;

import java.io.Serializable;
import java.util.Queue;

class Player extends AbstractObject implements Serializable {
    int dotEaten;
    int bombNUm;
    int health;
    private int predirect;

    Player(RandomGen rand, int[][] worldmap, int[][] worldmapCharacter) {
        do {
            posX = rand.random(WorldGenerator.WIDTH);
            posY = rand.random(WorldGenerator.HEIGHT);
            health = 3;
        } while (worldmap[posX][posY] != 0);
        worldmapCharacter[posX][posY] = 4;
        direction = rand.random(4);
    }

    int getDirection() {
        return direction;
    }

    int getPreDirection() {
        return predirect;
    }

    int bombs() {
        return bombNUm;
    }

    void move(int[][] worldmap, int[][] worldmapCharacter, char keyTyped, Queue<Bomb> bombqueue) {
        worldmap[posX][posY] = 2;
        worldmapCharacter[posX][posY] = 4;

        switch (keyTyped) {
            case 'w':
                direction = 1;
                predirect = direction;
                break;
            case 'a':
                direction = 0;
                predirect = direction;
                break;
            case 's':
                direction = 3;
                predirect = direction;
                break;
            case 'd':
                direction = 2;
                predirect = direction;
                break;
            case 'f':
                direction = 4;
                break; //no movement
            case 'e':
                placeBomb(bombqueue);
                break;

            default:
                break;
        }

        //move forward based on the direction and if not hitting the wall
        if ((worldmap[posX - 1][posY] == 2 || worldmap[posX - 1][posY] == 0) && direction == 0) {
            posX -= 1;
            eatDot(worldmap);
            System.out.println(bombNUm);
        } else if ((worldmap[posX][posY + 1] == 0
                || worldmap[posX][posY + 1] == 2) && direction == 1) {
            posY += 1;
            eatDot(worldmap);
            System.out.println(bombNUm);
        } else if ((worldmap[posX + 1][posY] == 0
                || worldmap[posX + 1][posY] == 2) && direction == 2) {
            posX += 1;
            eatDot(worldmap);
            System.out.println(bombNUm);
        } else if ((worldmap[posX][posY - 1] == 0
                || worldmap[posX][posY - 1] == 2) && direction == 3) {
            posY -= 1;
            eatDot(worldmap);
            System.out.println(bombNUm);
        }
    }

    private void eatDot(int[][] worldmap) {
        if (worldmap[posX][posY] == 0) {
            dotEaten++;
            bombNUm = dotEaten / 10;
        }
    }

    private void placeBomb(Queue<Bomb> bombqueue) {
        if (bombNUm >= 1) {
            bombqueue.add(new Bomb(posX, posY));
            dotEaten -= 10;
            bombNUm -= 1;
        }
    }
}

