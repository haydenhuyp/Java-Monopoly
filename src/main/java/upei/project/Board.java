package upei.project;

import java.util.ArrayList;

public class Board {
    private ArrayList<Tile> tiles;
    private CircularDoublyLinkedList<Player> playersOrder;
    private ArrayList<Integer> playersPosition;
    private Player currentPlayer;
    private Node<Player> currentPlayerPointer;
    private int currentPlayerIndex;
    private final Dices dices;
    private byte numberOfDoubleRolls = 0;
    private final int prisonIndex;

    public Board() {
        tiles = new ArrayList<Tile>();
        playersOrder = new CircularDoublyLinkedList<Player>();
        dices = new Dices();

        // add tiles to the board
        tiles.add(new Start());
        tiles.add(new Land("Brampton", 60, 10, (byte) 1));
        tiles.add(new CommunityChest("Community Chest"));
        tiles.add(new Land("Summerside", 65, 15, (byte) 1));
        tiles.add(new Tax("Tax", 100));
        tiles.add(new Utilities("Electricity", 200, 20));
        tiles.add(new Land("Windsor", 100, 25, (byte) 2));
        tiles.add(new ChanceChest("Chance Chest"));
        tiles.add(new Land("London", 110, 25, (byte) 2));
        tiles.add(new Land("Hamilton", 120, 30, (byte) 2));
        tiles.add(new Prison());
        tiles.add(new Land("Kitchener", 140, 35, (byte) 3));
        tiles.add(new Utilities("Water", 150, 40));
        tiles.add(new Land("Niagara Falls", 145, 40, (byte) 3));
        tiles.add(new Land("Ottawa", 160, 50, (byte) 3));
        tiles.add(new Utilities("Internet", 200, 45));
        tiles.add(new Land("Calgary", 180, 45, (byte) 4));
        tiles.add(new CommunityChest("Community Chest"));
        tiles.add(new Land("Quebec City", 180, 50, (byte) 4));
        tiles.add(new Land("Halifax", 200, 55, (byte) 4));
        tiles.add(new Parking("Parking"));
        tiles.add(new Land("Moncton", 220, 60, (byte) 5));
        tiles.add(new ChanceChest("Chance Chest"));
        tiles.add(new Land("Charlottetown", 220, 60, (byte) 5));
        tiles.add(new Land("Fredericton", 240, 65, (byte) 5));
        tiles.add(new Utilities("Gas", 200, 70));
        tiles.add(new Land("Markham", 260, 70, (byte) 6));
        tiles.add(new Land("Saskatoon", 260, 75, (byte) 6));
        tiles.add(new Utilities("Cable", 150, 80));
        tiles.add(new Land("Waterloo", 280, 75, (byte) 6));
        tiles.add(new GoToPrison());
        tiles.add(new Land("Regina", 300, 80, (byte) 7));
        tiles.add(new Land("Winnipeg", 300, 85, (byte) 7));
        tiles.add(new CommunityChest("Community Chest"));
        tiles.add(new Land("Vancouver", 320, 85, (byte) 7));
        tiles.add(new Utilities("Telephone", 200, 90));
        tiles.add(new ChanceChest("Chance Chest"));
        tiles.add(new Land("Montreal", 350, 90, (byte) 8));
        tiles.add(new Tax("Tax", 100));
        tiles.add(new Land("Toronto", 400, 95, (byte) 8));

        prisonIndex = findPrisonIndex();

        // add players to the board
        playersOrder.add(new Player("Player 1"));
        playersOrder.add(new Player("Player 2"));
        playersOrder.add(new Player("Player 3"));

        // Set the current player to the first player
        if (playersOrder.getSize() > 0) {
            currentPlayer = playersOrder.getHead().getData();
            currentPlayerIndex = 0;
            // Set the players position to 0
            playersPosition = new ArrayList<Integer>();
            for (int i = 0; i < playersOrder.getSize(); i++) {
                playersPosition.add(0);
            }
            currentPlayerPointer = playersOrder.getHead();
        } else {
            System.out.println("No players added to the board. Please add one or more players to the board.");
        }
    }

