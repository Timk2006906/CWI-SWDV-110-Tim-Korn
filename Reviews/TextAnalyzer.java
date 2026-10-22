
import java.util.Scanner;

public class TextAnalyzer {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Read a sentence from the user
        System.out.print("Enter a sentence: ");
        String sentence = input.nextLine();

        // Set up counters
        int wordCount = 0;
        int letterCount = 0;
        int digitCount = 0;
        int vowelCount = 0;

        // Check each character
        for (int i = 0; i < sentence.length(); i++) {
            char currentChar = sentence.charAt(i);
            char lowerChar = Character.toLowerCase(currentChar);

            if (Character.isLetter(currentChar)) {
                letterCount++;
            }

            if (Character.isDigit(currentChar)) {
                digitCount++;
            }

            if (lowerChar == 'a' || lowerChar == 'e'
                    || lowerChar == 'i' || lowerChar == 'o'
                    || lowerChar == 'u') {
                vowelCount++;
            }
        }

        // Display the results
        System.out.println("Letters: " + letterCount);
        System.out.println("Digits: " + digitCount);
        System.out.println("Vowels: " + vowelCount);

        // TODO: Count the words
        // TODO: Find the longest word
        // TODO: Print the sentence in title case
        // TODO: Print words in reverse order using StringBuilder
        // TODO: Print a 40-character border using repeat()

        input.close();
    }
}
