import java.util.ArrayList;
import java.util.List;

record Sale(String product, String region, int qty, double price) {}

public class SalesReport {
    public static void main(String[] args) {
        List<Sale> sales = new ArrayList<>();
    sales.add(new Sale("FunkoPops", "NorthAmerica", 150, 15.00));
    sale.add(new Sale("LegoSet", "North America", 1000, 25.99));
    sale.add(new Sale("Keyboards","South America", 140, 69.99));
    sale.add(new Sale("EldenRingVideoGame", "Asia",25000, 70));
    sale.add(new Sale("Monitors", "Europe", 2500, 160.99));
    sale.add(new Sale("Coffee", "Africa", 15000, 5.99));
    }
}