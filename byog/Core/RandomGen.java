package byog.Core;

import java.io.Serializable;
import java.util.Random;

public class RandomGen implements Serializable {

    private Random RANDOM;


    public RandomGen(long seed) {
        //constructor to generate the first number of the random sequence based on the seed
        RANDOM = new Random(seed);
    }

    public int randomInitx() {
        // generate the initial x position (the second number of the random sequence)
        return RANDOM.nextInt(WorldGenerator.WIDTH);
    }

    public int randomInity() {
        // generate the initial y position (the third number of the random sequence)
        return RANDOM.nextInt(WorldGenerator.HEIGHT);
    }

    public int random(int range) {
        //generate the initial x or y position (the second number of the random sequence)
        return RANDOM.nextInt(range);
    }

    public int featureSelector() {
        // 0 is room, 1 is hallway
        return RANDOM.nextInt(2);
    }

    public int orientationSelector() {
        // 0 is left, 1 is top, 2 is right, 3 is bottom
        /*return RANDOM.nextInt(4);*/
        return RandomUtils.uniform(RANDOM, 4);
    }

    public int branchNumber() {
        return RANDOM.nextInt(100);
    }

    public int roomWidth() {
        /*        return RANDOM.nextInt(5) + 4;*/
        return (int) RandomUtils.gaussian(RANDOM, 5, 1) + 4;
    }

    public int roomHeight() {
        /*        return RANDOM.nextInt(5) + 4;*/
        return (int) RandomUtils.gaussian(RANDOM, 5, 1) + 4;
    }

    public int hallwayWidth() {
        /*        return RANDOM.nextInt(11) + 3;*/
        return (int) RandomUtils.gaussian(RANDOM, 10, 1) + 3;
    }

    public int hallwayHeight() {
        /*        return RANDOM.nextInt(11) + 3;*/
        return (int) RandomUtils.gaussian(RANDOM, 10, 1) + 3;
    }
}
