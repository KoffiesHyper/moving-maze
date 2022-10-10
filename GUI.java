import java.awt.*;

public class GUI {

    public static Draw window;

    private static int tileSize = 75;
    private static int boardVPadding = 100 + tileSize;
    private static int boardHPadding = 100 + tileSize;
    private static int gap = 20;

    private static int scoreContainerHeight = 50;

    private static Tile[][] tiles;
    public static int[] playerScores = { 0, 0, 0, 0 };

    private static int width;
    private static int height;

    private static double scorePadding = 0.9;

    private static int pathWidth = 3;

    private static int amountPlants = 100;

    private static GameBoard board;
    private static Tile floatingTile;
    public static boolean slideValid = false;

    public static int[] lastPos = { 0, 0 };
    public static String corner = "";

    private static int[] lastSlide = { -1, -1 };
    private static String lastSlideDir = "";

    public static Color bg = new Color(0, 238, 255, 150);

    private static int[][] tilesImages;
    private static double[] plantx;
    private static double[] planty;
    private static int[] plantType;

    public static boolean canMoveAlongPath = false;
    private static int[] lastPathFinderPos = { -1, -1 };

    public static void passBoard(GameBoard newBoard) {
        board = newBoard;
    }

    public static void passFloatingTile(Tile tile) {
        floatingTile = tile;
    }

    public static void initiateWindow(Tile[][] newTiles) {
        tiles = newTiles;
        if (tiles.length >= 5)
            tileSize = 60;
        width = (tiles[0].length * tileSize) + 2 * boardHPadding + ((tiles[0].length - 1) * gap);
        height = (tiles.length * tileSize) + 2 * boardVPadding + scoreContainerHeight + ((tiles.length - 1) * gap);

        window = new Draw();

        window.setCanvasSize(width, height);
        window.setXscale(0, width);
        window.setYscale(0, height);
        window.setPenRadius(.006);
        window.setPenColor(Color.black);
        window.setTitle("Moving Maze");

        tilesImages = new int[tiles[0].length][tiles.length];

        for (int x = 0; x < tiles[0].length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                double r = Math.random();
                int pic = 0;

                if (r < .2)
                    pic = 1;
                else if (r >= .2 && r < .4)
                    pic = 2;
                else if (r >= .4 && r < .6)
                    pic = 3;
                else if (r >= .6 && r < .8)
                    pic = 4;
                else if (r >= .8 && r < 1)
                    pic = 5;
                tilesImages[x][y] = pic;
            }
        }

        plantx = new double[amountPlants];
        planty = new double[amountPlants];
        plantType = new int[amountPlants];

