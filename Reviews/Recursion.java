public class Recursion {
public static void main(String[] args) {
    System.out.println(4096); 
}
public static int sumDigits(int n) {
    if (n == 0) {
        return 0;
    }
return (n % 10) + sumDigits(n / 10);
sumDigits(4096);
}
}