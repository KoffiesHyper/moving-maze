/*
 * CS144 : Moving Maze project
 * The first hand-in's mock testing suite.  Do not modify this file.
 */

public class StudentTestSuite1 extends StudentTestSuiteBaseClass {

    public static void main(String[] args) {

        // This maze will be used throughout the testing suite.
        String[][] mazeTileEncodings = new String[][]{
            //   1         2         3
            {"0110xx", "0101xx", "0011xx"},  // 1
            {"1110xx", "1010xx", "1010xx"},  // 2
            {"1100xx", "1101xx", "1001xx"}   // 3
        };
        
        printHeader("Testing isTileOpenToSide:");
        test_isTileOpenToSide("1111xx", 'n', true);
        test_isTileOpenToSide("0111xx", 'n', false);        
        test_isTileOpenToSide("1011xx", 'n', true);
        test_isTileOpenToSide("1011xx", 's', true);
        test_isTileOpenToSide("1011xx", 'w', true);
        test_isTileOpenToSide("1011xx", 'e', false);
        test_isTileOpenToSide("1100xx", 'n', true);
        test_isTileOpenToSide("1100xx", 'e', true);
        test_isTileOpenToSide("1100xx", 's', false);
        test_isTileOpenToSide("1100xx", 'w', false);

        printHeader("Testing rotateTileClockwise:");
        test_rotateTileClockwise("1111xx", new boolean[]{true, true, true, true});
        test_rotateTileClockwise("1110xx", new boolean[]{false, true, true, true});
        test_rotateTileClockwise("1100xx", new boolean[]{false, true, true, false});
        test_rotateTileClockwise("0110xx", new boolean[]{false, false, true, true});

        printHeader("Testing rotateTileCounterclockwise:");
        test_rotateTileCounterclockwise("1111xx", new boolean[]{true, true, true, true});
        test_rotateTileCounterclockwise("1110xx", new boolean[]{true, true, false, true});
        test_rotateTileCounterclockwise("1010xx", new boolean[]{false, true, false, true});
        test_rotateTileCounterclockwise("1100xx", new boolean[]{true, false, false, true});
        
        printHeader("Testing slideTileIntoMaze1:");
        test_slideTileIntoMaze1(mazeTileEncodings, "1111xx", "n2", new boolean[]{true, true, false, true});
        test_slideTileIntoMaze1(mazeTileEncodings, "1111xx", "s2", new boolean[]{false, true, false, true});
        test_slideTileIntoMaze1(mazeTileEncodings, "1111xx", "e2", new boolean[]{true, true, true, false});
        test_slideTileIntoMaze1(mazeTileEncodings, "1111xx", "w2", new boolean[]{true, false, true, false});

        printHeader("Testing canMoveInDirection:");
        test_canMoveInDirection("1111xx", "1111xx", 'n', true);
        test_canMoveInDirection("1111xx", "1111xx", 'e', true);
        test_canMoveInDirection("1111xx", "1111xx", 's', true);
        test_canMoveInDirection("1111xx", "1111xx", 'w', true);
        test_canMoveInDirection("1100xx", "1010xx", 'n', true);
        test_canMoveInDirection("1100xx", "1100xx", 'n', false);
        test_canMoveInDirection("0011xx", "0011xx", 'n', false);

        printHeader("Testing canMoveAlongPath:");
        // no path
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'s', 's', 'e', 'e'}, true);
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'s', 'e', 'n', 'w'}, false);
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'s', 's', 'e', 'e', 'n', 'n', 'w', 'w'}, true);
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'s', 's', 'e', 'n', 'n', 'w'}, false);
        // off the board
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'w', 'e'}, false);
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'n', 'e', 's'}, false);
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'s', 's', 's'}, false);

        printOverallStats();

    }

    private static void test_isTileOpenToSide(String tileEncoding, char dir, boolean expectedOutput) {
        try {
            boolean apiCallOutput = MovingMaze.isTileOpenToSide(tileEncoding, dir);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

    private static void test_rotateTileClockwise(String tileEncoding, boolean[] expectedOutput) {
        try {
            boolean[] apiCallOutput = MovingMaze.rotateTileClockwise(tileEncoding);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

    private static void test_rotateTileCounterclockwise(String tileEncoding, boolean[] expectedOutput) {
        try {
            boolean[] apiCallOutput = MovingMaze.rotateTileCounterclockwise(tileEncoding);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

    private static void test_slideTileIntoMaze1(String[][] mazeTileEncodings, String floatingTileEncoding, String slidingIndicator, boolean[] expectedOutput) {
        try {
            boolean[] apiCallOutput = MovingMaze.slideTileIntoMaze1(mazeTileEncodings, floatingTileEncoding, slidingIndicator);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

    private static void test_canMoveInDirection(String curTileEncoding, String newTileEncoding, char dir, boolean expectedOutput) {
        try {
            boolean apiCallOutput = MovingMaze.canMoveInDirection(curTileEncoding, newTileEncoding, dir);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

    private static void test_canMoveAlongPath(String[][] mazeTileEncodings, char[] steps, boolean expectedOutput) {
        try {
            boolean apiCallOutput = MovingMaze.canMoveAlongPath(mazeTileEncodings, steps);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

}