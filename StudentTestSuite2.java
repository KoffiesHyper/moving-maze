/*
 * CS144 : Moving Maze project
 * The second hand-in's mock testing suite.  Do not modify this file.
 */

public class StudentTestSuite2 extends StudentTestSuiteBaseClass {

    public static void main(String[] args) {

        // This maze will be used throughout
        String[][] mazeTileEncodings = new String[][]{
            {"1100xx", "1101g1", "1011xx", "0011g2", "0011xx"},
            {"1010y1", "1111xx", "1111xx", "1111xx", "1010r1"},
            {"1110xx", "1111xx", "1111xx", "1111xx", "1011xx"},
            {"0011y2", "1111xx", "1111xx", "1111b2", "0011r2"},
            {"1100xx", "1101b1", "1011xx", "0011xx", "0011xx"},
        };
        String floatingTileEncoding = "1111xx";
        
        printHeader("Testing tileHasRelic:");
        test_tileHasRelic("1111xx", false);
        test_tileHasRelic("1001xx", false);
        test_tileHasRelic("0101xx", false);
        test_tileHasRelic("0101g1", true);
        test_tileHasRelic("0011r1", true);
        test_tileHasRelic("0110y1", true);
        test_tileHasRelic("1111g5", true);
        test_tileHasRelic("1111g9", true);
        
        printHeader("Testing slideTileIntoMaze2:");
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "w2", 'r');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "w4", 'r');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "s2", 'g');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "s4", 'g');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "n2", 'b');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "n4", 'x');

        printOverallStats();

    }

    private static void test_tileHasRelic(String tileEncoding, boolean expectedOutput) {
        try {
            boolean apiCallOutput = MovingMaze.tileHasRelic(tileEncoding);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

    private static void test_slideTileIntoMaze2(String[][] mazeTileEncodings, String floatingTileEncoding, String slidingIndicator, char expectedOutput) {
        try {
            char apiCallOutput = MovingMaze.slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, slidingIndicator);
            reportResultAndIncrementCounters(apiCallOutput, expectedOutput);
        } catch (Exception e) {
            reportCrashAndIncrementCounters(e.getMessage());
        }
    }

}