        for (int i = 0; i < amountPlants; i++) {
            plantx[i] = Math.random() * width;
            planty[i] = Math.random() * (height - scoreContainerHeight);
            plantType[i] = (int) Math.round((Math.random() * 4) + 1);
        }

    }

    public static void refreshWindow(int[] newScores) {
        playerScores = newScores;

        window.enableDoubleBuffering();
        window.clear(new Color(112, 114, 28));

        drawGrass();
        // drawWall();
        drawBoard();
        drawPlayers();
        drawScoreBoard();
        drawScores();
        drawRelics();
        drawInstruction();
        drawProps();

        window.show();
    }

    public static void refreshWindow() {
        window.clear(new Color(112, 114, 28));

        drawGrass();
        // drawWall();
        drawBoard();
        drawPlayers();
        drawScoreBoard();
        drawScores();
        drawRelics();
        drawInstruction();
        drawProps();

        window.show();
    }

    private static void drawProps() {

        window.picture(getX(-1) + tileSize / 2 + 5, getY(-1) - tileSize / 2, "./assests/corner-pillar-shadow.png",
                40, 45);
        window.picture(getX(-1) + tileSize / 2, getY(-1) - tileSize / 2, "./assests/corner-pillar.png", 30, 50);

        window.picture(getX(tiles[0].length) - tileSize / 2 + 5, getY(-1) - tileSize / 2,
                "./assests/corner-pillar-shadow.png", 40, 45);
        window.picture(getX(tiles[0].length) - tileSize / 2, getY(-1) - tileSize / 2,
                "./assests/corner-pillar.png", 30, 50);

        window.picture(getX(tiles[0].length) - tileSize / 2 + 5, getY(tiles.length) + tileSize / 2,
                "./assests/corner-pillar-shadow.png",
                40, 45);
        window.picture(getX(tiles[0].length) - tileSize / 2, getY(tiles.length) + tileSize / 2,
                "./assests/corner-pillar.png", 30, 50);

        window.picture(getX(-1) + tileSize / 2 + 5, getY(tiles.length) + tileSize / 2,
                "./assests/corner-pillar-shadow.png", 40, 45);
        window.picture(getX(-1) + tileSize / 2, getY(tiles.length) + tileSize / 2, "./assests/corner-pillar.png",
                30, 50);

        double frontBenchX = 0;

        if (tiles[0].length % 2 == 0) {
            frontBenchX = (getX(tiles[0].length / 2) + getX(tiles[0].length / 2 - 1)) / 2;
        } else {
            frontBenchX = getX(tiles[0].length / 2);
        }

        int distFromBench = 75;

        window.picture(frontBenchX + 5, getY(-1),
                "./assests/bench-front-shadow.png", 90, 62);
        window.picture(frontBenchX, getY(-1),
                "./assests/bench-front.png", 90, 60);
        window.picture(frontBenchX - distFromBench + 5, getY(-1) - 20,
                "./assests/bush-front-shadow.png", 38, 30);
        window.picture(frontBenchX - distFromBench, getY(-1) - 15,
                "./assests/bush-front.png", 38, 40);
        window.picture(frontBenchX + distFromBench + 5, getY(-1) - 20,
                "./assests/bush-front-shadow.png", 38, 30);
        window.picture(frontBenchX + distFromBench, getY(-1) - 15,
                "./assests/bush-front.png", 38, 40);

        double sideBenchY = 0;

        if (tiles[0].length % 2 == 0) {
            sideBenchY = (getY(tiles.length / 2) + getY(tiles.length / 2 - 1)) / 2;
        } else {
            sideBenchY = getY(tiles.length / 2);
        }

        window.picture(getX(-1) + 10, sideBenchY, "./assests/bench-right-shadow.png", 40, 100);
        window.picture(getX(-1), sideBenchY, "./assests/bench-left.png", 40, 100);
        window.picture(getX(tiles[0].length) + 5, sideBenchY, "./assests/bench-left-shadow.png", 40, 100);
        window.picture(getX(tiles[0].length), sideBenchY, "./assests/bench-right.png", 40, 100);
        window.picture(getX(-1) + 5, sideBenchY - 105, "./assests/bush-front-shadow.png", 38, 30);
        window.picture(getX(-1), sideBenchY - 100, "./assests/bush-front.png", 38, 40);
        window.picture(getX(-1) + 5, sideBenchY + 95, "./assests/bush-front-shadow.png", 38, 30);
        window.picture(getX(-1), sideBenchY + 100, "./assests/bush-front.png", 38, 40);
        window.picture(getX(tiles[0].length) + 5, sideBenchY - 105, "./assests/bush-front-shadow.png", 38, 30);
        window.picture(getX(tiles[0].length), sideBenchY - 100, "./assests/bush-front.png", 38, 40);
        window.picture(getX(tiles[0].length) + 5, sideBenchY + 95, "./assests/bush-front-shadow.png", 38, 30);
        window.picture(getX(tiles[0].length), sideBenchY + 100, "./assests/bush-front.png", 38, 40);
    }

    private static void drawGrass() {
        for (int i = 0; i < amountPlants; i++) {
            window.picture(plantx[i], planty[i], "assests/plant" + plantType[i] + ".png", 20, 20);
        }
    }

    private static void drawWall() {
        // window.picture(width / 2,
        // (height - scoreContainerHeight) / 2 - 30,
        // "./assests/wall2.png",
        // width - 2 * boardHPadding + 106,
        // height - scoreContainerHeight - 2 * boardVPadding + 150);

        window.picture(width / 2,
                (height - scoreContainerHeight) / 2 + 30,
                "./assests/struct-top.png",
                tiles[0].length * (tileSize + gap) * (10 / 8.5) + 20,
                ((tiles.length) * (tileSize + gap)) * (7.6 / 4.9));
        window.picture(width / 2,
                (height - scoreContainerHeight) / 2 - 148,
                "./assests/struct-bottom.png",
                tiles[0].length * (tileSize + gap) * (10 / 8.5) + 20,
                100);

        // window.picture(width/2, 85, "./assests/stairs.png", 170, 250);

        window.picture(30, height / 2, "./assests/statue.png", 50, 100);
        window.picture(width - 30, height / 2, "./assests/statue.png", 50, 100);

        // window.setPenColor(new Color(114, 117, 27));
        // window.filledRectangle(width / 2,
        // (height - scoreContainerHeight) / 2,
        // (width - 2 * boardHPadding) / 2,
        // (height - scoreContainerHeight - 2 * boardVPadding) / 2);
    }

    public static void refreshFloatingTile(double x, double y) {
        Color old = window.getPenColor();
        window.setPenColor(Color.MAGENTA);
        window.square(x, y, tileSize / 4);
        window.show();
        window.setPenColor(old);
    }

    private static void drawWater() {
        for (int x = 0; x < width; x += 50) {
            for (int y = 0; y < height; y += 50) {
                double r = Math.random();
                String water = "";

                if (r < 0.5)
                    water = "water1";
                else
                    water = "water2";

                window.picture(x + 25, y, "./assests/" + water + ".png", 50, 50);
            }
        }
    }

    private static void drawBoard() {
        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                drawTile(i, j);
            }
        }
        window.setPenColor(new Color(110, 38, 14));
        window.setPenRadius(.03);
        // StdDraw.rectangle(width / 2, (height - scoreContainerHeight) / 2,
        // tiles[0].length * tileSize * .5,
        // tiles.length * tileSize * .5);
        window.setPenRadius(.006);
    }

    private static void drawTile(int i, int j) {
        window.setPenColor(((i + j) % 2 == 0) ? new Color(0, 255, 0) : new Color(0, 175, 0));
        window.setPenColor((true) ? new Color(0, 255, 0) : new Color(0, 175, 0));
        window.picture(getX(i), getY(j), "./assests/g" + tilesImages[i][j] + ".png", tileSize,
                tileSize);
        // window.setPenColor(new Color(76,175,126));
        // window.setPenRadius(0.01);
        // window.square(getX(i), getY(j), tileSize / 2);
        addPathsForTile(i, j);
    }

    private static void drawFloatingTile(int i, int j, boolean valid) {
        Color bgColor = valid ? new Color(0, 255, 0, 100) : new Color(255, 0, 0, 100);
        window.setPenColor(bgColor);
        window.filledSquare(getX(i), getY(j), tileSize / 2);
        addPathsForFloatingTile(i, j, valid);

        if (floatingTile.getRelic())
            addRelic(i, j, floatingTile.relic());

        window.setPenColor(new Color(0, 0, 0));
        window.show();
    }

    private static void drawPlayers() {
        int[] greenPos = board.playerCurrentPos("G");
        int[] yellowPos = board.playerCurrentPos("Y");
        int[] redPos = board.playerCurrentPos("R");
        int[] bluePos = board.playerCurrentPos("B");

        drawPlayerOnTile(greenPos[1], greenPos[0], "G");
        drawPlayerOnTile(yellowPos[1], yellowPos[0], "Y");
        drawPlayerOnTile(redPos[1], redPos[0], "R");
        drawPlayerOnTile(bluePos[1], bluePos[0], "B");
    }

    private static void drawPlayerOnTile(int i, int j, String player) {
        double xOffset = 0;
        double yOffset = 0;
        String pic = "";

        switch (player) {
            case "G":
                pic = "player-green";
                window.setPenColor(Color.black);
                xOffset = -tileSize / 4;
                yOffset = tileSize / 4;
                break;
            case "Y":
                pic = "player-yellow";
                window.setPenColor(Color.yellow);
                xOffset = tileSize / 4;
                yOffset = tileSize / 4;
                break;
            case "R":
                pic = "player-red";
                window.setPenColor(Color.red);
                xOffset = -tileSize / 4;
                yOffset = -tileSize / 4;
                break;
            case "B":
                pic = "player-blue";
                window.setPenColor(Color.blue);
                xOffset = tileSize / 4;
                yOffset = -tileSize / 4;
                break;
        }

        String[] players = tiles[j][i].getPlayers();

        for (int x = 1; x < 4; x++)
            if (!players[x].equals(" ")) {
                window.picture(getX(i) + xOffset, getY(j) + yOffset, "assests/" + pic + ".png", 40, 40);
                return;
            }

        window.picture(getX(i), getY(j), "assests/" + pic + ".png", 40, 40);
    }

    private static void drawScoreBoard() {
        int sbWidth = 150;

        window.setPenColor(Color.BLACK);
        window.setPenRadius(.05);
        window.filledRectangle(width / 2, height - scoreContainerHeight / 2, sbWidth,
                scoreContainerHeight / 2);
        window.rectangle(width / 2, height - scoreContainerHeight / 2, sbWidth,
                scoreContainerHeight / 2);
    }

    public static void drawScores() {
        window.setPenColor(Color.WHITE);
        double gap = 260 / 3;
        for (int i = 0; i < 4; i++) {
            String player = "";
            switch (i) {
                case 0:
                    player = "Green";
                    break;
                case 1:
                    player = "Yellow";
                    break;
                case 2:
                    player = "Red";
                    break;
                case 3:
                    player = "Blue";
                    break;
            }
            Font scoreFont = new Font("Monospaced", Font.BOLD, 14);
            window.setFont(scoreFont);
            window.text(width / 2 - 130 + gap * i + 17.5, height - scoreContainerHeight / 2,
                    playerScores[i] + "");

            window.picture(width / 2 - 130 + gap * i - 10, height - scoreContainerHeight / 2,
                    "./assests/player-" + player + ".png", 30, 30);

            if (i == 3)
                continue;
        }
    }

    private static void drawRelics() {
        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[j][i].getRelic()) {
                    addRelic(i, j, tiles[j][i].relic());
                }
            }
        }
    }

    private static void drawInstruction() {
        window.setPenRadius(0.001);
        Font inst = new Font("Monospaced", Font.BOLD, 16);
        window.setFont(inst);
        window.setPenColor(Color.WHITE);
        window.text(width / 2, 100, UserInput.stage);
        Font helpMessage = new Font("Monospaced", Font.PLAIN, 12);
        window.setFont(helpMessage);
        window.text(width / 2, 80, "(Hold and Release H for help)");
    }

    public static int[] getClosestTile(double x, double y) {
        double smallestDist = Double.POSITIVE_INFINITY;
        int[] pos = new int[2];
        ;
        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (dist(getX(i), getY(j), x, y) < smallestDist) {
                    smallestDist = dist(getX(i), getY(j), x, y);
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }

        return pos;
    }

    public static void insertFloatingTile(double x, double y) {
        int[] insertPos = GUI.getClosestTile(x, y);

        lastSlide[0] = insertPos[0];
        lastSlide[1] = insertPos[1];

        String indicator = "";

        if (insertPos[0] == 0) {
            lastSlideDir  = "h";
            indicator = "w" + (insertPos[1] + 1);
        } else if (insertPos[0] == tiles[0].length - 1) {
            lastSlideDir  = "h";
            indicator = "e" + (insertPos[1] + 1);
        } else if (insertPos[1] == 0) {
            lastSlideDir  = "v";
            indicator = "n" + (insertPos[0] + 1);
        } else if (insertPos[1] == tiles.length - 1) {
            lastSlideDir  = "v";
            indicator = "s" + (insertPos[0] + 1);
        }

        board.insertFloatingTile(indicator);
        board.checkAllPlayerRelics();
        refreshWindow();
    }

    public static void rotateFloatingTile(String dir) {
        board.rotateFloatingTile(dir);
    }

    public static boolean checkIfPlayerWon(int player) {
        if (board.playerAtOrigin(player - 1) && board.getPlayerScores()[player - 1] == MovingMaze.k
                && MovingMaze.k > 0) {
            return true;
        }
        return false;
    }

    private static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static void addRelic(int i, int j, String color) {
        Color relicColor;
        String pic = "";

        switch (color) {
            case "G":
                pic = "relic-green.png";
                relicColor = Color.GREEN;
                break;
            case "Y":
                pic = "relic-yellow.png";
                relicColor = Color.YELLOW;
                break;
            case "B":
                pic = "relic-blue.png";
                relicColor = Color.BLUE;
                break;
            case "R":
                pic = "relic-red.png";

                relicColor = Color.RED;
                break;
            default:
                relicColor = Color.BLACK;
                break;
        }

        // window.setPenColor(relicColor);
        // window.filledSquare(getX(i), getY(j), 10);
        window.picture(getX(i), getY(j), "./assests/" + pic, 40, 40);
    }

    private static void addPathsForTile(int i, int j) {
        int shorter = 0;

        Color pathColor = new Color(100, 100, 100);
        pathColor = new Color(0, 238, 255, 150);
        window.setPenColor(bg);
        // window.filledRectangle(getX(i), getY(j), pathWidth / 2, pathWidth / 2);

        if (tiles[j][i].tileOpenOnSide("n")) {
            // window.filledRectangle(getX(i), getY(j) + tileSize / 4 - shorter, pathWidth /
            // 2, tileSize / 4 - shorter);
            window.picture(getX(i), getY(j) + 10 + (tileSize / 2 - 10) / 2, "assests/path-vert.png", pathWidth,
                    (tileSize / 2 - 10));
            window.filledRectangle(getX(i), getY(j) + 10 + (tileSize / 2 - 10) / 2, pathWidth / 2 + 3,
                    (tileSize / 2 - 10) / 2);
        }

        if (tiles[j][i].tileOpenOnSide("e")) {
            // window.filledRectangle(getX(i) + tileSize / 4 - shorter, getY(j), tileSize /
            // 4 - shorter, pathWidth / 2);
            window.picture(getX(i) + 10 + (tileSize / 2 - 10) / 2, getY(j), "assests/path-hori.png", (tileSize / 2) / 2,
                    pathWidth / 2 + 5);
            window.filledRectangle(getX(i) + 10 + (tileSize / 2 - 10) / 2, getY(j), (tileSize / 2 - 10) / 2,
                    pathWidth / 2 + 3);
        }

        if (tiles[j][i].tileOpenOnSide("s")) {
            // window.filledRectangle(getX(i), getY(j) - tileSize / 4 + shorter, pathWidth /
            // 2, tileSize / 4 - shorter);
            window.picture(getX(i), getY(j) - 10 - (tileSize / 2 - 10) / 2, "assests/path-vert.png", pathWidth,
                    (tileSize / 2 - 10));
            window.filledRectangle(getX(i), getY(j) - 10 - (tileSize / 2 - 10) / 2, pathWidth / 2 + 3,
                    (tileSize / 2 - 10) / 2);
        }

        if (tiles[j][i].tileOpenOnSide("w")) {
            // window.filledRectangle(getX(i) - tileSize / 4 + shorter, getY(j), tileSize /
            // 4 - shorter, pathWidth / 2);
            window.picture(getX(i) - 10 - (tileSize / 2 - 10) / 2, getY(j), "assests/path-hori.png", (tileSize / 2) / 2,
                    pathWidth / 2 + 5);
            window.filledRectangle(getX(i) - 10 - (tileSize / 2 - 10) / 2, getY(j), (tileSize / 2 - 10) / 2,
                    pathWidth / 2 + 3);
        }

        window.picture(getX(i), getY(j), "./assests/path-center.png", 15, 15);
    }

    private static void addPathsForFloatingTile(int i, int j, boolean valid) {
        Color pathColor = valid ? new Color(0, 255, 0) : new Color(255, 114, 118);
        window.setPenColor(pathColor);

        window.filledRectangle(getX(i), getY(j), pathWidth * 2, pathWidth / 2);

        if (floatingTile.tileOpenOnSide("n")) {
            window.filledRectangle(getX(i), getY(j) + tileSize / 4, pathWidth * 2, tileSize / 4);
        }

        if (floatingTile.tileOpenOnSide("e")) {
            window.filledRectangle(getX(i) + tileSize / 4, getY(j), tileSize / 4, pathWidth * 2);
        }

        if (floatingTile.tileOpenOnSide("s")) {
            window.filledRectangle(getX(i), getY(j) - tileSize / 4, pathWidth * 2, tileSize / 4);
        }

        if (floatingTile.tileOpenOnSide("w")) {
            window.filledRectangle(getX(i) - tileSize / 4, getY(j), tileSize / 4, pathWidth * 2);
        }
    }

    public static void updateFloatingTile() {
        double x = window.mouseX();
        double y = window.mouseY();

        int[] insertPos = getClosestTile(x, y);
        boolean valid = false;

        if (insertPos[0] == 0 || insertPos[0] == tiles[0].length - 1) {
            valid = (insertPos[1] + 1) % 2 == 0;
        }

        if (insertPos[1] == 0 || insertPos[1] == tiles.length - 1) {
            valid = (insertPos[0] + 1) % 2 == 0;
        }

        if (insertPos[0] == lastSlide[0] && insertPos[1] != lastSlide[1] && lastSlideDir.equals(("v"))) {
            valid = false;
        }
        
        if (insertPos[1] == lastSlide[1] && insertPos[0] != lastSlide[0] && lastSlideDir.equals(("h"))) {
            valid = false;
        }

        slideValid = valid;

        if (insertPos[0] == 0 && insertPos[1] == 0) {
            if (x < getX(insertPos[0]) - tileSize / 2) {
                if (corner.equals("v") || corner.equals("")) {
                    corner = "h";
                    refreshWindow();
                    drawFloatingTile(-1, insertPos[1], valid);
                }

            } else if (x >= getX(insertPos[0]) - tileSize / 2) {
                if (corner.equals("h") || corner.equals("")) {
                    corner = "v";
                    refreshWindow();
                    drawFloatingTile(0, -1, valid);
                }
            }
            lastPos[0] = insertPos[0];
            lastPos[1] = insertPos[1];
            return;
        }

        if (insertPos[0] == 0 && insertPos[1] == tiles.length - 1) {
            if (x < getX(insertPos[0]) - tileSize / 2) {
                if (corner.equals("v") || corner.equals("")) {
                    corner = "h";
                    refreshWindow();
                    drawFloatingTile(-1, insertPos[1], valid);
                }
            } else if (x >= getX(insertPos[0]) - tileSize / 2) {
                if (corner.equals("h") || corner.equals("")) {
                    corner = "v";
                    refreshWindow();
                    drawFloatingTile(0, insertPos[1] + 1, valid);
                }
            }
            lastPos[0] = insertPos[0];
            lastPos[1] = insertPos[1];
            return;
        }

        if (insertPos[0] == tiles[0].length - 1 && insertPos[1] == 0) {
            if (x > getX(insertPos[0]) + tileSize / 2) {
                if (corner.equals("v") || corner.equals("")) {
                    corner = "h";
                    refreshWindow();
                    drawFloatingTile(insertPos[0] + 1, insertPos[1], valid);
                }
            } else if (x <= getX(insertPos[0]) + tileSize / 2) {
                if (corner.equals("h") || corner.equals("")) {
                    corner = "v";
                    refreshWindow();
                    drawFloatingTile(insertPos[0], insertPos[1] - 1, valid);
                }
            }
            lastPos[0] = insertPos[0];
            lastPos[1] = insertPos[1];
            return;
        }

        if (insertPos[0] == tiles[0].length - 1 && insertPos[1] == tiles.length - 1) {
            if (x > getX(insertPos[0]) + tileSize / 2) {
                if (corner.equals("v") || corner.equals("")) {
                    corner = "h";
                    refreshWindow();
                    drawFloatingTile(insertPos[0] + 1, insertPos[1], valid);
                }
            } else if (x <= getX(insertPos[0]) + tileSize / 2) {
                if (corner.equals("h") || corner.equals("")) {
                    corner = "v";
                    refreshWindow();
                    drawFloatingTile(insertPos[0], insertPos[1] + 1, valid);
                }
            }
            lastPos[0] = insertPos[0];
            lastPos[1] = insertPos[1];
            return;
        }

        corner = "";

        if (lastPos[0] == insertPos[0] && lastPos[1] == insertPos[1]) {
            return;
        } else {
            lastPos[0] = insertPos[0];
            lastPos[1] = insertPos[1];
        }

        refreshWindow();

        if (insertPos[0] == 0) {
            drawFloatingTile(-1, insertPos[1], valid);
        } else if (insertPos[0] == tiles[0].length - 1) {
            drawFloatingTile(tiles[0].length, insertPos[1], valid);
        } else if (insertPos[1] == 0) {
            drawFloatingTile(insertPos[0], -1, valid);
        } else if (insertPos[1] == tiles.length - 1) {
            drawFloatingTile(insertPos[0], tiles.length, valid);
        }
    }

    public static void updatePathFinder(String player) {
        double x = window.mouseX();
        double y = window.mouseY();
        int[] insertPos = getClosestTile(x, y);

        if (insertPos[0] == lastPathFinderPos[0] && insertPos[1] == lastPathFinderPos[1])
            return;
        else {
            lastPathFinderPos[0] = insertPos[0];
            lastPathFinderPos[1] = insertPos[1];
        }

        canMoveAlongPath = board.isPathToTile(player, insertPos[0] + 1, insertPos[1] + 1);

        Color c = canMoveAlongPath ? new Color(0, 255, 0, 150)
                : new Color(255, 0, 0, 150);
        refreshWindow();
        window.setPenColor(c);
        window.setPenRadius(0.01);
        window.filledSquare(getX(insertPos[0]), getY(insertPos[1]), tileSize / 2);

        window.show();
    }

    public static String movePlayer(String currentPlayer, String input) {
        String moveResult = board.movePlayer(String.valueOf(currentPlayer.toCharArray()[0]), input);
        return moveResult;
    }

    public static void movePlayer(String currentPlayer, int x, int y) {
        board.movePlayer(String.valueOf(currentPlayer.toCharArray()[0]), x, y);
        board.checkAllPlayerRelics();
        refreshWindow();
    }

    private static int getX(int i) {
        return tileSize * i + boardHPadding + tileSize / 2 + gap * i;
    }

    private static int getY(int j) {
        return height - (tileSize * j + boardVPadding + scoreContainerHeight + tileSize / 2 + gap * j);
    }
}