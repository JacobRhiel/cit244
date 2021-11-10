import java.util.Scanner;

/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Oct 04, 2021
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        char[] binary;
        char[] hexStr = { 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] hexNumber = new char[1000];

        int number, endIndex = 0, length = 0, digitIndex, currentIndex;

        System.out.printf("%s", "Enter a binary number: ");

        binary = scanner.next().toCharArray();

        while(endIndex < binary.length) {
            number = 0;
            digitIndex = 0;

            for (currentIndex = endIndex + 3; currentIndex >= endIndex; currentIndex--) {
                if(binary[currentIndex] == '1')
                    number += Math.pow(2, digitIndex);
                digitIndex++;
            }
            hexNumber[length++] = number > 9 ? hexStr[number - 10] : (char) ('0' + number);
            endIndex += 4;
        }
        System.out.printf("%s\n", String.valueOf(hexNumber));
    }
}
