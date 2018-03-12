package byog.Core;


import java.io.Serializable;

/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byog.Core.Game class take over
 * in either keyboard or input string mode.
 */
/*n1234567838*/
/*n12234234234*/
public class Main implements Serializable {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            game.playWithInputString(args[0]);
            System.out.println(game.toString());
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
/*        Game game = new Game();
        game.playWithInputString("n12345435390");
        System.out.println(game.toString());*/

        /*        WorldGenerator newWorld = new WorldGenerator(490467890);*/
    }
}
