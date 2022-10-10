/**
 *  Implements the functionality of the games's board
 *  @author Christian Slier
 */
public class GameBoard {

    private static final String BORDER_HORI = "─";
    private static final String BORDER_VERT = "│";
    private static final String BORDER_TOPLEFT = "┌";
    private static final String BORDER_TOPRIGHT = "┐";
    private static final String BORDER_BOTTOMLEFT = "└";
    private static final String BORDER_BOTTOMRIGHT = "┘";
    private static final String BORDER_LEFTEDGE = "├";
    private static final String BORDER_RIGHTEDGE = "┤";
    private static final String BORDER_TOPEDGE = "┬";
    private static final String BORDER_BOTTOMEDGE = "┴";
    private static final String BORDER_MIDDLE = "┼";

    private Tile[][] tiles;

    /**
     * Floating tile of the game
     */
    public Tile floatingTile;

    private String[] gameBoard; 

    private int k = 0;

    private int[] playerScore = new int[4];

    /**
     * Constructor for the {@code GameBoard} class
     * Within the constructor, the initial state of the board is initialized
     * @param tileEncodings 2D-Array of Strings, representing the tile-encodings for the tiles of the board
     * @param floatingEncoding representation of the game's starting floating tile
     * @param x width of the board
     * @param y height of the board
     * @param k score to reach in order to win the game
     * @see Tile
     */
    public GameBoard(String[][] tileEncodings, String floatingEncoding, int x, int y, int k) {
        tiles = new Tile[y][x];
        char[] characters;
        this.k = k;

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                tiles[i][j] = new Tile(tileEncodings[i][j]);

                if (i == 0 && j == 0)
                    tiles[i][j].addPlayer("G");
                if (i == tiles.length - 1 && j == 0)
                    tiles[i][j].addPlayer("R");
                if (i == 0 && j == tiles[0].length - 1)
                    tiles[i][j].addPlayer("Y");
                if (i == tiles.length - 1 && j == tiles[0].length - 1)
                    tiles[i][j].addPlayer("B");

                characters = tileEncodings[i][j].toCharArray();

                if (characters[4] != 'x' && characters[5] == '1') {
                    tiles[i][j].setRelic(true);
                }
            }
        }

        GUI.initiateWindow(tiles);

        if (floatingEncoding.length() > 0) {

            floatingTile = new Tile(floatingEncoding);
            char[] ftEncoding = floatingEncoding.toCharArray();
            if (ftEncoding[4] != 'x' && ftEncoding[5] == '1') {
                floatingTile.setRelic(true);
            }

            GUI.passFloatingTile(floatingTile);
        }

        gameBoard = new String[(tiles.length * 3) + (tiles.length + 1)];
    }

    /**
     * Rotates the floating tile in the direction specified by {@code rotateDir}
     * Calls the {@code rotate} function on the floating tile's {@code Tile} instance
     * @param rotateDir direction of rotation
     * @see Tile#rotate
     */
    public void rotateFloatingTile(String rotateDir) {
        floatingTile.rotate(rotateDir);
    }

    /**
     * Used to get the values of the players' scores
     * @return {@code int[]}
     */
    public int[] getPlayerScores() {
        return playerScore;
    }

    /**
     * Determines if {@code player} is on his/her starting tile
     * @param player the current player's position in the order in which the game is played
     * @return {@code true} if {@code player} is currently on his/her starting tile
     * @see playerCurrentPos
     */
    public boolean playerAtOrigin(int player) {
        int[] pos = { 0, 0 };

        switch (player) {
            case 0:
                pos = playerCurrentPos("G");
                return pos[0] == 0 && pos[1] == 0;
            case 1:
                pos = playerCurrentPos("Y");
                return pos[0] == 0 && pos[1] == tiles[0].length - 1;
            case 2:
                pos = playerCurrentPos("R");
                return pos[0] == tiles.length - 1 && pos[1] == 0;
            case 3:
                pos = playerCurrentPos("B");
                return pos[0] == tiles.length - 1 && pos[1] == tiles[0].length - 1;
        }

        return false;
    }

    /**
     * Moves {@code player} in the direction specified by {@code dir}
     * @param player player that is to be moved
     * @param dir direction in which the player is to be moved
     * @return {@code String} - result of move: {@code error}/{@code success}/{@code collectedRelic}
     */
    public String movePlayer(String player, String dir) {
        int[] currentPos = playerCurrentPos(player);
        int i = currentPos[0];
        int j = currentPos[1];

        String fullDir = "";

        switch (dir) {
            case "n":
                fullDir = "north";
                break;
            case "e":
                fullDir = "east";
                break;
            case "s":
                fullDir = "south";
                break;
            case "w":
                fullDir = "west";
                break;
        }

        if (!tiles[i][j].tileOpenOnSide(dir)) {
            StdOut.println("Cannot move " + fullDir + ": no path.");
            return "error";
        }

        if (dir.equals("e")) {
            if (j == tiles[0].length - 1) {
                StdOut.println("Cannot move " + fullDir + ": off the board.");
                return "error";
            }

            if (tiles[i][j + 1].tileOpenOnSide("w")) {
                tiles[i][j].removePlayer(player);
                tiles[i][j + 1].addPlayer(player);
            } else {
                StdOut.println("Cannot move " + fullDir + ": no path.");
                return "error";
            }
        }

        if (dir.equals("w")) {
            if (j == 0) {
                StdOut.println("Cannot move " + fullDir + ": off the board.");
                return "error";
            }

            if (tiles[i][j - 1].tileOpenOnSide("e")) {
                tiles[i][j].removePlayer(player);
                tiles[i][j - 1].addPlayer(player);
            } else {
                StdOut.println("Cannot move " + fullDir + ": no path.");
                return "error";
            }
        }

        if (dir.equals("n")) {
            if (i == 0) {
                StdOut.println("Cannot move " + fullDir + ": off the board.");
                return "error";
            }

            if (tiles[i - 1][j].tileOpenOnSide("s")) {
                tiles[i][j].removePlayer(player);
                tiles[i - 1][j].addPlayer(player);
            } else {
                StdOut.println("Cannot move " + fullDir + ": no path.");
                return "error";
            }
        }

        if (dir.equals("s")) {
            if (i == tiles.length - 1) {
                StdOut.println("Cannot move " + fullDir + ": off the board.");
                return "error";
            }

            if (tiles[i + 1][j].tileOpenOnSide("n")) {
                tiles[i][j].removePlayer(player);
                tiles[i + 1][j].addPlayer(player);
            } else {
                StdOut.println("Cannot move " + fullDir + ": no path.");
                return "error";
            }
        }

        StdOut.println("Moving " + fullDir + ".");

        return onRelic(player);
    }

    public void movePlayer(String player, int i, int j){
        int[] pos = playerCurrentPos(player.charAt(0) + "");

        tiles[pos[0]][pos[1]].removePlayer(player.charAt(0) + "");
        tiles[j-1][i-1].addPlayer(player.charAt(0) + "");
    }

    public boolean isPathToTile(String player, int x, int y){
        int[] pos = playerCurrentPos(player);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].checked = false;
            }
        }

        return isPathToTile(pos[0], pos[1], tiles[y-1][x-1]);
    }
 
    public boolean isPathToTile(int i, int j, Tile goal){
        if(tiles[i][j] == goal) return true;

        tiles[i][j].checked = true;

        if(i != 0 && tiles[i][j].tileOpenOnSide("n") && tiles[i-1][j].tileOpenOnSide("s") && !tiles[i-1][j].checked && isPathToTile(i-1, j, goal)) return true;
        if(i != tiles.length-1 && tiles[i][j].tileOpenOnSide("s") && tiles[i+1][j].tileOpenOnSide("n") && !tiles[i+1][j].checked && isPathToTile(i+1, j, goal)) return true;
        if(j != 0  && tiles[i][j].tileOpenOnSide("w") && tiles[i][j-1].tileOpenOnSide("e") && !tiles[i][j-1].checked && isPathToTile(i, j-1, goal)) return true;
        if(j != tiles[0].length-1 && tiles[i][j].tileOpenOnSide("e") && tiles[i][j+1].tileOpenOnSide("w") && !tiles[i][j+1].checked && isPathToTile(i, j+1, goal)) return true;

        return false;
    }
    
    public String onRelic(String player){
        int x = playerCurrentPos(player)[0];
        int y = playerCurrentPos(player)[1];
        int playerNum = 0;

        if (tiles[x][y].getRelic() && hasPlayerRelic(x, y, player)) {
            switch (player) {
                case "G":
                    playerScore[0] = playerScore[0] + 1;
                    playerNum = 0;
                    break;
                case "Y":
                    playerScore[1] = playerScore[1] + 1;
                    playerNum = 1;
                    break;
                case "R":
                    playerScore[2] = playerScore[2] + 1;
                    playerNum = 2;
                    break;
                case "B":
                    playerScore[3] = playerScore[3] + 1;
                    playerNum = 3;
                    break;
            }

            activateNextRelic(player, playerNum);
            tiles[x][y].setRelic(false);
            return "collectedRelic";
        } else {
            return "success";
        }
    }

    public void checkAllPlayerRelics(){
        String[] players = {"Green", "Yellow", "Blue", "Red"};

        for(int i = 0; i < 4; i++){
            if (onRelic(players[i].charAt(0)+"").equals("collectedRelic")) {
                System.out.println(players[i] + " has collected a relic.");
                printScoreBoard();
            }
        }
    }

    /**
     * Makes the current player's next relic visible
     * @param player player who's next relic needs to be made active
     * @param num number that represent the current player
     */
    private void activateNextRelic(String player, int num) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (hasPlayerRelic(x, y, player) && Integer
                        .parseInt(String.valueOf(tiles[x][y].getTileEncoding().toCharArray()[5])) == playerScore[num]
                                + 1) {
                    tiles[x][y].setRelic(true);
                    return;
                }
            }
        }

        char[] ftEncoding = floatingTile.getTileEncoding().toCharArray();

        if (String.valueOf(ftEncoding[4]).equals(player.toLowerCase())
                && Integer.parseInt(String.valueOf(ftEncoding[5])) == playerScore[num] + 1) {
            floatingTile.setRelic(true);
            return;
        }
    }

    /**
     * Determines if a specific tile has a specific player's relic on it
     * @param x row of the specific tile
     * @param y column of the specific tile
     * @param player player who's relic needs to be checked
     * @return  {@code true} if the tile in position {@code(x, y)} has a relic for {@code player}
     */
    private boolean hasPlayerRelic(int x, int y, String player) {
        if (String.valueOf(tiles[x][y].getTileEncoding().toCharArray()[4]).equals(player.toLowerCase()))
            return true;

        return false;
    }

    /**
     * Returns the position of a specific player
     * Returns an Array of Integers of length 2, containing the x-y value of the player's position
     * @param player player who's position needs to be returned
     * @return {@code int[]}
     */
    public int[] playerCurrentPos(String player) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j].hasPlayer(player).equals(player)) {
                    int[] pos = { i, j };
                    return pos;
                }
            }
        }

        int[] placeholder = { 0, 0 };
        return placeholder;
    }

    /**
     * Inserts the floating tile in the location indicated by {@code indicator}
     * @param indicator location in which to insert the floating tile
     */
    public void insertFloatingTile(String indicator) {
        char insertSide = indicator.charAt(0);
        int pos = Integer.parseInt(String.valueOf(indicator.charAt(1)));
        String[] playersOffBoard = { "", "", "", "" };

        if (insertSide == 'w' | insertSide == 'n') {
            String newFloatingEncoding = "";
            boolean newFloatingRelicAct = false;
            if (insertSide == 'w') {
                playersOffBoard = tiles[pos - 1][tiles[0].length - 1].getPlayers();
                newFloatingEncoding = tiles[pos - 1][tiles[0].length - 1].getTileEncoding();
                newFloatingRelicAct = tiles[pos - 1][tiles[0].length - 1].getRelic();
                for (int i = tiles[0].length - 1; i >= 1; i--) {
                    tiles[pos - 1][i].setTileEncoding(tiles[pos - 1][i - 1].getTileEncoding());
                    copyTileInfo(tiles[pos - 1][i], tiles[pos - 1][i - 1]);
                }
                tiles[pos - 1][0].setTileEncoding(floatingTile.getTileEncoding());
                copyTileInfo(tiles[pos - 1][0], floatingTile);
                tiles[pos - 1][0].setPlayers(playersOffBoard);
            } else if (insertSide == 'n') {
                playersOffBoard = tiles[tiles.length - 1][pos - 1].getPlayers();
                newFloatingEncoding = tiles[tiles.length - 1][pos - 1].getTileEncoding();
                newFloatingRelicAct = tiles[tiles.length - 1][pos - 1].getRelic();
                for (int i = tiles.length - 1; i >= 1; i--) {
                    tiles[i][pos - 1].setTileEncoding(tiles[i - 1][pos - 1].getTileEncoding());
                    copyTileInfo(tiles[i][pos - 1], tiles[i - 1][pos - 1]);
                }
                tiles[0][pos - 1].setTileEncoding(floatingTile.getTileEncoding());
                copyTileInfo(tiles[0][pos - 1], floatingTile);
                tiles[0][pos - 1].setPlayers(playersOffBoard);
            }

            floatingTile.setTileEncoding(newFloatingEncoding);
            floatingTile.setRelic(newFloatingRelicAct);
        }

        if (insertSide == 'e' | insertSide == 's') {
            String newFloatingEncoding = "";
            boolean newFloatingRelicAct = false;
            if (insertSide == 'e') {
                playersOffBoard = tiles[pos - 1][0].getPlayers();
                newFloatingEncoding = tiles[pos - 1][0].getTileEncoding();
                newFloatingRelicAct = tiles[pos - 1][0].getRelic();
                for (int i = 0; i <= tiles[0].length - 2; i++) {
                    tiles[pos - 1][i].setTileEncoding(tiles[pos - 1][i + 1].getTileEncoding());
                    copyTileInfo(tiles[pos - 1][i], tiles[pos - 1][i + 1]);
                }
                tiles[pos - 1][tiles[0].length - 1].setTileEncoding(floatingTile.getTileEncoding());
                copyTileInfo(tiles[pos - 1][tiles[0].length - 1], floatingTile);
                tiles[pos - 1][tiles[0].length - 1].setPlayers(playersOffBoard);

            } else if (insertSide == 's') {
                playersOffBoard = tiles[0][pos - 1].getPlayers();
                newFloatingEncoding = tiles[0][pos - 1].getTileEncoding();
                newFloatingRelicAct = tiles[0][pos - 1].getRelic();
                for (int i = 0; i <= tiles.length - 2; i++) {
                    tiles[i][pos - 1].setTileEncoding(tiles[i + 1][pos - 1].getTileEncoding());
                    copyTileInfo(tiles[i][pos - 1], tiles[i + 1][pos - 1]);
                }
                tiles[tiles.length - 1][pos - 1].setTileEncoding(floatingTile.getTileEncoding());
                copyTileInfo(tiles[tiles.length - 1][pos - 1], floatingTile);
                tiles[tiles.length - 1][pos - 1].setPlayers(playersOffBoard);
            }

            floatingTile.setTileEncoding(newFloatingEncoding);
            floatingTile.setRelic(newFloatingRelicAct);
        }
    }

    /**
     * Copies data from {@code from} to {@code to}
     * @param to {@code Tile} to which to copy
     * @param from {@code Tile} from which to copy 
     */
    private void copyTileInfo(Tile to, Tile from) {
        to.setRelic(from.getRelic());
        to.setPlayers(from.getPlayers());
    }

    /**
     * Prints the scoreboard
     */
    public void printScoreBoard() {
        StdOut.println("Relics collected /" + k + ":");
        StdOut.printf("%-9s", "- Green");
        StdOut.println(playerScore[0]);
        StdOut.printf("%-9s", "- Yellow");
        StdOut.println(playerScore[1]);
        StdOut.printf("%-9s", "- Red");
        StdOut.println(playerScore[2]);
        StdOut.printf("%-9s", "- Blue");
        StdOut.println(playerScore[3]);
    }

    /**
     * Prints the game board
     */
    public void printGameBoard() {
        String[] boardText = getTextRepresentation();
        String[] floatingTile = getFloatingTileText();
        System.out.println();

        for (int r = 0; r < boardText.length; r++) {
            System.out.println(boardText[r]);
        }
        System.out.println();

        for (int r = 0; r < floatingTile.length; r++) {
            System.out.println(floatingTile[r]);
        }
        System.out.println();
    }

    /**
     * Converts the {@code tiles} array to a 1D-String-Array
     * @return {@code String[]} containing the lines that need to be printed to display the game board
     */
    public String[] getTextRepresentation() {
        for (int r = 0; r < gameBoard.length; r++) {
            gameBoard[r] = "";
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                String[][] tileText = tiles[i][j].getTextRepresentation();

                for (int k = 0; k < 3; k++) {
                    gameBoard[(i * 3) + (k + i + 1)] += String.join("", tileText[k]);
                }
            }
        }

        for (int x = 0; x < gameBoard.length; x += 4) {
            String line = "";

            for (int i = 0; i < (tiles[0].length * 7); i++) {
                line += BORDER_HORI;
            }

            gameBoard[x] = line;
        }

        for (int x = 1; x < gameBoard.length - 1; x++) {
            if (gameBoard[x].charAt(0) == BORDER_HORI.charAt(0))
                gameBoard[x] = BORDER_LEFTEDGE + gameBoard[x] + BORDER_RIGHTEDGE;
            else
                gameBoard[x] = BORDER_VERT + gameBoard[x] + BORDER_VERT;
        }

        gameBoard[0] = BORDER_TOPLEFT + gameBoard[0].substring(0, gameBoard[0].length()) + BORDER_TOPRIGHT;
        gameBoard[gameBoard.length - 1] = BORDER_BOTTOMLEFT
                + gameBoard[gameBoard.length - 1].substring(0, gameBoard[gameBoard.length - 1].length())
                + BORDER_BOTTOMRIGHT;

        for (int x = 8; x < tiles[0].length * 8 - 1; x += 8) {
            for (int y = 0; y < gameBoard.length; y++) {
                String edgeSymbol = y == gameBoard.length - 1 ? BORDER_BOTTOMEDGE
                        : (y == 0 ? BORDER_TOPEDGE : BORDER_VERT);
                gameBoard[y] = gameBoard[y].substring(0, x) + edgeSymbol
                        + gameBoard[y].substring(x, gameBoard[y].length());
            }
        }

        for (int i = 1; i < gameBoard.length - 1; i++) {
            char[] characters = gameBoard[i].toCharArray();

            for (int j = 1; j < characters.length - 1; j++) {
                if (characters[j] == BORDER_VERT.charAt(0) && characters[j + 1] == BORDER_HORI.charAt(0)) {
                    characters[j] = BORDER_MIDDLE.charAt(0);
                    gameBoard[i] = String.valueOf(characters);
                }
            }
        }

        int rowCounter = 1;

        for (int i = 0; i < gameBoard.length; i++) {
            if ((i + 2) % 4 == 0) {
                gameBoard[i] = rowCounter + gameBoard[i] + rowCounter;
                rowCounter++;
            } else
                gameBoard[i] = " " + gameBoard[i];
        }

        String horiNumbers = " ";

        for (int i = 1; i <= tiles[0].length; i++) {
            horiNumbers += "    " + i + "   ";
        }

        String[] boardWithNumbers = new String[gameBoard.length + 2];

        for (int i = 0; i < gameBoard.length; i++)
            boardWithNumbers[i + 1] = gameBoard[i];
        boardWithNumbers[0] = horiNumbers + "    ";
        boardWithNumbers[boardWithNumbers.length - 1] = horiNumbers + "    ";

        return boardWithNumbers;
    }
    
    /**
     * Returns a boolean array, indicating if the {@code floatingTile} is open to any side
     * 1st element represents North, 2nd element represents west, etc.
     * @return {@code boolean[]}
     */
    public boolean[] getFloatingTile() {
        return floatingTile.toBooleanArray();
    }

    /**
     * Converts the {@code floatingTile} to a 1D-String-Array
     * @return {@code String[]} containing the lines that need to be printed to display the floating tile
     */
    public String[] getFloatingTileText() {
        String[] lines = new String[5];

        for (int k = 0; k < 3; k++) {
            lines[k + 1] = BORDER_VERT + String.join("", floatingTile.getTextRepresentation()[k]) + BORDER_VERT;
        }

        lines[0] = "┌───────┐";
        lines[4] = "└───────┘";

        char[] characters = lines[2].toCharArray();
        characters[4] = floatingTile.getTileCharacter().charAt(0);

        lines[2] = String.valueOf(characters);

        return lines;
    }

    public void refreshGUI(){
        GUI.refreshWindow(playerScore);
    }
}
