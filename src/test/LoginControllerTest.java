import com.ecommerce.LoginController;
import org.junit.jupiter.api.Test;
import com.ecommerce.Customer;

import static org.junit.jupiter.api.Assertions.*;
public class LoginControllerTest {
    @Test
        public void testIsInputValid() {
            LoginController controller = new LoginController();
            //Valid with username and password
            assertTrue(controller.isInputValid("user", "pass"));
            //Invalid with no username
            assertFalse(controller.isInputValid("", "pass"));
            //Invalid with no password
            assertFalse(controller.isInputValid("user", ""));
            //Invalid with neither
            assertFalse(controller.isInputValid("", ""));
        }
        @Test
    void testGetUsernameFromAccountId() {
        //Create a customer
        Customer customer = new Customer("user", "password123", "acc123", false);
        LoginController.accounts.put("user", customer);

        //Test correct username
        String result = LoginController.getUsernameFromAccountId("acc123");
        assertEquals("user", result);

        //Test incorrect username
        result = LoginController.getUsernameFromAccountId("nonExistentId");
        assertEquals("Unknown", result);
    }

}

