package byog.Core;

import java.io.Serializable;

public class RoomGenerator extends AbstractNode implements Serializable {

    RoomGenerator(int initialPosX, int initialPosY, RandomGen rand) {
        startPosX = initialPosX;
        startPosY = initialPosY;
        height =  randomHeight(rand);
        width = randomWidth(rand);
        endPosX = startPosX + width - 1;
        endPosY = startPosY + height - 1;
        orientation = 5; //break orientation initialization
    }

    RoomGenerator(AbstractNode preNode, RandomGen rand) {
        height =  randomHeight(rand);
        width = randomWidth(rand);
        orientation = rand.orientationSelector();
        orientation(orientation, rand, preNode);
    }

    @Override
    public int randomHeight(RandomGen rand) {
        return rand.roomHeight();
    }
    @Override
    public int randomWidth(RandomGen rand) {
        return rand.roomWidth();
    }

}
