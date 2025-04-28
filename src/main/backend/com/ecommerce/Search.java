package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public static List<Product> searchByName(List<Product> products, String query) {
        List<Product> result = new ArrayList<>();
        if (products == null) {
            return result;
        }

        String lowerQuery = query.toLowerCase();

        for (Product p : products) {
            if (p.getName().toLowerCase().contains(lowerQuery)) {
                result.add(p);
            }
        }
        return result;
    }
}
