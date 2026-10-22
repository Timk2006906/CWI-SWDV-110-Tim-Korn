import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;
public class TicketQueue{
public static void main (String [] args){
 boolean running = true;
Scanner scanner = new Scanner(System.in);
Deque<String> tickets = new ArrayDeque<>();
Deque<String> resolved = new ArrayDeque<>();
while (running) {
    System.out.println("Help-Desk Ticket Queue");
    System.out.println("1. New Ticket");
    System.out.println("2. Next Ticket");
    System.out.println("3. Peek Ticket");
    System.out.println("4. Count Tickets");
    System.out.println("5. Undo");
    System.out.println("6. Quit");
    String choice = scanner.nextLine();
    if (choice.equals("1")) {
    System.out.println("You selected New Ticket!");
    
} else if (choice.equals("2")){
    System.out.println("You selected next ticket");

} else if (choice.equals("3")){
    System.out.println("You selected Peek ticket");

} else if (choice.equals("4")){
    System.out.println("You selected Count tickets");

} else if (choice.equals("5")){
    System.out.println("Undo not working yet");

} else if (choice.equals("6")) { running = false; System.out.println("Goodbye!"); }
} 
}
}

