/*
 * CS144 : Moving Maze project
 * The first hand-in's mock testing suite.  Do not modify this file.
 */

public class DemiTestSuite1 extends DemiTestSuiteBaseClass {

    public static void main(String[] args) {

        System.out.println("API Test Suite --- hand-in 1");
        System.out.println("There are 10 marks available.  Each test case counts 1 mark.");
        printLine();

        // This maze will be used throughout the testing suite.
        String[][] mazeTileEncodings = new String[][]{
            //   1         2         3
            {"0110xx", "0111xx", "0011xx"}, // 1
            {"1010xx", "1111xx", "1011xx"}, // 2
            {"1110xx", "1111xx", "1010xx"}, // 3
            {"0101xx", "1111xx", "0101xx"}, // 4
            {"1100xx", "1111xx", "1001xx"}  // 5
        };
        
        printHeader("Testing isTileOpenToSide");
        test_isTileOpenToSide("1001xx", 'e', false);
        test_isTileOpenToSide("1001xx", 'w', true);

        printHeader("Testing rotateTileClockwise");
        test_rotateTileClockwise("1001xx", new boolean[]{true, true, false, false});

        printHeader("Testing rotateTileCounterclockwise");
        test_rotateTileCounterclockwise("1001xx", new boolean[]{false, false, true, true});
        
        printHeader("Testing slideTileIntoMaze1");
        test_slideTileIntoMaze1(mazeTileEncodings, "1111xx", "w2", new boolean[]{true, false, true, true});
        test_slideTileIntoMaze1(mazeTileEncodings, "1111xx", "e2", new boolean[]{true, false, true, false});

        printHeader("Testing canMoveInDirection");
        test_canMoveInDirection("1010xx", "1010xx", 'n', true);
        test_canMoveInDirection("1010xx", "1010xx", 'e', false);

        printHeader("Testing canMoveAlongPath");
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'s', 's', 'e', 's', 's', 'w'}, true);
        test_canMoveAlongPath(mazeTileEncodings, new char[]{'w'}, false);

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