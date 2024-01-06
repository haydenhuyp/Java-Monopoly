package upei.project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TaxTest {
    @Test
    public void testTax() {
        Tax tax = new Tax("Tax", 100);
        assertEquals("Tax", tax.getName());
        assertEquals(100, tax.getTaxAmount());
    }
}
