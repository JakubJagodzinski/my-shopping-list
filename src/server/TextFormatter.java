package server;

public class TextFormatter {

    public static final String TEXT_RESET_CODE = "\u001B[0m";
    public static final String TEXT_RED_CODE = "\u001B[31m";
    public static final String TEXT_YELLOW_CODE = "\u001B[33m";

    public static String makeTextRed(String text) {
        return TEXT_RED_CODE + text + TEXT_RESET_CODE;
    }

    public static String makeTextYellow(String text) {
        return TEXT_YELLOW_CODE + text + TEXT_RESET_CODE;
    }

    public static void printlnRed(String text) {
        System.out.println(makeTextRed(text));
    }

    public static void printlnYellow(String text) {
        System.out.println(makeTextYellow(text));
    }

    public static void printErrorMessage(String errorMessage) {
        printlnRed("Error: " + errorMessage + "!");
    }

}
