package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40);
        game.startGame();
        String randstr = game.generateRandomString(seed);

        game.drawFrame(randstr);
        game.flashSequence(randstr);
        game.solicitNCharsInput(seed);
        System.out.println(game.solicitNCharsInput(seed));

    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder result = new StringBuilder(n);
        Random randomStr = new Random(n);
        int index = randomStr.nextInt(25);
        for (int i=0; i<n; i++){
            int indexRandom = randomStr.nextInt(25);
            result.append(CHARACTERS[indexRandom]);
        }

        return result.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        StdDraw.clear(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(20,20,s);
        StdDraw.show();
        StdDraw.pause(2000);

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i=0; i< letters.length(); i++){
            StdDraw.clear(Color.BLACK);
            Font font = new Font("Arial", Font.BOLD, 40);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.WHITE);
            //String str = letters[i];

            StdDraw.text(20,20, Character.toString(letters.charAt(i)));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder result = new StringBuilder(n);

        Font font = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);

        while(StdDraw.hasNextKeyTyped()){
            result.append(StdDraw.nextKeyTyped());
            StdDraw.clear(Color.black);
            StdDraw.text(20,20,result.toString());
            StdDraw.show();
            StdDraw.pause(1000);
        }
        return result.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
    }

}
