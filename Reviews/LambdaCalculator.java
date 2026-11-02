import java.util.HashMap;
import java.util.Map;
public class LambdaCalculator {

    public static void main(String[] args) {

        MathOperation subtract = (a, b) -> a - b;

        System.out.println(subtract.apply(10, 3));
            Map<String, MathOperation> operations = new HashMap<>();

    operations.put("+", (a, b) -> a + b);
    operations.put("-", (a, b) -> a - b);
    operations.put("*", (a, b) -> a * b);

System.out.println(operations.get("+").apply(10, 5));
System.out.println(operations.get("-").apply(10, 5));
System.out.println(operations.get("*").apply(10, 5));
    }
}