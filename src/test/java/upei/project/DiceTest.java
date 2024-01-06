package upei.project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DiceTest {
    /**
     * Test the dice roll method, dice should roll between 2 and 12
     */
    @Test
    public void testDice(){
        Dices myDice = new Dices();
        assertTrue(myDice.roll()>=2 && myDice.roll()<=12, "Test Dice: Expected: 2-12, Received: "+ myDice.roll());
    }
}
