import java.util.Scanner;

/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Sep 13, 2021
 */
public class FriendsAndFamily {

    private static final String ASCII_ARROW = StringUtils.ANSI_CYAN + "--> " + StringUtils.ANSI_RESET;
    
    public static void main(String[] args) {
        // since I am using Intellij (and don't really want to change this setting), \n is the built-in consoles
        // 'lineSeparator(), using System.lineSeparator() is the appropriate usage.
        // The whole purpose of this change is me allowing spaces in the scanner response.
        // When you hit enter on a print() the console will execute the System's
        // lineSeparator. This was not accounting for the multiple words in a response, and nextLine() does not work
        // with print() and my regex validation (unless I make it overly complex) and regex is memory intensive as it is.
        Scanner scanner = new Scanner(System.in).useDelimiter("\n"); // System.lineSeparator()
        Object[] friendsAndFamily;
        Friend friend;
        Family family;
        String name;
        String relation;
        int timeKnown;
        byte type;

        System.out.println("("
                + StringUtils.ANSI_RED + "Warning: " + StringUtils.ANSI_RESET
                + "When prompted for choice of "
                + StringUtils.ANSI_CYAN + "\"friend\" " + StringUtils.ANSI_RESET
                + "or "
                + StringUtils.ANSI_CYAN + "\"family\"" + StringUtils.ANSI_RESET
                + ", anything other than friend or family will end the program.)");

        friendsAndFamily = getSize(scanner);

        for (int index = 0; index < friendsAndFamily.length; index++) {
            System.out.print("\nIs this a friend or family member? "
                    + StringUtils.ANSI_YELLOW + "(case insensitive) " + StringUtils.ANSI_RESET
                    + ASCII_ARROW);
            // if input is invalid or type != 0 || != 1 it closes, safe call for getName instead of inside the if/else
            type = friendOrFamily(scanner);
            name = getName(scanner, type);
            if (type == 0) {
                timeKnown = getTimeKnown(scanner, name);
                friend = new Friend(name, timeKnown);
                friendsAndFamily[index] = friend;
            } else if (type == 1) {
                relation = getRelation(scanner, name);
                family = new Family(name, relation);
                friendsAndFamily[index] = family;
            }
        }
        display(friendsAndFamily);
    }

    /**
     * Gets the size of members to be entered and instantiates a new {@link Object[]}.
     * @param scanner The scanner object.
     * @return A new {@link Object[]}.
     */
    private static Object[] getSize(Scanner scanner) {
        Object[] arr;
        byte size = 0;
        while(size < 1) {
            System.out.print("\nHow many friends & family do you wish to enter? " + ASCII_ARROW);
            // I use byte since the likelihood of them entering a number > 127 is minimal.
            // next up would be a short to save on allocating more memory.
            boolean hasByteValue = scanner.hasNextByte();
            if (!hasByteValue) {
                // no return, subsequent insns are effectively unreachable.
                closeProgram();
            }
            size = scanner.nextByte();
            if(size <= 0) {
                System.out.println("[" + StringUtils.ANSI_RED + "Invalid entry: " + StringUtils.ANSI_YELLOW + size
                        + StringUtils.ANSI_RESET + "] - Please enter a positive size greater than "
                        + StringUtils.ANSI_CYAN + "0 " + StringUtils.ANSI_RESET + ".");
            }
        }
        arr = new Object[size];
        return arr;
    }

    /**
     * @apiNote byte is used as a representation of memory enhancement, it is not required and it's
     * purpose is to show understanding of memory optimization standards.
     *
     * Gets whether the entry is a family member or a friend.
     * @param scanner The scanner object.
     * @return The type opcode that represents the type of entry.
     */
    private static byte friendOrFamily(Scanner scanner) {
        byte type = 2;
        boolean hasAlphaString = scanner.hasNext("[a-zA-Z]*$");
        if (!hasAlphaString) {
            // no return, subsequent insns are effectively unreachable.
            closeProgram();
        }
        // this is showing off the use of switch.
        // generally speaking 2 options should be an if-else
        // same goes for enums
        switch (scanner.next().toLowerCase()) {
            case "friend":
                type = 0;
                break;
            case "family":
                type = 1;
                break;
            default:
                // no return, subsequent insns are effectively unreachable.
                closeProgram();
                break;
        }
        return type;
    }



    /**
     * Gets the name of the family member or friend based on input.
     * @param scanner The scanner object.
     * @param type The type of member input.
     * @return A string representation of their name, in uppercase.
     */
    private static String getName(Scanner scanner, int type) {
        String name;
        if (type == 0) {
            System.out.print("\nEnter the name of this friend " + ASCII_ARROW);
        } else if (type == 1) {
            System.out.print("\nEnter the name of this family member " + ASCII_ARROW);
        }
        validateAlphaString(scanner);
        name = scanner.next();
        boolean isMultiPart = name.split(" ").length > 0;
        return isMultiPart ? StringUtils.capitalizeAll(" ", name) : StringUtils.capitalize(name);
    }
    
    /**
     * Gets the relation to a family member based on input.
     * @param scanner The scanner object.
     * @param name The name of the family members.
     * @return A string representation of the relationship, in uppercase.
     */
    private static String getRelation(Scanner scanner, String name) {
        String relation;
        String formattedName = StringUtils.capitalize(name);
        System.out.print("\nEnter your relation to " + formattedName + " " + ASCII_ARROW);
        validateAlphaString(scanner);
        relation = scanner.next();
        boolean isMultiPart = relation.split(" ").length > 0;
        return isMultiPart ? StringUtils.capitalizeAll(" ", relation) : StringUtils.capitalize(relation);
    }

    /**
     * @apiNote byte is used as a representation of memory enhancement, it is not required and it's
     * purpose is to show understanding of memory optimization standards.
     *
     * Gets the time the user has known a friend.
     * @param scanner The scanner object.
     * @param name The name of the friend.
     * @return a byte representing the known time.
     */
    private static byte getTimeKnown(Scanner scanner, String name) {
        String formattedName = StringUtils.capitalize(name);
        System.out.print("\nHow long have you known " + formattedName + "? "
                + StringUtils.ANSI_YELLOW + "(in years) " + StringUtils.ANSI_RESET
                + ASCII_ARROW);
        boolean isDigit = scanner.hasNextByte();
        if (!isDigit) {
            closeProgram();
        }
        // 127 years is self explanatory here.
        // I'd rather do logic here for handling negative case instead of the constructor of Friend.
        return scanner.nextByte();
    }

    /**
     * Displays friend and family members with an enhanced for loop.
     * @param friendsAndFamily The friendsAndFamily.
     */
    private static void display(Object[] friendsAndFamily) {
        System.out.println("\n");
        for (Object o : friendsAndFamily) {
            System.out.println(o);
        }
    }

    /**
     * Validation check to see if the next input matches regex for Alpha ASCII inclusive characters.
     * @param scanner The scanner object.
     */
    private static void validateAlphaString(Scanner scanner) {
        boolean hasAlphaString = scanner.hasNext("[\\p{Alpha} .'-]+");
        if (!hasAlphaString) {
            // no return, subsequent insns are effectively unreachable.
            closeProgram();
        }
    }
    
    /**
     * Terminates the program.
     */
    private static void closeProgram() {
        System.out.println("Invalid input.. closing program.");
        System.exit(0);
    }
}
