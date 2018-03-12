package byog.Core;

import java.io.Serializable;

public interface NodeSpace {
    int height();

    int width();

    int startPosX();

    int startPosY();

    int endPosX();

    int endPosY();

    int randomHeight(RandomGen rand);

    int randomWidth(RandomGen rand);

    int orientation();

    void orientation(int orient, RandomGen rand, AbstractNode preNode);

    default boolean checkboundary(int[][] worldMap) {
        if (startPosX() < 2 || endPosX() > WorldGenerator.WIDTH - 3 || startPosY() < 2
                || endPosY() > WorldGenerator.HEIGHT - 3) {
            return false;
        }
        return true;
    }

    default boolean checkcollision(int[][] worldMap, AbstractNode preNode) {
        if (orientation() == 0) {
            for (int i = startPosX(); i <= endPosX(); i++) {
                for (int j = startPosY(); j <= endPosY(); j++) {
                    if (i == preNode.startPosX() && j > preNode.startPosY()
                            && j < preNode.endPosY()) {
                        continue;
                    }
                    if (worldMap[i][j] != 3) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (orientation() == 1) {
            for (int i = startPosX(); i <= endPosX(); i++) {
                for (int j = startPosY(); j <= endPosY(); j++) {
                    if (j == preNode.endPosY() && i > preNode.startPosX()
                            && i < preNode.endPosX()) {
                        continue;
                    }
                    if (worldMap[i][j] != 3) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (orientation() == 2) {
            for (int i = startPosX(); i <= endPosX(); i++) {
                for (int j = startPosY(); j <= endPosY(); j++) {
                    if (i == preNode.endPosX() && j > preNode.startPosY()
                            && j < preNode.endPosY()) {
                        continue;
                    }
                    if (worldMap[i][j] != 3) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (orientation() == 3) {
            for (int i = startPosX(); i <= endPosX(); i++) {
                for (int j = startPosY(); j <= endPosY(); j++) {
                    if (j == preNode.startPosY() && i > preNode.startPosX()
                            && i < preNode.endPosX()) {
                        continue;
                    }
                    if (worldMap[i][j] != 3) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;

    }

    default void fillup(int[][] worldMap) {
        // 0 is floor, 1 is wall and 3 is outerspace
        for (int i = startPosX(); i <= endPosX(); i++) {
            for (int j = startPosY(); j <= endPosY(); j++) {
                worldMap[i][j] = 0;
            }
        }

        for (int i = startPosX(); i <= endPosX(); i++) {
            worldMap[i][startPosY()] = 1;
            worldMap[i][startPosY() + height() - 1] = 1;
        }

        for (int j = startPosY(); j <= endPosY(); j++) {
            worldMap[startPosX()][j] = 1;
            worldMap[startPosX() + width() - 1][j] = 1;
        }
        // open up the connection
        switch (orientation()) {
            case 0:
                worldMap[startPosX() + width() - 1][startPosY() + 1] = 0;
                break;
            case 1:
                worldMap[startPosX() + 1][startPosY()] = 0;
                break;
            case 2:
                worldMap[startPosX()][startPosY() + 1] = 0;
                break;
            case 3:
                worldMap[startPosX() + 1][startPosY() + height() - 1] = 0;
                break;
            default:
                break;

        }
    }
}
