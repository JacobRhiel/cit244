/**
 * Random String utilities.
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Sep 13, 2021
 */
public class StringUtils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Capitalizes each delimited word.
     *
     * @param delimiter The delimiter to split.
     * @param raw       The raw string literal.
     * @return The capitalized (and in this case color formatted) string.
     */
    public static String capitalizeAll(String delimiter, String raw) {
        String[] words = raw.split(delimiter);
        StringBuilder builder = new StringBuilder(ANSI_PURPLE);
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.matches("^\\p{Alpha}[\\p{Alpha}.'-]+\\p{Alpha}$")) {
                builder.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));
            }
            if (i != words.length - 1)
                builder.append(" ");
        }
        return builder.append(ANSI_RESET).toString();
    }

    /**
     * Capitalizes a single word.
     *
     * @param word The word.
     * @return The capitalized (and in this case formatted) string.
     */
    public static String capitalize(String word) {
        if (word.matches("^[A-Z]")) return word;
        return ANSI_PURPLE + word.substring(0, 1).toUpperCase() + word.substring(1) + ANSI_RESET;
    }
}
