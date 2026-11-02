import java.util.ArrayList;
import java.util.List;

public class StudentRoster {
    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();
        students.add(new Student("Alex", 3.5, "Computer Science"));
        students.add(new Student("Jordan", 3.8, "Business"));
        students.add(new Student("frank", 1.8,  "Humanities"));
        students.add(new Student("Sam", 2.5, "Art"));
        students.add(new Student("Tim", 4.0, "Software development"));
        students.add(new Student("Bruce", 4.0, "Batman"));
        students.add(new Student("Madison", 3.0,"Entrepreneurship"));
        students.add(new Student("Isabella", 3.7, "Healthcare"));
    }
}