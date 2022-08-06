import java.util.function.IntPredicate;

/* Stellenbosch University CS144 Project 2022
 * MovingMaze.java
 * Name and surname: Christian Slier
 * Student number: 26105802
 */

public class MovingMaze {

    // ==========================================================
    // Constants
    // ==========================================================

    // Move these where you'll use them, or refer to them with e.g.
    // MovingMaze.PATH_NESW

    // ═ ║ ╔ ╗ ╚ ╝ ╠ ╣ ╦ ╩ ╬
    // ─ │ ┌ ┐ └ ┘ ├ ┤ ┬ ┴ ┼

    public static final String PATH_EW = "═";
    public static final String PATH_NS = "║";
    public static final String PATH_ES = "╔";
    public static final String PATH_SW = "╗";
    public static final String PATH_NE = "╚";
    public static final String PATH_NW = "╝";
    public static final String PATH_NES = "╠";
    public static final String PATH_NSW = "╣";
    public static final String PATH_ESW = "╦";
    public static final String PATH_NEW = "╩";
    public static final String PATH_NESW = "╬";

    public static GameBoard board;

    static int k = 0;

    // ==========================================================
    // Main function
    // ==========================================================

    public static void main(String[] args) {
        // args[0] will contain the filename of the game board file to be loaded.
        // args[1] will contain either "text" or "gui".

        In file = new In("./" + args[0]);

        int x = file.readInt();
        int y = file.readInt();
        k = file.readInt();

        printStartBanner(k);

        String floatingTileEncoding = file.readString();
        String[][] tileEncodings = extractGameBoard(file, x, y);

        board = new GameBoard(tileEncodings, floatingTileEncoding, x, y, k);
        board.printGameBoard();
        startPromptCycle();
    }

    // ==========================================================
    // Test API functions
    // ==========================================================

    // Populate these with high-level code that references your codebase.

    // ----------------------------------------------------------
    // First hand-in

    public static void startPromptCycle() {

        int player = 1;
        String currentPlayer = "";
        In terminalInput = new In();
        String previousSlide = "w1";
        while (true) {
            switch (player) {
                case 1:
                    currentPlayer = "Green";
                    break;
                case 2:
                    currentPlayer = "Yellow";
                    break;
                case 3:
                    currentPlayer = "Red";
                    break;
                case 4:
                    currentPlayer = "Blue";
                    break;
            }

            while (true) {
                System.out.println("[" + currentPlayer + "] Rotate and slide the floating tile:");
                StdOut.print("> ");

                String input = terminalInput.readString();

                if (input.equals("quit")) {
                    StdOut.println("Game has been quit.");
                    board.printScoreBoard();
                    return;
                }

                if (Integer.parseInt(String.valueOf(input.toCharArray()[1])) % 2 != 0) {
                    StdOut.println("Cannot slide into odd positions.");
                    continue;
                }

                if (input.equals(oppositeSlide(previousSlide))) {
                    StdOut.println("Cannot slide into last exit point.");
                    continue;
                } else
                    previousSlide = input;

                if (input.equals("r") || input.equals("l")) {
                    StdOut.print("Rotating " + (input.equals("r") ? "right" : "left"));
                    board.rotateFloatingTile(input);
                    board.printGameBoard();
                } else {
                    StdOut.print("Inserting at " + input);
                    board.insertFloatingTile(input);
                    board.printGameBoard();
                    break;
                }
            }

            while (true) {
                System.out.println("[" + currentPlayer + "] Move your adventurer:");
                StdOut.print("> ");

                String input = terminalInput.readString();

                if (input.equals("quit")) {
                    StdOut.println("Game has been quit.");
                    board.printScoreBoard();
                    return;
                }

                if (input.equals("done")) {
                    StdOut.println("End of " + currentPlayer + "'s turn.");
                    board.printGameBoard();
                    break;
                }

                if (board.movePlayer(String.valueOf(currentPlayer.toCharArray()[0]), input).equals("collectedRelic")) {
                    board.printGameBoard();
                    StdOut.println(currentPlayer + " has collected a relic.");

                    if (board.getPlayerScores()[player - 1] == k) {
                        StdOut.println(currentPlayer + " has all their relics.");
                    }

                    StdOut.println("End of " + currentPlayer + "'s turn.");
                    board.printScoreBoard();
                    board.printGameBoard();
                    break;
                }

                board.printGameBoard();

                if (board.playerAtOrigin(player - 1) && board.getPlayerScores()[player - 1] == k) {
                    StdOut.println(currentPlayer + " has won.");
                    board.printScoreBoard();
                    return;
                }
            }

            player++;
            if (player == 5)
                player = 1;
        }
    }

    public static void print2D(String[][] array) {
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
                StdOut.print(array[r][c]);
            }
            StdOut.println();
        }
    }

    public static String oppositeSlide(String input) {
        char[] characters = input.toCharArray();
        char dir = characters[0];

        switch (dir) {
            case 'n':
                return String.valueOf('s') + characters[1];
            case 'e':
                return String.valueOf('w') + characters[1];
            case 's':
                return String.valueOf('n') + characters[1];
            case 'w':
                return String.valueOf('e') + characters[1];
        }

        return "";
    }

    public static void printStartBanner(int k) {
        System.out.println(dashes());
        System.out.println("Moving Maze");
        System.out.println("Relic goal: " + k);
        System.out.println(dashes());
    }

    public static String[][] extractGameBoard(In file, int x, int y) {
        String[][] gameBoard = new String[y][x];

        int row = 0;
        int col = 0;

        while (true) {
            if (row == x || col == y)
                break;

            String newTile = file.readString();
            gameBoard[col][row] = newTile;

            if (row == x - 1) {
                row = 0;
                col++;
            } else
                row++;
        }

        return gameBoard;
    }

    public static String dashes() {
        String d = "";

        for (int i = 1; i <= 50; i++) {
            d += "-";
        }

        return d;
    }

    public static boolean isTileOpenToSide(String tileEncoding, char dir) {
        return false; // TODO
    }

    public static boolean[] rotateTileClockwise(String tileEncoding) {
        return new boolean[] { false, false, false, false }; // TODO
    }

    public static boolean[] rotateTileCounterclockwise(String tileEncoding) {
        return new boolean[] { false, false, false, false }; // TODO
    }

    public static boolean[] slideTileIntoMaze1(String[][] mazeTileEncodings, String floatingTileEncoding,
            String slidingIndicator) {
        return new boolean[] { false, false, false, false }; // TODO
    }

    public static boolean canMoveInDirection(String curTileEncoding, String newTileEncoding, char dir) {
        return false; // TODO
    }

    public static boolean canMoveAlongPath(String[][] mazeTileEncodings, char[] steps) {
        return false; // TODO
    }

    // ----------------------------------------------------------
    // Second hand-in

    public static boolean tileHasRelic(String tileEncoding) {
        return false; // TODO
    }

    public static char slideTileIntoMaze2(String[][] mazeTileEncodings, String floatingTileEncoding,
            String slidingIndicator) {
        return '?'; // TODO
    }

}