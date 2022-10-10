/*
 * CS144 : Moving Maze project
 * The second hand-in's testing suite.  Do not modify this file.
 */

public class DemiTestSuite2 extends DemiTestSuiteBaseClass {

    public static void main(String[] args) {

        // This maze will be used throughout
        String[][] mazeTileEncodings = new String[][]{
            {"1111xx", "1111xx", "1111xx"},
            {"1111xx", "1111xx", "1111g1"},
            {"1111xx", "1111xx", "1111xx"},
            {"1111xx", "1111xx", "1111r1"},
            {"1111xx", "1111xx", "1111xx"},
            {"1111xx", "1111xx", "1111xx"},
            {"1111xx", "1111xx", "1111xx"},
            {"1111xx", "1111xx", "1111y1"},
            {"1111xx", "1111xx", "1111xx"},
        };
        String floatingTileEncoding = "1111b1";
        
        printHeader("Testing tileHasRelic:");
        test_tileHasRelic(floatingTileEncoding, true);
        
        printHeader("Testing slideTileIntoMaze2:");
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "w2", 'g');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "w4", 'r');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "w6", 'x');
        test_slideTileIntoMaze2(mazeTileEncodings, floatingTileEncoding, "w8", 'y');

        // printOverallStats();

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
