package byog.Core;


import java.io.Serializable;

abstract class AbstractObject implements Serializable {
    int posX;
    int posY;
    int direction;

    //floor labeled as 0
    //wall labeled as 1
    // nothing labeled as 2
    //monster labeled as 3
    //player labeled as 4
    //dot eaten floor labeled as 5
    //bomb labeled as 6
    //fire labeled as 7
}
