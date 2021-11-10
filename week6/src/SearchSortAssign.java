/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Nov 07, 2021
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class SearchSortAssign {

    private static final File file = new File("somanynumbers.txt");

    public static void main(String[] args) {

        // START HERE
        boolean fileExists = Files.exists(file.toPath());
        if(!fileExists) createFile();
        try {
            int[] readIntegers = loadFile();
            AllSorts sorts = new AllSorts();
            AllSearch search = new AllSearch();

            long startTimeMS = System.currentTimeMillis();

            // fastest sort
            // merge sort - Divide and conquer, worst-case time complexity is the same as best.
            // The most calls in this method will be the n number or length of the array.
            int[] sorted = sorts.mergeSort(readIntegers);
            // binary is binary, depending on circumstances it will always be faster.
            // the arr. is sorted, making it even faster, linear is next very close, but less efficient
            // as it requires an extra check when processing.
            int foundNumberIndex = search.jumpSearch(sorted, 44);

            // slowest sort
            // bubble is the most basic search algo. Compares each adj. pair to check if the elements are
            // in order, then swaps them. This turns into a doubly-sized time complexity of n^2.
            // slowest search
            // jump search, although filters, requires checking all entries for a base value
            // to reference when searching. IE: slow.
            /*int[] sorted = sorts.bubbleSort(readIntegers);
            int foundNumberIndex = search.jumpSearch(sorted, 44);*/

            System.out.println("Found number at index: " + foundNumberIndex);

            long endTimeMS = System.currentTimeMillis();
            long totalTimeMS = (endTimeMS - startTimeMS);
            boolean isSeconds = totalTimeMS > 1000;

            System.out.println("Total search time took: " + (
                    isSeconds ? Duration.ofMillis(totalTimeMS).getSeconds()
                            : totalTimeMS
                    ) + (isSeconds ? "seconds." : "ms."));
        } catch(IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }

    // Method which generates text file containing 1 million random numbers
    public static void createFile()
    {
        // File to be created in default directory
        Random rng = new Random();
        int r;

        try {
            // Create the file
            PrintWriter output = new PrintWriter(file);

            // Write random numbers into a file
            for (int i = 0; i < 1000000; i++)
            {
                // Genereates a random number in range of (0 - 100)
                r = rng.nextInt(101);
                output.write(r + ", ");
            }
            System.out.println("File created.");
            // Close file
            output.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Cannot do that.");
        }
    }

    private static int[] loadFile() throws IOException, NumberFormatException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        String value = new String(bytes, StandardCharsets.UTF_8);
        return Arrays.stream(value.split(", "))
                .flatMapToInt(num -> IntStream.of(Integer.parseInt(num)))
                .toArray();
    }
}