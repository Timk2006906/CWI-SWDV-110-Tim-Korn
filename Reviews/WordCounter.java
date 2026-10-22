import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
public class WordCounter {
public static void main (String[] args ){
HashMap<String, Integer> wordCounts  = new HashMap<>();
Scanner scanner = new Scanner(System.in);

System.out.println("Enter a paragraph:");
String sentence = scanner.nextLine();

sentence = sentence.toLowerCase();
sentence = sentence.replaceAll("[^a-z ]", "");
String[] words = sentence.trim().split("\\s+");

for (String word : words) {
    if (!wordCounts.containsKey(word)) {
        wordCounts.put(word, 1);
    
 
    }
}
}
}

