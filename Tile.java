/**
 *  Implements the functionality of a tile
 *  @author Christian Slier
 */
public class Tile {

    private static final String PATH_EW = "═";
    private static final String PATH_NS = "║";
    private static final String PATH_ES = "╔";
    private static final String PATH_SW = "╗";
    private static final String PATH_NE = "╚";
    private static final String PATH_NW = "╝";
    private static final String PATH_NES = "╠";
    private static final String PATH_NSW = "╣";
    private static final String PATH_ESW = "╦";
    private static final String PATH_NEW = "╩";
    private static final String PATH_NESW = "╬";

    private String tileEncoding;
    private String[][] textRepresentation;
    private String[] players;
    private boolean relicActive = false;

    public boolean checked = false;

    public Tile next;

    /**
     * Constructor for the {@code Tile} class
     * Within the constructor, the initial state of the tile is initialized
     * @param encoding String encoding representing the tile's data
     */
    public Tile(String encoding) {
        tileEncoding = encoding;
        textRepresentation = new String[3][7];
        players = new String[4];
        players[0] = " ";
        players[1] = " ";
        players[2] = " ";
        players[3] = " ";
    }

    /**
     * Returns the encoding of the {@code Tile} instance
     * @return {@code String} storing the {@code Tile} instance's encoding
     */
    public String getTileEncoding() {
        return tileEncoding;
    }

    public String relic(){
        return (tileEncoding.charAt(4) + "").toUpperCase();
    }

    /**
     * Changes the {@code Tile}'s encoding to {@code newEncoding}
     * @param newEncoding new encoding for the {@code Tile}
     */
    public void setTileEncoding(String newEncoding) {
        tileEncoding = newEncoding;
    }

    /**
     * Adds a player to the {@code Tile}, indicating that {@code player} is currently on this {@code Tile} instance
     * @param player player that is added to this {@code Tile} instance
     */
    public void addPlayer(String player) {
        for (int i = 0; i <= 3; i++) {
            if (players[i].equals(" ")) {
                players[i] = player;
                break;
            }
        }
    }

    /**
     * Removes a specific player from {@code Tile}
     * @param player player that is removed from this {@code Tile} instance
     */
    public void removePlayer(String player) {
        for (int i = 0; i <= 3; i++) {
            if (players[i].equals(player)) {
                players[i] = " ";
                break;
            }
        }
    }

    /**
     * Determines if the {@code Tile} has a specific player on it
     * @param player player that needs to be checked
     * @return {@code String} if {@code player} is on the {@code Tile}
     */
    public String hasPlayer(String player) {
        for (int i = 0; i <= 3; i++) {
            if (players[i].equals(player) || player.equals("any")) {
                return player;
            }
        }

        return " ";
    }

    /**
     * Returns {@code true} if the {@code Tile} is open on side {@code dir}
     * @param dir direction checked
     * @return {@code true} if tile is open on side {@code dir}, {@code false} otherwise
     */
    public boolean tileOpenOnSide(String dir) {

        char[] characters = tileEncoding.toCharArray();
        switch (dir) {
            case "n":
                return characters[0] == '1';
            case "e":
                return characters[1] == '1';
            case "s":
                return characters[2] == '1';
            case "w":
                return characters[3] == '1';
        }

        return false;
    }

    /**
     * Rotates the {@code Tile} in direction indicated by {@code rotateDir}
     * @param rotateDir direction in which to rotate
     */
    public void rotate(String rotateDir) {

        char[] characters;

        if (rotateDir.equals("r")) {
            characters = tileEncoding.toCharArray();
            char lastBit = characters[3];

            for (int i = 3; i >= 1; i--)
                characters[i] = characters[i - 1];

            characters[0] = lastBit;
            tileEncoding = String.valueOf(characters);
        } else if (rotateDir.equals("l")) {
            characters = tileEncoding.toCharArray();
            char firstBit = characters[0];

            for (int i = 0; i <= 2; i++)
                characters[i] = characters[i + 1];

            characters[3] = firstBit;
            tileEncoding = String.valueOf(characters);
        }
    }

    /**
     * Determines whether the {@code Tile} has a relic on it
     * @return {@code true} if the tile has a relic on it, {@code false} otherwise
     */
    public boolean hasRelic(){
        return tileEncoding.toCharArray()[4] != 'x';
    }

