import java.util.ArrayList;
import java.util.Scanner;

public class ListManager {
 public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
ArrayList<String> tasks = new ArrayList<>();

boolean running = true;

while (running) {
System.out.println("To-Do List Manager");
System.out.println("1. Add Task");
System.out.println("2. Remove Task");
System.out.println("3. List Tasks");
System.out.println("4. Done");
System.out.println("5. Quit");
String sentence = scanner.nextLine();
if (sentence.equals("1")) {
    System.out.println("You selected Add Task!");
    System.out.println("Enter a task:");
    String task = scanner.nextLine();

} else if (sentence.equals("2")) {
    System.out.println("You selected Remove Task!");

} else if (sentence.equals("3")) {
    System.out.println("You selected List Tasks!");

} else if (sentence.equals("4")) {
System.out.println("Done!");

} else if (sentence.equals("5")) { running = false; System.out.println("Goodbye!"); }
}
}
}


