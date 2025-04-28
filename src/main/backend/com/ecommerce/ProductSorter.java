package com.ecommerce;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductSorter {
    public static void sortByName(List<Product> products) {
        Collections.sort(products, Comparator.comparing(Product::getName));
    }
    public static void sortByPrice(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
    }
    public static void sortByPriceDescending(List<Product> products) {
        Collections.sort(products, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
    }
}
