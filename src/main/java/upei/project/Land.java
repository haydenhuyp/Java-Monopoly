package upei.project;

import java.util.ArrayList;

public class Land extends PurchasableTile{
    // for simplicity, we will use 0 for brown, 1 for light blue, 2 for pink, 3 for orange, 4 for red, 5 for yellow, 6 for green, 7 for dark blue
    private byte colorGroup;

    // first item represent property price, next four items represent houses, last item represents hotel
    private int[] priceList = new int[6];
    // house = 0 means no house, house = 1 means one house, house = 5 means hotel
    private byte houseCount = 0;
    // first item represent the original rent rate, next four items represent houses, last item represents hotel
    private int[] rentList = new int[6];

    public Land(String name, int price, int rent, byte colorGroup) {
        super(name);
        this.colorGroup = colorGroup;
        // populate priceList
        priceList[0] = price;
        rentList[0] = rent;
        for (int i = 1; i < priceList.length; i++) {
            priceList[i] = price + (i * 50);
            this.rentList[i] = rent + (i * 50);
        }
    }

    /**
     * Sell the property to the bank
     */
    @Override
    public void sell(){
        int sellPrice = 0;
        for (int i = 0; i < houseCount + 1; i++) {
            sellPrice += priceList[houseCount];
        }
        this.getOwner().setBalance(this.getOwner().getBalance() + sellPrice/2);
        this.setOwner(null);
    }

    public byte getColorGroup() {
        return colorGroup;
    }

    public int[] getPriceList() {
        return priceList;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(byte houseCount) {
        this.houseCount = houseCount;
    }

    public int[] getRentList() {
        return rentList;
    }

    @Override
    public int getPrice() {
        return priceList[houseCount];
    }

    @Override
    public int getRent() {
        return rentList[houseCount];
    }

    /**
     * The player pays the rent to the owner
     * @param player The player who landed on the tile
     */
    public void payRent(Player player){
        this.getOwner().setBalance(this.getOwner().getBalance() + getRent());
        player.setBalance(player.getBalance() - getRent());
    }
}
