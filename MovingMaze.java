/* Stellenbosch University CS144 Project 2022
 * MovingMaze.java
 * Name and surname: Christian Slier
 * Student number: 26105802
 */

public class MovingMaze {

    // ==========================================================
    // Constants
    // ==========================================================

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

        String floatingTileEncoding = file.readString();
        String[][] tileEncodings = extractGameBoard(file, x, y);

        board = new GameBoard(tileEncodings, floatingTileEncoding, x, y, k);
        startPromptCycle();
    }

    // ==========================================================
    // Test API functions
    // ==========================================================

    // Populate these with high-level code that references your codebase.

    public static boolean isTileOpenToSide(String tileEncoding, char dir) {
        Tile tile = new Tile(tileEncoding);
        return tile.tileOpenOnSide(String.valueOf(dir));
    }

    public static boolean[] rotateTileClockwise(String tileEncoding) {
        Tile tile = new Tile(tileEncoding);
        tile.rotate("r");
        return tile.toBooleanArray();
    }

    public static boolean[] rotateTileCounterclockwise(String tileEncoding) {
        Tile tile = new Tile(tileEncoding);
        tile.rotate("l");
        return tile.toBooleanArray();
    }

    public static boolean[] slideTileIntoMaze1(String[][] mazeTileEncodings, String floatingTileEncoding,
            String slidingIndicator) {

        GameBoard board = new GameBoard(mazeTileEncodings, floatingTileEncoding, mazeTileEncodings.length,
                mazeTileEncodings[0].length, 10);
        board.insertFloatingTile(slidingIndicator);
        return board.getFloatingTile();
    }

    public static boolean canMoveInDirection(String curTileEncoding, String newTileEncoding, char dir) {
        Tile tile1 = new Tile(curTileEncoding);
        Tile tile2 = new Tile(newTileEncoding);
        return tile1.tileOpenOnSide(String.valueOf(dir))
                && tile2.tileOpenOnSide(tile2.oppositeDir(String.valueOf(dir)));
    }

    public static boolean canMoveAlongPath(String[][] mazeTileEncodings, char[] steps) {
        GameBoard board = new GameBoard(
                mazeTileEncodings, "",
                mazeTileEncodings.length,
                mazeTileEncodings[0].length,
                1);

        for (int i = 0; i < steps.length; i++) {
            if (board.movePlayer("G", String.valueOf(steps[i])).equals("error"))
                return false;
        }
        return true;
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

    // ----------------------------------------------------------
    // First hand-in

    public static void startPromptCycle() {
        printStartBanner(k);
        board.printGameBoard();

        int player = 1;
        String currentPlayer = "";
        In terminalInput = new In();
        String previousSlide = "";
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
                System.out.println("[" + currentPlayer + "] Rotate and slide the floating tile: ");
                System.out.print("> ");

                String input = terminalInput.readString();

                if (input.equals("quit")) {
                    System.out.println("Game has been quit.");
                    board.printScoreBoard();
                    return;
                }

                if (input.length() > 1 && Integer.parseInt(String.valueOf(input.toCharArray()[1])) % 2 != 0) {
                    System.out.println("Cannot slide into odd positions.");
                    continue;
                }

                if (input.equals(oppositeSlide(previousSlide))) {
                    System.out.println("Cannot slide into last exit point.");
                    continue;
                } else
                    previousSlide = input;

                if (input.equals("r") || input.equals("l")) {
                    System.out.println("Rotating " + (input.equals("r") ? "right." : "left."));
                    board.rotateFloatingTile(input);
                    board.printGameBoard();
                } else {
                    System.out.println("Inserting at " + input + ".");
                    board.insertFloatingTile(input);
                    board.printGameBoard();
                    break;
                }
            }

            while (true) {
                System.out.println("[" + currentPlayer + "] Move your adventurer: ");
                System.out.print("> ");

                String input = terminalInput.readString();

                if (input.equals("quit")) {
                    System.out.println("Game has been quit.");
                    board.printScoreBoard();
                    return;
                }

                if (input.equals("done")) {
                    System.out.println("End of " + currentPlayer + "'s turn.");
                    board.printScoreBoard();
                    board.printGameBoard();
                    break;
                }

                String moveResult = board.movePlayer(String.valueOf(currentPlayer.toCharArray()[0]), input);

                if (moveResult.equals("collectedRelic")) {
                    board.printGameBoard();
                    System.out.println(currentPlayer + " has collected a relic.");

                    if (board.getPlayerScores()[player - 1] == k) {
                        System.out.println(currentPlayer + " has all their relics.");
                    }

                    System.out.println("End of " + currentPlayer + "'s turn.");
                    board.printScoreBoard();
                    board.printGameBoard();
                    break;
                } else if (moveResult.equals("success"))
                    board.printGameBoard();

                if (board.playerAtOrigin(player - 1) && board.getPlayerScores()[player - 1] == k && k > 0) {
                    System.out.println(currentPlayer + " has won.");
                    board.printScoreBoard();
                    return;
                }
            }

            player++;
            if (player == 5)
                player = 1;
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
}