package upei.project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChanceChestTest {
    /**
     * Test the ChanceChest constructor
     */
    @Test
    public void testChanceChestConstructor(){
        ChanceChest chanceChest = new ChanceChest("Chance Chest");
        assertEquals(chanceChest.getName(), "Chance Chest", "Test ChanceChest Constructor: Expected: Chance Chest, Received: "+chanceChest.getName());
    }

    /**
     * Test the drawCard method, the card returned should not be null
     */
    @Test
    public void drawCardTest(){
        ChanceChest chanceChest = new ChanceChest("Chance Chest");
        ChanceCard card = chanceChest.drawCard();
        assertNotNull(card, "Test drawCard: Expected: true, Received: false");
    }
}
