import java.awt.Color;

public class UserInput extends Thread {

    public static String stage = "Slide";
    public static String currentPlayer = "Green";
    public static int player = 1;

    public static boolean pressed = false;

    @Override
    public void run() {
        String moveResult = "";
        while (true) {

            stage = currentPlayer + ": Slide the floating tile";
            GUI.refreshWindow();

            while (true) {

                GUI.updateFloatingTile();

                if (playerQuit()) {
                    stage = currentPlayer + " quit the game.";
                    GUI.refreshWindow();
                    return;
                }

                if (GUI.window.isMousePressed() && GUI.slideValid) {
                    int[] scores = GUI.playerScores.clone();
                    GUI.insertFloatingTile(GUI.window.mouseX(), GUI.window.mouseY());

                    pulseIfCollected(scores);

                    GUI.window.pause(200);
                    break;
                }

                if (GUI.window.isKeyPressed(76)) {
                    GUI.rotateFloatingTile("l");
                    GUI.lastPos[0] = -1;
                    GUI.lastPos[1] = -1;
                    GUI.corner = "";
                    GUI.updateFloatingTile();
                    GUI.window.pause(100);
                }

                if (GUI.window.isKeyPressed(82)) {
                    GUI.rotateFloatingTile("r");
                    GUI.lastPos[0] = -1;
                    GUI.lastPos[1] = -1;
                    GUI.corner = "";
                    GUI.updateFloatingTile();
                    GUI.window.pause(100);
                }

                if (GUI.window.isKeyPressed(72)) {
                    pressed = true;
                }

                if (!GUI.window.isKeyPressed(72) && pressed) {
                    HelpWindow.showHelpWindow();
                    pressed = false;
                }

                GUI.window.pause(50);
            }

            stage = currentPlayer + ": Move your adventurer";
            GUI.refreshWindow();

            while (true) {

                GUI.updatePathFinder(currentPlayer.charAt(0) + "");

                if (GUI.window.isMousePressed() && GUI.canMoveAlongPath) {
                    int[] scores = GUI.playerScores.clone();

                    int[] closestPos = GUI.getClosestTile(GUI.window.mouseX(), GUI.window.mouseY());

                    GUI.movePlayer(currentPlayer, closestPos[0] + 1, closestPos[1] + 1);

                    if(pulseIfCollected(scores).equals(currentPlayer)){
                        break;
                    }

                    GUI.window.pause(200);
                }

                if (playerQuit()) {
                    stage = currentPlayer + " quit the game.";
                    GUI.refreshWindow();
                    return;
                }

                if (GUI.window.isKeyPressed(70)) {
                    break;
                }

                if (GUI.window.isKeyPressed(87)) {
                    moveResult = GUI.movePlayer(currentPlayer, "n");
                    GUI.refreshWindow();

                    if (moveResult.equals("collectedRelic")) {
                        pulseWindow(currentPlayer);
                        break;
                    }

                    if (playerWon(player)) {
                        stage = currentPlayer + " has won!";
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        return;
                    }

                    GUI.window.pause(100);
                }

                if (GUI.window.isKeyPressed(68)) {
                    moveResult = GUI.movePlayer(currentPlayer, "e");
                    GUI.refreshWindow();

                    if (moveResult.equals("collectedRelic")) {
                        pulseWindow(currentPlayer);
                        break;
                    }

                    if (playerWon(player)) {
                        stage = currentPlayer + " has won!";
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        return;
                    }

                    GUI.window.pause(100);
                }

                if (GUI.window.isKeyPressed(83)) {
                    moveResult = GUI.movePlayer(currentPlayer, "s");
                    GUI.refreshWindow();

                    if (moveResult.equals("collectedRelic")) {
                        pulseWindow(currentPlayer);
                        break;
                    }

                    if (playerWon(player)) {
                        stage = currentPlayer + " has won!";
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        return;
                    }

                    GUI.window.pause(100);
                }

                if (GUI.window.isKeyPressed(65)) {
                    moveResult = GUI.movePlayer(currentPlayer, "w");
                    GUI.refreshWindow();

                    if (moveResult.equals("collectedRelic")) {
                        pulseWindow(currentPlayer);
                        break;
                    }

                    if (playerWon(player)) {
                        stage = currentPlayer + " has won!";
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        pulseWindow(currentPlayer);
                        return;
                    }

                    GUI.window.pause(100);
                }

                if (GUI.window.isKeyPressed(72)) {
                    pressed = true;
                }

                if (!GUI.window.isKeyPressed(72) && pressed) {
                    HelpWindow.showHelpWindow();
                    pressed = false;
                }

                GUI.window.pause(100);
            }

            nextPlayer();
        }
    }

    private static String pulseIfCollected(int[] scores) {
        if (scores[0] != GUI.playerScores[0]){
            pulseWindow("Green");
            return "Green";
        }
        if (scores[1] != GUI.playerScores[1]){
            pulseWindow("Yellow");
            return "Yellow";
        }
        if (scores[2] != GUI.playerScores[2]){
            pulseWindow("Red");
            return "Red";
        }
        if (scores[3] != GUI.playerScores[3]){
            pulseWindow("Blue");
            return "Blue";
        }

        return "";
    }

    public static void pulseWindow(String p) {
        Color c = new Color(0, 0, 0);

        switch (p) {
            case "Green":
                c = new Color(0, 255, 0);
                break;
            case "Yellow":
                c = new Color(255, 234, 0);
                break;
            case "Red":
                c = new Color(255, 0, 0);
                break;
            case "Blue":
                c = new Color(0, 0, 255);
                break;
        }

        double initRed = GUI.bg.getRed();
        double initGreen = GUI.bg.getGreen();
        double initBlue = GUI.bg.getBlue();

        int n = 20;

        double deltaR = -(initRed - c.getRed()) / n;
        double deltaG = -(initGreen - c.getGreen()) / n;
        double deltaB = -(initBlue - c.getBlue()) / n;

        for (int i = 0; i < n; i++) {
            Color newColor = new Color((int) (initRed + deltaR * i), (int) (initGreen + deltaG * i),
                    (int) (initBlue + deltaB * i), 150);
            GUI.bg = newColor;
            GUI.refreshWindow();
            GUI.window.pause(10);
        }

        for (int i = 0; i < n; i++) {
            Color newColor = new Color((int) (c.getRed() - deltaR * i), (int) (c.getGreen() - deltaG * i),
                    (int) (c.getBlue() - deltaB * i), 150);
            GUI.bg = newColor;
            GUI.refreshWindow();
            GUI.window.pause(10);
        }

        GUI.bg = new Color((int) initRed, (int) initGreen, (int) initBlue, 150);
        GUI.refreshWindow();
    }

    private static boolean playerQuit() {
        if (GUI.window.isKeyPressed(81)) {
            return true;
        }

        return false;
    }

    private static boolean playerWon(int player) {
        if (GUI.checkIfPlayerWon(player)) {
            return true;
        }

        return false;
    }

    private static void nextPlayer() {
        switch (currentPlayer) {
            case "Green":
                currentPlayer = "Yellow";
                break;
            case "Yellow":
                currentPlayer = "Red";
                break;
            case "Red":
                currentPlayer = "Blue";
                break;
            case "Blue":
                currentPlayer = "Green";
                break;
        }

        player++;
        if (player == 5)
            player = 1;
    }
}