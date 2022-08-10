public class Tile {

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

    public static final String BORDER_HORI = "─";
    public static final String BORDER_VERT = "│";
    public static final String BORDER_TOPLEFT = "┌";
    public static final String BORDER_TOPRIGHT = "┐";
    public static final String BORDER_BOTTOMLEFT = "└";
    public static final String BORDER_BOTTOMRIGHT = "┘";
    public static final String BORDER_LEFTEDGE = "├";
    public static final String BORDER_RIGHTEDGE = "┤";
    public static final String BORDER_TOPEDGE = "┬";
    public static final String BORDER_BOTTOMEDGE = "┴";
    public static final String BORDER_MIDDLE = "┼";

    private String tileEncoding;
    private String[][] textRepresentation;
    private String[] players;
    private boolean relicActive = false;

    public Tile(String c) {
        tileEncoding = c;
        textRepresentation = new String[3][7];
        players = new String[4];
        players[0] = " ";
        players[1] = " ";
        players[2] = " ";
        players[3] = " ";
    }

    public String getTileEncoding() {
        return tileEncoding;
    }

    public void setTileEncoding(String newEncoding) {
        tileEncoding = newEncoding;
    }

    public void addPlayer(String player) {
        for (int i = 0; i <= 3; i++) {
            if (players[i].equals(" ")) {
                players[i] = player;
                break;
            }
        }
    }

    public void removePlayer(String player) {
        for (int i = 0; i <= 3; i++) {
            if (players[i].equals(player)) {
                players[i] = " ";
                break;
            }
        }
    }

    public String hasPlayer(String player) {
        for (int i = 0; i <= 3; i++) {
            if (players[i].equals(player) || player.equals("any")) {
                return player;
            }
        }

        return " ";
    }

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

    public boolean getRelic(){
        return relicActive;
    }

    public void setRelic(boolean active){
        relicActive = active;
    }

    public String[] getPlayers(){
        return players;
    }

    public void setPlayers(String[] players){
        this.players = players;
    }

    public boolean[] toBooleanArray(){
        boolean[] bits = new boolean[4];
        char[] characters = tileEncoding.toCharArray();
        for(int i = 0; i < 4; i++){
            bits[i] = characters[i] == '1';
        }
        return bits;
    }
    
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

    public String[][] getTextRepresentation() {
        for (int r = 0; r < textRepresentation.length; r++) {
            for (int c = 0; c < textRepresentation[0].length; c++) {
                textRepresentation[r][c] = " ";
            }
        }

        textRepresentation[1][3] = getTileCharacter(tileEncoding);

        textRepresentation[0][1] = hasPlayer("G");
        textRepresentation[2][1] = hasPlayer("R");
        textRepresentation[2][5] = hasPlayer("B");
        textRepresentation[0][5] = hasPlayer("Y");

        if(relicActive) {textRepresentation[1][3] = String.valueOf(tileEncoding.toCharArray()[4]); }

        return textRepresentation;
    }

    public String getTileCharacter(String tileEncoding) {
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

        // if(tileEncoding.charAt(4) != 'x') return
        // String.valueOf(tileEncoding.charAt(4));

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