    /**
     * Find the Prison tile index
     * @return the index of the Prison tile
     */
    private int findPrisonIndex() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) instanceof Prison) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Strategy 1: The player will always buy the tile if it is purchasable and the player has enough money.
     */
    public void playStrategy1() {
        // get player from the circular doubly linked list
        int counter = 0;

        System.out.println("Game Starts!");
        while (counter<10000 && playersOrder.getSize() > 1) {
            byte sum = dices.roll();

            System.out.println(currentPlayer.getName() + " rolled " + sum + ".");
            if (currentPlayer.isInPrison()) {
                if (currentPlayer.isHasFreeFromPrisonCard()) {
                    currentPlayer.setHasFreeFromPrisonCard(false);
                    currentPlayer.setInPrison(false);
                    System.out.println(currentPlayer.getName() + " used a get out of jail free card.");
                } else {
                    if (dices.areTwoDicesEqual()) {
                        currentPlayer.setInPrison(false);
                        System.out.println(currentPlayer.getName() + " rolled a double and got out of jail.");
                    } else {
                        System.out.println(currentPlayer.getName() + " is still in jail.");
                        counter++;
                        currentPlayerPointer = currentPlayerPointer.getNext();
                        currentPlayer = currentPlayerPointer.getData();
                        currentPlayerIndex = (currentPlayerIndex + 1) % playersOrder.getSize();
                        continue;
                    }
                }
            }
            // move the player
            movePlayer(sum);
            System.out.println(currentPlayer.getName() + " moved to " + tiles.get(playersPosition.get(currentPlayerIndex)).getName() + ".");
            // get the tile
            Tile tile = tiles.get(playersPosition.get(currentPlayerIndex));
            // check if the tile is a purchasable tile
            if (tile instanceof PurchasableTile purchasableTile) {
                // check if the tile is owned
                if (purchasableTile.getOwner() == null) {
                    // check if the player has enough money to buy the tile
                    if (currentPlayer.getBalance() >= purchasableTile.getPrice()) {
                        // buy the tile
                        purchasableTile.buy(currentPlayer);
                        System.out.println(currentPlayer.getName() + " bought " + purchasableTile.getName() + " for " + purchasableTile.getPrice() + " dollars.");

                    } else {
                        System.out.println(currentPlayer.getName() + " does not have enough money to buy " + purchasableTile.getName() + ".");
                    }
                } else {
                    if (purchasableTile.getOwner() == currentPlayer) {
                        if (purchasableTile instanceof Land land) {
                            // check if the player exceeds the house limit
                            if (land.getHouseCount() > 4) {
                                System.out.println(currentPlayer.getName() + " cannot build more than 5 houses.");
                                continue;
                            } else {
                                // check if the player has enough money to buy a house or a hotel
                                if (currentPlayer.getBalance() >= land.getPriceList()[land.getHouseCount()]) {
                                    // buy a house or a hotel
                                    land.setHouseCount((byte) (land.getHouseCount() + 1));
                                    System.out.println(currentPlayer.getName() + " built a house at " + land.getName() + " for " + land.getPriceList()[land.getHouseCount() - 1] + " dollars.");
                                } else {
                                    System.out.println(currentPlayer.getName() + " does not have enough money to build a house.");
                                }
                            }

                        }
                    } else { // if the player is not the owner
                        // pay the rent if not the owner
                        purchasableTile.payRent(currentPlayer);
                        System.out.println(currentPlayer.getName() + " paid " + purchasableTile.getOwner().getName() + " " + purchasableTile.getRent() + " dollars.");
                    }
                }
            } else if (tile instanceof ChanceChest) {
                // draw a chance card
                ChanceCard card = ((ChanceChest) tile).drawCard();
                System.out.println(currentPlayer.getName() + " drew a chance card: " + card.getName());
                if (card.getAction() == Action.MOVE) {
                    // move the player
                    playersPosition.set(currentPlayerIndex, (playersPosition.get(currentPlayerIndex) + card.getParameter()) % tiles.size());
                } else if (card.getAction() == Action.PAY) {
                    // pay the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars.");
                }
            } else if (tile instanceof CommunityChest) {
                // draw a community chest card
                CommunityCard card = ((CommunityChest) tile).drawCard();
                System.out.println(currentPlayer.getName() + " drew a community chest card: " + card.getName());
                if (card.getAction() == Action.MOVE) {
                    // move the player
                    movePlayer((byte) card.getParameter());
                } else if (card.getAction() == Action.PAY) {
                    // pay the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars.");
                } else if (card.getAction() == Action.RECEIVE) {
                    // receive the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() + card.getParameter());
                    System.out.println(currentPlayer.getName() + " received " + card.getParameter() + " dollars.");
                } else if (card.getAction() == Action.RECEIVE_FROM_ALL) {
                    // receive the amount from all players
                    for (int i = 0; i < playersOrder.getSize(); i++) {
                        if (playersOrder.get(i) != currentPlayer) {
                            playersOrder.get(i).setBalance(playersOrder.get(i).getBalance() - card.getParameter());
                            currentPlayer.setBalance(currentPlayer.getBalance() + card.getParameter());
                        }
                    }
                    System.out.println(currentPlayer.getName() + " received " + card.getParameter() + " dollars from all players.");
                } else if (card.getAction() == Action.PAY_TO_ALL) {
                    // pay the amount to all players
                    for (int i = 0; i < playersOrder.getSize(); i++) {
                        if (playersOrder.get(i) != currentPlayer) {
                            playersOrder.get(i).setBalance(playersOrder.get(i).getBalance() + card.getParameter());
                            currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                        }
                    }
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars to all players.");
                } else if (card.getAction() == Action.FREE_FROM_PRISON) {
                    // set the player's freeFromPrison to true
                    currentPlayer.setHasFreeFromPrisonCard(true);
                    System.out.println(currentPlayer.getName() + " received a get out of jail free card.");
                } else if (card.getAction() == Action.GO_TO_PRISON) {
                    // move the player to the prison tile
                    moveToPrison(currentPlayer);
                    currentPlayer.setInPrison(true);
                    System.out.println(currentPlayer.getName() + " went to jail.");
                }

            } else if (tile instanceof Tax tax) {
                // pay the tax
                currentPlayer.setBalance(currentPlayer.getBalance() - tax.getTaxAmount());
                System.out.println(currentPlayer.getName() + " paid " + tax.getTaxAmount() + " dollars in tax.");
            } else if (tile instanceof Parking) {
                System.out.println(currentPlayer.getName() + " landed in the parking tile.");
            } else if (tile instanceof Start) {
                // add 200 to the player's balance
                currentPlayer.setBalance(currentPlayer.getBalance() + 200);
                System.out.println(currentPlayer.getName() + " received 200 dollars for visiting the start tile.");
            } else if (tile instanceof Prison) {
                System.out.println(currentPlayer.getName() + " landed in the prison tile.");
            } else if (tile instanceof GoToPrison) {
                // move the player to the prison tile
                moveToPrison(currentPlayer);
                currentPlayer.setInPrison(true);
                System.out.println(currentPlayer.getName() + " went to jail.");
            }

            if (currentPlayer.getBalance() < 0) {
                System.out.println(currentPlayer.getName() + "'s balance is below 0: " + currentPlayer.getBalance());
                int totalAssets = 0;
                for (int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i) instanceof PurchasableTile) {
                        PurchasableTile currentTile = (PurchasableTile) tiles.get(i);
                        if (currentTile.getOwner() == currentPlayer) {
                            totalAssets += currentTile.getPrice() / 2;
                        }
                    }
                }
                if (totalAssets >= Math.abs(currentPlayer.getBalance())) {
                    // sell until the player has enough money
                    while (currentPlayer.getBalance() < 0) {
                        for (int i = 0; i < tiles.size(); i++) {
                            if (tiles.get(i) instanceof PurchasableTile) {
                                PurchasableTile currentTile = (PurchasableTile) tiles.get(i);
                                if (currentTile.getOwner() == currentPlayer) {
                                    currentTile.sell();
                                    System.out.println(currentPlayer.getName() + " sold " + currentTile.getName() + " for " + currentTile.getPrice() / 2 + " dollars.");
                                    if (currentPlayer.getBalance() >= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println(currentPlayer.getName() + " is bankrupt and is out of the game.");
                    System.out.println(currentPlayer.getName() + " balance: " + currentPlayer.getBalance());

                    // remove the player from the playersOrder
                    playersOrder.remove(currentPlayer);
                    // remove the player from the playersPosition arraylist
                    playersPosition.remove(currentPlayerIndex);
                    // remove the player from the tiles
                    for (int i = 0; i < tiles.size(); i++) {
                        if (tiles.get(i) instanceof PurchasableTile currentTile) {
                            if (currentTile.getOwner() == currentPlayer) {
                                currentTile.setOwner(null);
                            }
                        }
                    }
                }
                getNextPlayer();
            } else if (dices.areTwoDicesEqual()) {
                numberOfDoubleRolls++;
                if (numberOfDoubleRolls == 3) {
                    System.out.println(currentPlayer.getName() + " rolled a double 3 times in a row and is going to jail.");
                    // move the player to the prison tile
                    moveToPrison(currentPlayer);
                    currentPlayer.setInPrison(true);

                    getNextPlayer();
                } else {
                    System.out.println(currentPlayer.getName() + " rolled a double and got another turn.");
                }
            } else {
                getNextPlayer();
            }
            counter++;
        }
        printGameResult();
    }

    /**
     * Strategy 2: For simulation and comparison purposes, the first player will only buy the tiles in color group 4 and 5 and utilities.
     *      The other players will buy any tile if they have enough money.
     */
    public void playStrategy2() {
        // get player from the circular doubly linked list
        int counter = 0;

        System.out.println("Game Starts!");
        while (counter < 10000 && playersOrder.getSize() > 1) {
            byte sum = dices.roll();

            System.out.println(currentPlayer.getName() + " rolled " + sum + ".");
            if (currentPlayer.isInPrison()) {
                if (currentPlayer.isHasFreeFromPrisonCard()) {
                    currentPlayer.setHasFreeFromPrisonCard(false);
                    currentPlayer.setInPrison(false);
                    System.out.println(currentPlayer.getName() + " used a get out of jail free card.");
                } else {
                    if (dices.areTwoDicesEqual()) {
                        currentPlayer.setInPrison(false);
                        System.out.println(currentPlayer.getName() + " rolled a double and got out of jail.");
                    } else {
                        System.out.println(currentPlayer.getName() + " is still in jail.");
                        counter++;
                        currentPlayerPointer = currentPlayerPointer.getNext();
                        currentPlayer = currentPlayerPointer.getData();
                        currentPlayerIndex = (currentPlayerIndex + 1) % playersOrder.getSize();
                        continue;
                    }
                }
            }
            // move the player
            movePlayer(sum);
            System.out.println(currentPlayer.getName() + " moved to " + tiles.get(playersPosition.get(currentPlayerIndex)).getName() + ".");
            // get the tile
            Tile tile = tiles.get(playersPosition.get(currentPlayerIndex));
            // check if the tile is a purchasable tile
            if (tile instanceof PurchasableTile purchasableTile) {
                // check if the tile is owned
                if (purchasableTile.getOwner() == null) {
                    // check if the player is the first player in the list
                    if (playersOrder.get(0) == currentPlayer) {
                        // check if the tile is in color group 4 or 5
                        if (purchasableTile instanceof Land land) {
                            if (land.getColorGroup() == 4 || land.getColorGroup() == 5) {
                                // check if the player has enough money to buy the tile
                                if (currentPlayer.getBalance() >= purchasableTile.getPrice()) {
                                    // buy the tile
                                    purchasableTile.buy(currentPlayer);
                                    System.out.println(currentPlayer.getName() + " bought " + purchasableTile.getName() + " for " + purchasableTile.getPrice() + " dollars.");

                                } else {
                                    System.out.println(currentPlayer.getName() + " does not have enough money to buy " + purchasableTile.getName() + ".");
                                }
                            }
                        } else if (purchasableTile instanceof Utilities utilities) {
                            // check if the player has enough money to buy the tile
                            if (currentPlayer.getBalance() >= utilities.getPrice()) {
                                // buy the tile
                                utilities.buy(currentPlayer);
                                System.out.println(currentPlayer.getName() + " bought " + utilities.getName() + " for " + utilities.getPrice() + " dollars.");

                            } else {
                                System.out.println(currentPlayer.getName() + " does not have enough money to buy " + utilities.getName() + ".");
                            }
                        }
                    } else {
                        // check if the player has enough money to buy the tile
                        if (currentPlayer.getBalance() >= purchasableTile.getPrice()) {
                            // buy the tile
                            purchasableTile.buy(currentPlayer);
                            System.out.println(currentPlayer.getName() + " bought " + purchasableTile.getName() + " for " + purchasableTile.getPrice() + " dollars.");

                        } else {
                            System.out.println(currentPlayer.getName() + " does not have enough money to buy " + purchasableTile.getName() + ".");
                        }
                    }
                } else {
                    if (purchasableTile.getOwner() == currentPlayer) {
                        if (purchasableTile instanceof Land land) {
                            // check if the player exceeds the house limit
                            if (land.getHouseCount() > 4) {
                                System.out.println(currentPlayer.getName() + " cannot build more than 5 houses.");
                                continue;
                            } else {
                                // check if the player has enough money to buy a house or a hotel
                                if (currentPlayer.getBalance() >= land.getPriceList()[land.getHouseCount()]) {
                                    // buy a house or a hotel
                                    land.setHouseCount((byte) (land.getHouseCount() + 1));
                                    System.out.println(currentPlayer.getName() + " built a house at " + land.getName() + " for " + land.getPriceList()[land.getHouseCount() - 1] + " dollars.");
                                } else {
                                    System.out.println(currentPlayer.getName() + " does not have enough money to build a house.");
                                }
                            }
                        }
                    } else { // if the player is not the owner
                        // pay the rent if not the owner
                        purchasableTile.payRent(currentPlayer);
                        System.out.println(currentPlayer.getName() + " paid " + purchasableTile.getOwner().getName() + " " + purchasableTile.getRent() + " dollars.");
                    }
                }
            } else if (tile instanceof ChanceChest) {
                // draw a chance card
                ChanceCard card = ((ChanceChest) tile).drawCard();
                System.out.println(currentPlayer.getName() + " drew a chance card: " + card.getName());
                if (card.getAction() == Action.MOVE) {
                    // move the player
                    playersPosition.set(currentPlayerIndex, (playersPosition.get(currentPlayerIndex) + card.getParameter()) % tiles.size());
                } else if (card.getAction() == Action.PAY) {
                    // pay the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars.");
                }
            } else if (tile instanceof CommunityChest) {
                // draw a community chest card
                CommunityCard card = ((CommunityChest) tile).drawCard();
                System.out.println(currentPlayer.getName() + " drew a community chest card: " + card.getName());
                if (card.getAction() == Action.MOVE) {
                    // move the player
                    movePlayer((byte) card.getParameter());
                } else if (card.getAction() == Action.PAY) {
                    // pay the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars.");
                } else if (card.getAction() == Action.RECEIVE) {
                    // receive the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() + card.getParameter());
                    System.out.println(currentPlayer.getName() + " received " + card.getParameter() + " dollars.");
                } else if (card.getAction() == Action.RECEIVE_FROM_ALL) {
                    // receive the amount from all players
                    for (int i = 0; i < playersOrder.getSize(); i++) {
                        if (playersOrder.get(i) != currentPlayer) {
                            playersOrder.get(i).setBalance(playersOrder.get(i).getBalance() - card.getParameter());
                            currentPlayer.setBalance(currentPlayer.getBalance() + card.getParameter());
                        }
                    }
                    System.out.println(currentPlayer.getName() + " received " + card.getParameter() + " dollars from all players.");
                } else if (card.getAction() == Action.PAY_TO_ALL) {
                    // pay the amount to all players
                    for (int i = 0; i < playersOrder.getSize(); i++) {
                        if (playersOrder.get(i) != currentPlayer) {
                            playersOrder.get(i).setBalance(playersOrder.get(i).getBalance() + card.getParameter());
                            currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                        }
                    }
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars to all players.");
                } else if (card.getAction() == Action.FREE_FROM_PRISON) {
                    // set the player's freeFromPrison to true
                    currentPlayer.setHasFreeFromPrisonCard(true);
                    System.out.println(currentPlayer.getName() + " received a get out of jail free card.");
                } else if (card.getAction() == Action.GO_TO_PRISON) {
                    // move the player to the prison tile
                    moveToPrison(currentPlayer);
                    currentPlayer.setInPrison(true);
                    System.out.println(currentPlayer.getName() + " went to jail.");
                }

            } else if (tile instanceof Tax tax) {
                // pay the tax
                currentPlayer.setBalance(currentPlayer.getBalance() - tax.getTaxAmount());
                System.out.println(currentPlayer.getName() + " paid " + tax.getTaxAmount() + " dollars in tax.");
            } else if (tile instanceof Parking) {
                System.out.println(currentPlayer.getName() + " landed in the parking tile.");
            } else if (tile instanceof Start) {
                // add 200 to the player's balance
                currentPlayer.setBalance(currentPlayer.getBalance() + 200);
                System.out.println(currentPlayer.getName() + " received 200 dollars for visiting the start tile.");
            } else if (tile instanceof Prison) {
                System.out.println(currentPlayer.getName() + " landed in the prison tile.");
            } else if (tile instanceof GoToPrison) {
                // move the player to the prison tile
                moveToPrison(currentPlayer);
                currentPlayer.setInPrison(true);
                System.out.println(currentPlayer.getName() + " went to jail.");
            }

            if (currentPlayer.getBalance() < 0) {
                System.out.println(currentPlayer.getName() + "'s balance is below 0: " + currentPlayer.getBalance());
                int totalAssets = 0;
                for (int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i) instanceof PurchasableTile) {
                        PurchasableTile currentTile = (PurchasableTile) tiles.get(i);
                        if (currentTile.getOwner() == currentPlayer) {
                            totalAssets += currentTile.getPrice() / 2;
                        }
                    }
                }
                if (totalAssets >= Math.abs(currentPlayer.getBalance())) {
                    // sell until the player has enough money
                    while (currentPlayer.getBalance() < 0) {
                        for (int i = 0; i < tiles.size(); i++) {
                            if (tiles.get(i) instanceof PurchasableTile) {
                                PurchasableTile currentTile = (PurchasableTile) tiles.get(i);
                                if (currentTile.getOwner() == currentPlayer) {
                                    currentTile.sell();
                                    System.out.println(currentPlayer.getName() + " sold " + currentTile.getName() + " for " + currentTile.getPrice() / 2 + " dollars.");
                                    if (currentPlayer.getBalance() >= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println(currentPlayer.getName() + " is bankrupt and is out of the game.");
                    System.out.println(currentPlayer.getName() + " balance: " + currentPlayer.getBalance());

                    // remove the player from the playersOrder
                    playersOrder.remove(currentPlayer);
                    // remove the player from the playersPosition arraylist
                    playersPosition.remove(currentPlayerIndex);
                    // remove the player from the tiles
                    for (int i = 0; i < tiles.size(); i++) {
                        if (tiles.get(i) instanceof PurchasableTile currentTile) {
                            if (currentTile.getOwner() == currentPlayer) {
                                currentTile.setOwner(null);
                            }
                        }
                    }
                }
                getNextPlayer();
            } else if (dices.areTwoDicesEqual()) {
                numberOfDoubleRolls++;
                if (numberOfDoubleRolls == 3) {
                    System.out.println(currentPlayer.getName() + " rolled a double 3 times in a row and is going to jail.");
                    // move the player to the prison tile
                    moveToPrison(currentPlayer);
                    currentPlayer.setInPrison(true);

                    getNextPlayer();
                } else {
                    System.out.println(currentPlayer.getName() + " rolled a double and got another turn.");
                }
            } else {
                getNextPlayer();
            }
            System.out.println("Counter: " + counter + ", Player order count: " + playersOrder.getSize());
            counter++;
        }
        printGameResult();
    }

    /**
     * Strategy 3: For simulation and comparison purposes, the first player will only buy if they have over 500 dollars after buying the tile.
     *     The other players will buy any tile if they have enough money.
     */
    public void playStrategy3() {
        // get player from the circular doubly linked list
        int counter = 0;

        System.out.println("Game Starts!");
        while (counter < 10000 && playersOrder.getSize() > 1) {
            byte sum = dices.roll();

            System.out.println(currentPlayer.getName() + " rolled " + sum + ".");
            if (currentPlayer.isInPrison()) {
                if (currentPlayer.isHasFreeFromPrisonCard()) {
                    currentPlayer.setHasFreeFromPrisonCard(false);
                    currentPlayer.setInPrison(false);
                    System.out.println(currentPlayer.getName() + " used a get out of jail free card.");
                } else {
                    if (dices.areTwoDicesEqual()) {
                        currentPlayer.setInPrison(false);
                        System.out.println(currentPlayer.getName() + " rolled a double and got out of jail.");
                    } else {
                        System.out.println(currentPlayer.getName() + " is still in jail.");
                        counter++;
                        currentPlayerPointer = currentPlayerPointer.getNext();
                        currentPlayer = currentPlayerPointer.getData();
                        currentPlayerIndex = (currentPlayerIndex + 1) % playersOrder.getSize();
                        continue;
                    }
                }
            }
            // move the player
            movePlayer(sum);
            System.out.println(currentPlayer.getName() + " moved to " + tiles.get(playersPosition.get(currentPlayerIndex)).getName() + ".");
            // get the tile
            Tile tile = tiles.get(playersPosition.get(currentPlayerIndex));
            // check if the tile is a purchasable tile
            if (tile instanceof PurchasableTile purchasableTile) {
                // check if the tile is owned
                if (purchasableTile.getOwner() == null) {
                    if (currentPlayer == playersOrder.get(0)) {
                        // check if the player has over 500 after buying the tile, if so, buy it, otherwise don't
                        if (currentPlayer.getBalance() - purchasableTile.getPrice() > 500) {
                            // buy the tile
                            purchasableTile.buy(currentPlayer);
                            System.out.println(currentPlayer.getName() + " bought " + purchasableTile.getName() + " for " + purchasableTile.getPrice() + " dollars.");
                        } else {
                            System.out.println(currentPlayer.getName() + " wants to keep their balance above 500 so they didn't buy " + purchasableTile.getName() + ".");
                        }
                    } else {
                        // check if the player has enough money to buy the tile
                        if (currentPlayer.getBalance() >= purchasableTile.getPrice()) {
                            // buy the tile
                            purchasableTile.buy(currentPlayer);
                            System.out.println(currentPlayer.getName() + " bought " + purchasableTile.getName() + " for " + purchasableTile.getPrice() + " dollars.");

                        } else {
                            System.out.println(currentPlayer.getName() + " does not have enough money to buy " + purchasableTile.getName() + ".");
                        }
                    }
                } else {
                    if (purchasableTile.getOwner() == currentPlayer) {
                        if (purchasableTile instanceof Land land) {
                            // check if the player exceeds the house limit
                            if (land.getHouseCount() > 4) {
                                System.out.println(currentPlayer.getName() + " cannot build more than 5 houses.");
                                continue;
                            } else {
                                // check if the player has enough money to buy a house or a hotel
                                if (currentPlayer.getBalance() >= land.getPriceList()[land.getHouseCount()]) {
                                    // buy a house or a hotel
                                    land.setHouseCount((byte) (land.getHouseCount() + 1));
                                    System.out.println(currentPlayer.getName() + " built a house at " + land.getName() + " for " + land.getPriceList()[land.getHouseCount() - 1] + " dollars.");
                                } else {
                                    System.out.println(currentPlayer.getName() + " does not have enough money to build a house.");
                                }
                            }

                        }
                    } else { // if the player is not the owner
                        // pay the rent if not the owner
                        purchasableTile.payRent(currentPlayer);
                        System.out.println(currentPlayer.getName() + " paid " + purchasableTile.getOwner().getName() + " " + purchasableTile.getRent() + " dollars.");
                    }
                }
            } else if (tile instanceof ChanceChest) {
                // draw a chance card
                ChanceCard card = ((ChanceChest) tile).drawCard();
                System.out.println(currentPlayer.getName() + " drew a chance card: " + card.getName());
                if (card.getAction() == Action.MOVE) {
                    // move the player
                    playersPosition.set(currentPlayerIndex, (playersPosition.get(currentPlayerIndex) + card.getParameter()) % tiles.size());
                } else if (card.getAction() == Action.PAY) {
                    // pay the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars.");
                }
            } else if (tile instanceof CommunityChest) {
                // draw a community chest card
                CommunityCard card = ((CommunityChest) tile).drawCard();
                System.out.println(currentPlayer.getName() + " drew a community chest card: " + card.getName());
                if (card.getAction() == Action.MOVE) {
                    // move the player
                    movePlayer((byte) card.getParameter());
                } else if (card.getAction() == Action.PAY) {
                    // pay the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars.");
                } else if (card.getAction() == Action.RECEIVE) {
                    // receive the amount
                    currentPlayer.setBalance(currentPlayer.getBalance() + card.getParameter());
                    System.out.println(currentPlayer.getName() + " received " + card.getParameter() + " dollars.");
                } else if (card.getAction() == Action.RECEIVE_FROM_ALL) {
                    // receive the amount from all players
                    for (int i = 0; i < playersOrder.getSize(); i++) {
                        if (playersOrder.get(i) != currentPlayer) {
                            playersOrder.get(i).setBalance(playersOrder.get(i).getBalance() - card.getParameter());
                            currentPlayer.setBalance(currentPlayer.getBalance() + card.getParameter());
                        }
                    }
                    System.out.println(currentPlayer.getName() + " received " + card.getParameter() + " dollars from all players.");
                } else if (card.getAction() == Action.PAY_TO_ALL) {
                    // pay the amount to all players
                    for (int i = 0; i < playersOrder.getSize(); i++) {
                        if (playersOrder.get(i) != currentPlayer) {
                            playersOrder.get(i).setBalance(playersOrder.get(i).getBalance() + card.getParameter());
                            currentPlayer.setBalance(currentPlayer.getBalance() - card.getParameter());
                        }
                    }
                    System.out.println(currentPlayer.getName() + " paid " + card.getParameter() + " dollars to all players.");
                } else if (card.getAction() == Action.FREE_FROM_PRISON) {
                    // set the player's freeFromPrison to true
                    currentPlayer.setHasFreeFromPrisonCard(true);
                    System.out.println(currentPlayer.getName() + " received a get out of jail free card.");
                } else if (card.getAction() == Action.GO_TO_PRISON) {
                    // move the player to the prison tile
                    moveToPrison(currentPlayer);
                    currentPlayer.setInPrison(true);
                    System.out.println(currentPlayer.getName() + " went to jail.");
                }

            } else if (tile instanceof Tax tax) {
                // pay the tax
                currentPlayer.setBalance(currentPlayer.getBalance() - tax.getTaxAmount());
                System.out.println(currentPlayer.getName() + " paid " + tax.getTaxAmount() + " dollars in tax.");
            } else if (tile instanceof Parking) {
                System.out.println(currentPlayer.getName() + " landed in the parking tile.");
            } else if (tile instanceof Start) {
                // add 200 to the player's balance
                currentPlayer.setBalance(currentPlayer.getBalance() + 200);
                System.out.println(currentPlayer.getName() + " received 200 dollars for visiting the start tile.");
            } else if (tile instanceof Prison) {
                System.out.println(currentPlayer.getName() + " landed in the prison tile.");
            } else if (tile instanceof GoToPrison) {
                // move the player to the prison tile
                moveToPrison(currentPlayer);
                currentPlayer.setInPrison(true);
                System.out.println(currentPlayer.getName() + " went to jail.");
            }

            if (currentPlayer.getBalance() < 0) {
                System.out.println(currentPlayer.getName() + "'s balance is below 0: " + currentPlayer.getBalance());
                int totalAssets = 0;
                for (int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i) instanceof PurchasableTile) {
                        PurchasableTile currentTile = (PurchasableTile) tiles.get(i);
                        if (currentTile.getOwner() == currentPlayer) {
                            totalAssets += currentTile.getPrice() / 2;
                        }
                    }
                }
                if (totalAssets >= Math.abs(currentPlayer.getBalance())) {
                    // sell until the player has enough money
                    while (currentPlayer.getBalance() < 0) {
                        for (int i = 0; i < tiles.size(); i++) {
                            if (tiles.get(i) instanceof PurchasableTile) {
                                PurchasableTile currentTile = (PurchasableTile) tiles.get(i);
                                if (currentTile.getOwner() == currentPlayer) {
                                    currentTile.sell();
                                    System.out.println(currentPlayer.getName() + " sold " + currentTile.getName() + " for " + currentTile.getPrice() / 2 + " dollars.");
                                    if (currentPlayer.getBalance() >= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println(currentPlayer.getName() + " is bankrupt and is out of the game.");
                    System.out.println(currentPlayer.getName() + " balance: " + currentPlayer.getBalance());

                    // remove the player from the playersOrder
                    playersOrder.remove(currentPlayer);
                    // remove the player from the playersPosition arraylist
                    playersPosition.remove(currentPlayerIndex);
                    // remove the player from the tiles
                    for (int i = 0; i < tiles.size(); i++) {
                        if (tiles.get(i) instanceof PurchasableTile currentTile) {
                            if (currentTile.getOwner() == currentPlayer) {
                                currentTile.setOwner(null);
                            }
                        }
                    }
                }
                getNextPlayer();
            } else if (dices.areTwoDicesEqual()) {
                numberOfDoubleRolls++;
                if (numberOfDoubleRolls == 3) {
                    System.out.println(currentPlayer.getName() + " rolled a double 3 times in a row and is going to jail.");
                    // move the player to the prison tile
                    moveToPrison(currentPlayer);
                    currentPlayer.setInPrison(true);

                    getNextPlayer();
                } else {
                    System.out.println(currentPlayer.getName() + " rolled a double and got another turn.");
                }
            } else {
                getNextPlayer();
            }
            System.out.println("Counter: " + counter + ", Player order count: " + playersOrder.getSize());
            counter++;
        }
        printGameResult();
    }

    /**
     * Move player forward a number of steps
     * @param steps the number of steps to move
     */
    private void movePlayer(byte steps) {
        if (playersPosition.get(currentPlayerIndex) + steps >= tiles.size()) {
            // add 200 to the player's balance
            currentPlayer.setBalance(currentPlayer.getBalance() + 100);
            System.out.println(currentPlayer.getName() + " received 100 dollars for passing the start tile.");
        }
        playersPosition.set(currentPlayerIndex, (playersPosition.get(currentPlayerIndex) + steps) % tiles.size());
    }

    /**
     * Get the next player in the circular doubly linked list
     */
    private void getNextPlayer() {
        currentPlayerPointer = currentPlayerPointer.getNext();
        currentPlayer = currentPlayerPointer.getData();
        currentPlayerIndex = (currentPlayerIndex + 1) % playersOrder.getSize();
        numberOfDoubleRolls = 0;
    }

    /**
     * Move the player to the prison tile
     * @param player the player to move to prison
     */
    private void moveToPrison(Player player) {
        playersPosition.set(currentPlayerIndex, prisonIndex);
    }

    /**
     * Print the game result (winner's balance and assets)
     */
    private void printGameResult() {
        System.out.println("Game Ends!\n");
        System.out.println("---------------------");
        for (int i = 0; i < playersOrder.getSize(); i++) {
            System.out.println(playersOrder.get(i).getName() + " balance: " + playersOrder.get(i).getBalance());
        }
        // Assets
        System.out.println("---------------------");
        for (int i = 0; i < playersOrder.getSize(); i++) {
            System.out.println(playersOrder.get(i).getName() + " assets: ");
            for (int j = 0; j < tiles.size(); j++) {
                if (tiles.get(j) instanceof PurchasableTile tile) {
                    if (tile.getOwner() == playersOrder.get(i)) {
                        System.out.print(tile.getName());
                        if (tile instanceof Land land) {
                            System.out.println(": Houses: " + (land.getHouseCount()));
                        }
                        else {
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the players order
     * @return the players order
     */
    public CircularDoublyLinkedList<Player> getPlayersOrder() {
        return playersOrder;
    }

    public void reset(){
        playersOrder = new CircularDoublyLinkedList<>();
        playersOrder.add(new Player("Player 1"));
        playersOrder.add(new Player("Player 2"));
        playersOrder.add(new Player("Player 3"));
        tiles = new ArrayList<>();
        // add tiles to the board
        tiles.add(new Start());
        tiles.add(new Land("Brampton", 60, 10, (byte) 1));
        tiles.add(new CommunityChest("Community Chest"));
        tiles.add(new Land("Summerside", 65, 15, (byte) 1));
        tiles.add(new Tax("Tax", 100));
        tiles.add(new Utilities("Electricity", 200, 20));
        tiles.add(new Land("Windsor", 100, 25, (byte) 2));
        tiles.add(new ChanceChest("Chance Chest"));
        tiles.add(new Land("London", 110, 25, (byte) 2));
        tiles.add(new Land("Hamilton", 120, 30, (byte) 2));
        tiles.add(new Prison());
        tiles.add(new Land("Kitchener", 140, 35, (byte) 3));
        tiles.add(new Utilities("Water", 150, 40));
        tiles.add(new Land("Niagara Falls", 145, 40, (byte) 3));
        tiles.add(new Land("Ottawa", 160, 50, (byte) 3));
        tiles.add(new Utilities("Internet", 200, 45));
        tiles.add(new Land("Calgary", 180, 45, (byte) 4));
        tiles.add(new CommunityChest("Community Chest"));
        tiles.add(new Land("Quebec City", 180, 50, (byte) 4));
        tiles.add(new Land("Halifax", 200, 55, (byte) 4));
        tiles.add(new Parking("Parking"));
        tiles.add(new Land("Moncton", 220, 60, (byte) 5));
        tiles.add(new ChanceChest("Chance Chest"));
        tiles.add(new Land("Charlottetown", 220, 60, (byte) 5));
        tiles.add(new Land("Fredericton", 240, 65, (byte) 5));
        tiles.add(new Utilities("Gas", 200, 70));
        tiles.add(new Land("Markham", 260, 70, (byte) 6));
        tiles.add(new Land("Saskatoon", 260, 75, (byte) 6));
        tiles.add(new Utilities("Cable", 150, 80));
        tiles.add(new Land("Waterloo", 280, 75, (byte) 6));
        tiles.add(new GoToPrison());
        tiles.add(new Land("Regina", 300, 80, (byte) 7));
        tiles.add(new Land("Winnipeg", 300, 85, (byte) 7));
        tiles.add(new CommunityChest("Community Chest"));
        tiles.add(new Land("Vancouver", 320, 85, (byte) 7));
        tiles.add(new Utilities("Telephone", 200, 90));
        tiles.add(new ChanceChest("Chance Chest"));
        tiles.add(new Land("Montreal", 350, 90, (byte) 8));
        tiles.add(new Tax("Tax", 100));
        tiles.add(new Land("Toronto", 400, 95, (byte) 8));

        currentPlayer = playersOrder.getHead().getData();
        currentPlayerIndex = 0;
        // Set the players position to 0
        playersPosition = new ArrayList<Integer>();
        for (int i = 0; i < playersOrder.getSize(); i++) {
            playersPosition.add(0);
        }
        currentPlayerPointer = playersOrder.getHead();
    }
}
