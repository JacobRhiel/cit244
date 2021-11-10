import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Oct 17, 2021
 */
public class CaesarCipherRhiel {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("CaesarCipherRhiel.txt"))) {
            StringBuilder builder = new StringBuilder("Decrypted passwords: \n");
            List<String> lines = reader.lines().collect(Collectors.toList());
            int lineCount = lines.size();
            for (String line : lines) {
                builder.append("\t")
                        .append(ANSI_CYAN)
                        .append("--> ")
                        .append(ANSI_RESET)
                        .append(decrypt(line, 5));
                lineCount--;
                if (lineCount != 0)
                    builder.append("\n");
            }
            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Decrypts a given password with a shift (offset).
     *
     * @param password The password to decrypt.
     * @param shift    The shift (offset) to algorithmically check against.
     * @return The decrypted string.
     */
    private static String decrypt(String password, int shift) {
        char[] validChars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int pos = 0;
        char currentChar;
        int sub;

        // return, it won't parse and most likely cause an exception to be thrown.
        if (password == null || password.isEmpty()) return password;

        StringBuilder decryptedPassword = new StringBuilder();

        for (; pos < password.length(); pos++) {
            currentChar = password.charAt(pos);
            int alphabetCharPos = Arrays.binarySearch(validChars, currentChar);
            int difference = alphabetCharPos - shift;
            // - 1, we are exclusive of 0 in this instance, ~ is compliment operator to flip the negative number.
            sub = difference < 0 ? (validChars.length - 1) - ~difference : difference;
            char decryptedChar = validChars[sub];
            decryptedPassword.append(decryptedChar);
        }
        return decryptedPassword.toString();
    }

}
