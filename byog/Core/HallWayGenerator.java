package byog.Core;

import java.io.Serializable;

public class HallWayGenerator extends AbstractNode implements Serializable {

    HallWayGenerator(AbstractNode preNode, RandomGen rand) {
        int direction = rand.random(2);
        if (direction == 0) {
            width = randomWidth(rand);
            height = 3;
        } else {
            height =  randomHeight(rand);
            width = 3;
        }
        orientation = rand.orientationSelector();
        orientation(orientation, rand, preNode);
    }

    @Override
    public int randomHeight(RandomGen rand) {
        return rand.hallwayHeight();
    }
    @Override
    public int randomWidth(RandomGen rand) {
        return rand.hallwayWidth();
    }
}
