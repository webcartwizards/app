import com.ecommerce.Cart;
import com.ecommerce.CartItem;
import com.ecommerce.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CartTest {

    private Cart cart;

    @BeforeEach
    void setup() {
        cart = Cart.getInstance();
        cart.getCartItems().clear(); // Clear cart before each test
    }

    @Test
    void AddNewItem() {
        Product prod = new Product("Shirt", 20.00, "Clothing");
        cart.addToCart(prod, 1);

        List<CartItem> items = cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals("Shirt", items.get(0).getProd().getName());
        assertEquals(1, items.get(0).getQuantity());
    }

    @Test
    void AddSameItem() {
        Product prod = new Product("Jeans", 40.00, "Clothing");
        cart.addToCart(prod, 1);
        cart.addToCart(prod, 2); // Should merge

        List<CartItem> items = cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals(3, items.get(0).getQuantity());
    }

    @Test
    void AddDifferentItems() {
        Product prod1 = new Product("Shoes", 60.00, "Shoes");
        Product prod2 = new Product("Hat",15.00, "Accessory");

        cart.addToCart(prod1, 1);
        cart.addToCart(prod2, 2);

        List<CartItem> items = cart.getCartItems();
        assertEquals(2, items.size());
    }
}