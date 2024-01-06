package upei.project;

import java.util.ArrayList;

public class ChanceChest extends Tile{
    private ArrayList<ChanceCard> chanceCards;
    private ArrayList<ChanceCard> usedCards;
    public ChanceChest(String name) {
        super(name);
        // populate the chanceCards arraylist
        chanceCards = new ArrayList<ChanceCard>();
        chanceCards.add(new ChanceCard("Advance to Go", Action.MOVE, 0));
        chanceCards.add(new ChanceCard("Go back 3 spaces", Action.MOVE, -3));
        chanceCards.add(new ChanceCard("Bank pays you dividend of $50", Action.PAY, 50));
        chanceCards.add(new ChanceCard("Pay poor tax of $15", Action.PAY, 15));
        chanceCards.add(new ChanceCard("Your building loan matures, collect $150", Action.PAY, 150));
        chanceCards.add(new ChanceCard("You have won a crossword competition, collect $100", Action.PAY, 100));

        // shuffle the chanceCards arraylist
        java.util.Collections.shuffle(chanceCards);

        // initialize the usedCards arraylist
        usedCards = new ArrayList<ChanceCard>();
    }

    /**
     * Get the next card from the deck
     * @return the next card
     */
    public ChanceCard drawCard() {
        ChanceCard card = chanceCards.get(0);
        chanceCards.remove(0);
        usedCards.add(card);
        if (chanceCards.isEmpty()) {
            chanceCards = usedCards;
            usedCards = new ArrayList<ChanceCard>();
            java.util.Collections.shuffle(chanceCards);
        }
        return card;
    }
}
