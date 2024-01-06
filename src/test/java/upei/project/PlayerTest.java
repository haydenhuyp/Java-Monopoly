package upei.project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PlayerTest {
    /**
     * Test the player properties like isInPrison and hasFreeFromPrisonCard
     */
    @Test
    public void testPlayerProperties() {
        Player player = new Player("Player 1");
        assertFalse(player.isInPrison());
        assertFalse(player.isHasFreeFromPrisonCard());
        assertEquals(1500, player.getBalance());
    }

    /**
     * Test the balance of the player
     * They should have 2000 after setting the balance to 2000
     */
    @Test
    public void PlayerBalanceTest(){
        Player player = new Player("Player 1");
        player.setBalance(2000);
        assertEquals(2000, player.getBalance());
    }
}
