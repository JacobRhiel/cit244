/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Sep 13, 2021
 */
public class Friend {

    private final String name;
    private int timeKnown;

    public Friend(String name, int timeKnown) {
        this.name = name;
        this.timeKnown = validateTimeKnown(timeKnown);
    }

    public int validateTimeKnown(int timeKnown) {
        // Math.max returns 0 if - value or whatever was entered greater than 0.
        return this.timeKnown = Math.max(timeKnown, 0);
    }

    @Override
    public String toString() {
        String yearText = timeKnown > 1 ? "years" : "year";
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtils.ANSI_YELLOW)
                .append(name)
                .append(StringUtils.ANSI_RESET)
                .append(" and you have been friends for ");
        if (timeKnown == 0)
            builder.append("not very long.");
        else {
            builder.append(StringUtils.ANSI_CYAN)
                    .append(timeKnown)
                    .append(StringUtils.ANSI_RESET)
                    .append(" ")
                    .append(yearText)
                    .append(".");
        }
        return builder.toString();
    }
}
