/*
 * CS144 : Moving Maze project
 * A base class for the testing suites.  Do not modify this file.
 */

import java.util.Arrays;

public abstract class DemiTestSuiteBaseClass {

    public static int tests_ran = 0;
    public static int tests_passed = 0;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String BLUE = "\033[0;34m";

    public static void reportCrashAndIncrementCounters(String message) {
        tests_ran++;
        System.out.println(RED + "Test failed: " + RESET + "crashed with message \"" + message + "\".");
    }

    public static void reportResultAndIncrementCounters(boolean[] apiCallOutput, boolean[] expectedOutput) {
        tests_ran++;
        if (areBooleanArraysEqual(apiCallOutput, expectedOutput)) {
            tests_passed++;
            reportPass();
        } else {
            reportFail();
        }
        reportWhy(Arrays.toString(expectedOutput), Arrays.toString(apiCallOutput));
    }

    public static void reportResultAndIncrementCounters(char apiCallOutput, char expectedOutput) {
        tests_ran++;
        if (apiCallOutput == expectedOutput) {
            tests_passed++;
            reportPass();
        } else {
            reportFail();
        }
        reportWhy(expectedOutput+"", apiCallOutput+"");
    }

    public static void reportResultAndIncrementCounters(boolean apiCallOutput, boolean expectedOutput) {
        tests_ran++;
        if (apiCallOutput == expectedOutput) {
            tests_passed++;
            reportPass();
        } else {
            reportFail();
        }
        reportWhy(expectedOutput+"", apiCallOutput+"");
    }

    public static void printHeader(String header) {
        // printLine();
        System.out.println(RESET + header + RESET);
    }

    public static void reportPass() {
        System.out.print(GREEN + "  Test passed." + RESET);
    }

    public static void reportFail() {
        System.out.print(RED + "  Test failed. " + RESET);
        // System.out.print("");
    }

    public static void reportWhy(String expectedOutput, String apiCallOutput) {
        System.out.println();
        // System.out.println("Expected " + expectedOutput + ", got " + apiCallOutput + ".");
    }

    public static void printLine() {
        System.out.println("==============================");
    }

    public static void printOverallStats() {
        printLine();
        System.out.println((tests_passed == tests_ran ? GREEN : RESET) + tests_passed + "/" + tests_ran + " tests passed." + RESET);
    }

    public static boolean areBooleanArraysEqual(boolean[] arr1, boolean[] arr2) {
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

}