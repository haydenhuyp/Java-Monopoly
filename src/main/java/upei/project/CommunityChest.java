package upei.project;

import java.util.ArrayList;

public class CommunityChest extends Tile{
    private ArrayList<CommunityCard> communityChestCards;
    private ArrayList<CommunityCard> usedCommunityChestCards;
    public CommunityChest(String name) {
        super(name);

        // Create the community chest cards
        communityChestCards = new ArrayList<CommunityCard>();
        communityChestCards.add(new CommunityCard("Advance to Go", Action.MOVE, 0));
        communityChestCards.add(new CommunityCard("Bank error in your favor, collect $200", Action.PAY, 200));
        communityChestCards.add(new CommunityCard("Doctor's fee, pay $50", Action.PAY, 50));
        communityChestCards.add(new CommunityCard("From sale of stock you get $50", Action.PAY, 50));
        communityChestCards.add(new CommunityCard("Get out of jail free", Action.FREE_FROM_PRISON, 0));
        communityChestCards.add(new CommunityCard("Go to jail", Action.GO_TO_PRISON, 0));
        communityChestCards.add(new CommunityCard("Grand Opera Night, collect $50 from every player for opening night seats", Action.RECEIVE_FROM_ALL, 50));
        communityChestCards.add(new CommunityCard("Send holiday gifts to all the players, each will get $10", Action.PAY_TO_ALL, 10));

        // shuffle the communityChestCards arraylist
        java.util.Collections.shuffle(communityChestCards);

        // initialize the usedCommunityChestCards arraylist
        usedCommunityChestCards = new ArrayList<CommunityCard>();
    }

    /**
     * Get the next card from the deck
     * @return the next card
     */
    public CommunityCard drawCard() {
        CommunityCard card = communityChestCards.get(0);
        communityChestCards.remove(0);
        usedCommunityChestCards.add(card);
        if (communityChestCards.isEmpty()) {
            communityChestCards = usedCommunityChestCards;
            usedCommunityChestCards = new ArrayList<CommunityCard>();
            java.util.Collections.shuffle(communityChestCards);
        }
        return card;
    }
}
