import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double num1 = 0;
        double num2 = 0;
        boolean validInput = false;

        // Keep asking until both values convert successfully
        while (!validInput) {
            try {
                System.out.print("Enter the first number: ");
                String input1 = scanner.nextLine();
                num1 = Double.parseDouble(input1);

                System.out.print("Enter the second number: ");
                String input2 = scanner.nextLine();
                num2 = Double.parseDouble(input2);

                validInput = true; // only reached if both conversions succeed
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: please enter valid numbers (e.g. 3, 4.5, 2.0). Try again.\n");
            }
        }

        // Perform the four operations
        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;
        double quotient;

        if (num2 != 0) {
            quotient = num1 / num2;
        } else {
            System.out.println("Warning: division by zero, storing result as 0.");
            quotient = 0;
        }

        // Store results in an ArrayList
        ArrayList<Double> results = new ArrayList<>();
        results.add(sum);
        results.add(difference);
        results.add(product);
        results.add(quotient);

        // Labels so the output/file is readable
        String[] labels = { "Addition", "Subtraction", "Multiplication", "Division" };

       
        System.out.println("\n--- Results ---");
        for (int i = 0; i < results.size(); i++) {
            System.out.println(labels[i] + ": " + results.get(i));
        }

        try (FileWriter writer = new FileWriter("transactions.txt")) {
            writer.write("Calculator Transactions\n");
            writer.write("Num1 = " + num1 + ", Num2 = " + num2 + "\n");
            for (int i = 0; i < results.size(); i++) {
                writer.write(labels[i] + ": " + results.get(i) + "\n");
            }
            System.out.println("\nResults saved to transactions.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        scanner.close();
    }
}