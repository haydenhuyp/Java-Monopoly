package upei.project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {
    /**
     * Test the constructor of the Board class
     * There should be 3 players in the game, expected 3
     */
    @Test
    public void testBoardConstructor_expected3(){
        Board myBoard = new Board();
        assertEquals(myBoard.getPlayersOrder().getSize(), 3, "Test Constructor: Expected: 3, Received: "+myBoard.getPlayersOrder().getSize());
    }

    @Test
    public void testRemovePlayer(){
        Board myBoard = new Board();
        Player player2 = new Player("Player 2");
        myBoard.getPlayersOrder().remove(player2);
        assertEquals(myBoard.getPlayersOrder().getSize(), 2, "Test Remove Player: Expected: 2, Received: "+ myBoard.getPlayersOrder().getSize());
    }

    /**
     * Test if the players are in the correct order
     * Player 1 should be first, Player 2 should be second, Player 3 should be third
     */
    @Test
    public void testPlayersOrder_BoardInitialized_Player1Player2Player3(){
        Board myBoard = new Board();
        assertEquals(myBoard.getPlayersOrder().get(0).getName(), "Player 1", "Test Players Order: Expected: Player 1, Received: "+ myBoard.getPlayersOrder().get(0).getName());
        assertEquals(myBoard.getPlayersOrder().get(1).getName(), "Player 2", "Test Players Order: Expected: Player 2, Received: "+ myBoard.getPlayersOrder().get(1).getName());
        assertEquals(myBoard.getPlayersOrder().get(2).getName(), "Player 3", "Test Players Order: Expected: Player 3, Received: "+ myBoard.getPlayersOrder().get(2).getName());
    }

    /**
     * Test if the players' balance is correct, should be 1500
     */
    @Test
    public void testPlayersBalance_BoardInitialized_1500(){
        Board myBoard = new Board();
        assertEquals(myBoard.getPlayersOrder().get(0).getBalance(), 1500, "Test Players Balance: Expected: 1500, Received: "+ myBoard.getPlayersOrder().get(0).getBalance());
        assertEquals(myBoard.getPlayersOrder().get(1).getBalance(), 1500, "Test Players Balance: Expected: 1500, Received: "+ myBoard.getPlayersOrder().get(1).getBalance());
        assertEquals(myBoard.getPlayersOrder().get(2).getBalance(), 1500, "Test Players Balance: Expected: 1500, Received: "+ myBoard.getPlayersOrder().get(2).getBalance());
    }

    /**
     * Test if there is one player left (the winner) after playStrategy1 is called
     */
    @Test
    public void testPlayStrategy1_onePlayerLeft(){
        Board myBoard = new Board();
        myBoard.playStrategy1();
        assertEquals(myBoard.getPlayersOrder().getSize(), 1, "Test Play Strategy 1: Expected: 1, Received: "+ myBoard.getPlayersOrder().getSize());
    }

    /**
     * Test if there is one player left (the winner) after playStrategy2 is called
     */
    @Test
    public void testPlayStrategy2_onePlayerLeft(){
        Board myBoard = new Board();
        myBoard.playStrategy2();
        assertEquals(myBoard.getPlayersOrder().getSize(), 1, "Test Play Strategy 2: Expected: 1, Received: "+ myBoard.getPlayersOrder().getSize());
    }

    /**
     * Test if there is one player left (the winner) after playStrategy3 is called
     */
    @Test
    public void testPlayStrategy3_onePlayerLeft(){
        Board myBoard = new Board();
        myBoard.playStrategy3();
        assertEquals(myBoard.getPlayersOrder().getSize(), 1, "Test Play Strategy 3: Expected: 1, Received: "+ myBoard.getPlayersOrder().getSize());
    }
}
