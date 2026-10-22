
import java.util.Scanner;

public class NameEmailParser {
    public static void main(String[] args) {
        Scanner signIn = new Scanner(System.in);

        System.out.print("Enter your full name: ");
        String fullName = signIn.nextLine();

        // Find the first space in the name
        int spacePosition = fullName.indexOf(" ");

        // Get the first name
        String firstName = fullName.substring(0, spacePosition);

        // Display the first name
        System.out.println("First name: " + firstName);

        // Find the next part of the name here


        // Display the last name here


        signIn.close();
    }
}

