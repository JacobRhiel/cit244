/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Sep 13, 2021
 */
public class Family {

    private final String name, relation;

    public Family(String name, String relation) {
        this.name = name;
        this.relation = relation;
    }

    @Override
    public String toString() {
        return StringUtils.ANSI_YELLOW + name + StringUtils.ANSI_RESET
                + " is part of your family, the relationship is "
                + StringUtils.ANSI_CYAN + relation + StringUtils.ANSI_RESET
                + ".";
    }
}
