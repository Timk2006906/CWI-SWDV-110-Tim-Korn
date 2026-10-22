import java.util.ArrayList;
import java.util.Collections;
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
            System.out.println("6. Sort Tasks (stretch)");
            System.out.println("7. Move Task (stretch)");
            String sentence = scanner.nextLine();

            if (sentence.equals("1")) {
                System.out.println("You selected Add Task!");
                System.out.println("Enter a task:");
                String task = scanner.nextLine();
                tasks.add(task);

            } else if (sentence.equals("2")) {
                System.out.println("You selected Remove Task!");
                removeTask(tasks, scanner, "Removed");

            } else if (sentence.equals("3")) {
                System.out.println("You selected List Tasks!");
                if (tasks.isEmpty()) {
                    System.out.println("Your list is empty!");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }

            } else if (sentence.equals("4")) {
                System.out.println("You selected Done!");
                removeTask(tasks, scanner, "Nice work! Completed");

            } else if (sentence.equals("5")) {
                running = false;
                System.out.println("Goodbye!");

            } else if (sentence.equals("6")) {
                Collections.sort(tasks);
                System.out.println("Tasks sorted!");

            } else if (sentence.equals("7")) {
                System.out.println("Enter the task number to move:");
                String fromInput = scanner.nextLine();
                System.out.println("Enter the new position:");
                String toInput = scanner.nextLine();
                try {
                    int from = Integer.parseInt(fromInput);
                    int to = Integer.parseInt(toInput);
                    if (from >= 1 && from <= tasks.size() && to >= 1 && to <= tasks.size()) {
                        move(tasks, from - 1, to - 1);
                        System.out.println("Moved task " + from + " to position " + to + ".");
                    } else {
                        System.out.println("Oops! Pick numbers between 1 and " + tasks.size() + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Oops! That's not a number.");
                }

            } else {
                System.out.println("Please choose a number from the menu.");
            }

            System.out.println();
        }
    }

    public static void removeTask(ArrayList<String> tasks, Scanner scanner, String verb) {
        System.out.println("Enter the task number:");
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number >= 1 && number <= tasks.size()) {
                String removed = tasks.remove(number - 1);
                System.out.println(verb + ": " + removed);
            } else {
                System.out.println("Oops! Pick a number between 1 and " + tasks.size() + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Oops! That's not a number.");
        }
    }

    public static void move(ArrayList<String> tasks, int indexToMoveFrom, int indexToMoveTo) {
        String task = tasks.remove(indexToMoveFrom);
        tasks.add(indexToMoveTo, task);
    }
}