    /**
     * Returns {@code true} if the {@code Tile}'s relic is active
     * @return {@code true} if the {@code Tile}'s relic is active, {@code false} otherwise
     */
    public boolean getRelic(){
        return relicActive;
    }

    /**
     * Changes the {@code Tile}'s relic to active/inactive
     * @param active boolean that indicates whether to make the {@code Tile}'s relic active/inactive
     */
    public void setRelic(boolean active){
        relicActive = active;
    }

    /**
     * Returns all players that are on this {@code Tile}
     * @return {@code String[]} that is populated with the players that are currently on this {@code Tile}
     */
    public String[] getPlayers(){
        return players;
    }

    /**
     * Changes {@code this.players} to the {@code player} argument
     * @param players new list of players that are on this {@code Tile}
     */
    public void setPlayers(String[] players){
        this.players = players;
    }

    /**
     * Converts the {@code Tile} to an array of booleans
     * @return {@code boolean[]} populated with four booleans, indicating to which sides the {@code Tile} is open/closed
     */
    public boolean[] toBooleanArray(){
        boolean[] bits = new boolean[4];
        char[] characters = tileEncoding.toCharArray();
        for(int i = 0; i < 4; i++){
            bits[i] = characters[i] == '1';
        }
        return bits;
    }
    
    /**
     * Returns the direction opposite to {@code dir}
     * @param dir direction that's opposite is returned
     * @return {@code String} that stores the opposite direction of {@code dir}
     */
    public String oppositeDir(String dir) {
        switch (dir) {
            case "n":
                return "s";
            case "e":
                return "w";
            case "s":
                return "n";
            case "w":
                return "e";
        }
        return "";
    }

    /**
     * Returns a 2D-String-Array, storing the characters of a text representation of the {@code Tile}
     * @return {@code String[]}
     */
    public String[][] getTextRepresentation() {
        for (int r = 0; r < textRepresentation.length; r++) {
            for (int c = 0; c < textRepresentation[0].length; c++) {
                textRepresentation[r][c] = " ";
            }
        }

        textRepresentation[1][3] = getTileCharacter();

        textRepresentation[0][1] = hasPlayer("G");
        textRepresentation[2][1] = hasPlayer("R");
        textRepresentation[2][5] = hasPlayer("B");
        textRepresentation[0][5] = hasPlayer("Y");

        if(relicActive) {textRepresentation[1][3] = String.valueOf(tileEncoding.toCharArray()[4]); }

        return textRepresentation;
    }

    /**
     * Gives the character that is at the center of the {@code Tile}'s text representation
     * The function also fills in the {@code Tile}'s path characters
     * @return {@code String} that is at the center of the {@code Tile}'s text representation
     */
    public String getTileCharacter() {
        String openDirections = "";

        if (tileEncoding.toCharArray()[0] == '1') {
            openDirections += "N";
            textRepresentation[0][3] = PATH_NS;
        }

        if (tileEncoding.toCharArray()[1] == '1') {
            openDirections += "E";

            for (int i = 0; i <= 2; i++)
                textRepresentation[1][4 + i] = PATH_EW;
        }

        if (tileEncoding.toCharArray()[2] == '1') {
            openDirections += "S";
            textRepresentation[2][3] = PATH_NS;
        }

        if (tileEncoding.toCharArray()[3] == '1') {
            openDirections += "W";

            for (int i = 0; i <= 2; i++)
                textRepresentation[1][0 + i] = PATH_EW;
        }

        if(tileEncoding.charAt(4) != 'x' && relicActive) return
        String.valueOf(tileEncoding.charAt(4));

        switch (openDirections) {
            case "EW":
                return PATH_EW;
            case "NS":
                return PATH_NS;
            case "ES":
                return PATH_ES;
            case "SW":
                return PATH_SW;
            case "NE":
                return PATH_NE;
            case "NW":
                return PATH_NW;
            case "NES":
                return PATH_NES;
            case "NSW":
                return PATH_NSW;
            case "ESW":
                return PATH_ESW;
            case "NEW":
                return PATH_NEW;
            case "NESW":
                return PATH_NESW;
            default:
                return "";
        }
    }
}
