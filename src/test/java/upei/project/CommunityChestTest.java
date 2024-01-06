package upei.project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CommunityChestTest {
    /**
     * Test the CommunityChest constructor
     */
    @Test
    public void testCommunityChestConstructor(){
        CommunityChest communityChest = new CommunityChest("Community Chest");
        assertEquals(communityChest.getName(), "Community Chest", "Test CommunityChest Constructor: Expected: Community Chest, Received: "+communityChest.getName());
    }

    /**
     * Test the drawCard method, the card returned should not be null
     */
    @Test
    public void drawCardTest(){
        CommunityChest communityChest = new CommunityChest("Community Chest");
        CommunityCard card = communityChest.drawCard();
        assertNotNull(card, "Test drawCard: Expected: true, Received: false");
    }
}
