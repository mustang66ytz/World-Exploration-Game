package byog.Core;
import java.io.Serializable;
public abstract class AbstractNode implements NodeSpace, Serializable {
    protected int height;
    protected int width;
    int startPosX;
    int startPosY;
    int endPosX;
    int endPosY;
    int orientation = 5;

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    public int startPosX() {
        return startPosX;
    }

    @Override
    public int startPosY() {
        return startPosY;
    }

    @Override
    public int endPosX() {
        return endPosX;
    }

    @Override
    public int endPosY() {
        return endPosY;
    }

    @Override
    public int orientation() {
        return orientation;
    }

    @Override
    public void orientation(int orient, RandomGen rand, AbstractNode preNode) {
        switch (orient) {
            case 0:
                startPosX = preNode.startPosX();
                if (preNode.height() == 3) {
                    startPosY = preNode.startPosY();
                } else {
                    startPosY = rand.random(preNode.height() - 2) + preNode.startPosY();
                }
                startPosX = startPosX - width() + 1;
                endPosX = startPosX + width() - 1;
                endPosY = startPosY + height() - 1;
                break;
            case 3:
                startPosY = preNode.startPosY();
                if (preNode.width() == 3) {
                    startPosX = preNode.startPosX();
                } else {
                    startPosX = rand.random(preNode.width() - 2) + preNode.startPosX();
                }
                startPosY = startPosY - height() + 1;
                endPosX = startPosX + width() - 1;
                endPosY = startPosY + height() - 1;
                break;
            case 1:
                if (preNode.width() == 3) {
                    startPosX = preNode.startPosX();
                } else {
                    startPosX = rand.random(preNode.width() - 2) + preNode.startPosX();
                }
                startPosY = preNode.startPosY() + preNode.height() - 1;
                endPosX = startPosX + width() - 1;
                endPosY = startPosY + height() - 1;
                break;
            case 2:
                if (preNode.height() == 3) {
                    startPosY = preNode.startPosY();
                } else {
                    startPosY = rand.random(preNode.height() - 2) + preNode.startPosY();
                }
                startPosX = preNode.startPosX() + preNode.width() - 1;
                endPosX = startPosX + width() - 1;
                endPosY = startPosY + height() - 1;
                break;
            default:
                break;
        }
    }
}
