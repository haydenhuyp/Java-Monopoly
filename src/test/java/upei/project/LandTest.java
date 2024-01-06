package upei.project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LandTest {
    /**
     * Test the Land constructor
     */
    @Test
    public void testLandConstructor(){
        Land newYork = new Land("New York", 100, 10, (byte) 1);
        assertEquals(newYork.getName(), "New York", "Test Land Constructor: Expected: New York, Received: "+newYork.getName());
        assertEquals(newYork.getPrice(), 100, "Test Land Constructor: Expected: 100, Received: "+newYork.getPrice());
        assertEquals(newYork.getRent(), 10, "Test Land Constructor: Expected: 10, Received: "+newYork.getRent());
        assertNull(newYork.getOwner(), "Test Land Constructor: Expected: null, Received: " + newYork.getOwner());
        assertEquals(newYork.getColorGroup(), 1, "Test Land Constructor: Expected: 1, Received: "+newYork.getColorGroup());
    }

    /**
     * Test the Land payRent method, player should have 1490 after paying rent
     */
    @Test
    public void testPayRent_PlayerPaysRent_PlayerBalanceDeducted(){
        Land newYork = new Land("New York", 100, 10, (byte) 1);
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        newYork.setOwner(player2);
        newYork.payRent(player1);
        assertEquals(player1.getBalance(), 1490, "Test Land payRent: Expected: 1490, Received: "+player1.getBalance());
    }

    /**
     * Test the Land sell method, owner should be null and player balance should increase
     */
    @Test
    public void testSell_PlayerSells_OwnerIsNullAndPlayerBalanceIncreases(){
        Land newYork = new Land("New York", 100, 10, (byte) 1);
        Player player1 = new Player("Player 1");
        newYork.setOwner(player1);
        newYork.sell();
        assertNull(newYork.getOwner(), "Test Land sell: Expected: null, Received: "+newYork.getOwner());
        assertEquals(player1.getBalance(), 1550, "Test Land sell: Expected: 1550, Received: "+player1.getBalance());
    }

    /**
     * Test the Land buy method, owner should be player and player balance should decrease
     */
    @Test
    public void testBuy_PlayerBuys_OwnerIsPlayerAndPlayerBalanceDecreases(){
        Land newYork = new Land("New York", 100, 10, (byte) 1);
        Player player1 = new Player("Player 1");
        newYork.buy(player1);
        assertEquals(newYork.getOwner(), player1, "Test Land buy: Expected: Player 1, Received: "+newYork.getOwner());
        assertEquals(player1.getBalance(), 1400, "Test Land buy: Expected: 1400, Received: "+player1.getBalance());
    }
}
