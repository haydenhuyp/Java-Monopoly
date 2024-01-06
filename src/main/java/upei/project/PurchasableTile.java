package upei.project;

public abstract class PurchasableTile extends Tile {
    private int price;
    private Player owner;
    private int rent;

    public PurchasableTile(String name) {
        super(name);
    }

    public PurchasableTile(String name, int price, int rent) {
        super(name);
        this.price = price;
        this.rent = rent;
    }

    /**
     * Sell the property to the bank
     */
    public void sell(){
        this.getOwner().setBalance(this.getOwner().getBalance() + getPrice()/2);
        this.setOwner(null);
    }

    /**
     * Buy the property from the bank
     * @param player the player who is buying the property
     */
    public void buy(Player player){
        player.setBalance(player.getBalance() - getPrice());
        setOwner(player);
    }

    /**
     * The player pays the rent to the owner
     * @param player The player who landed on the tile
     */
    public void payRent(Player player){
        this.owner.setBalance(this.owner.getBalance() + getRent());
        player.setBalance(player.getBalance() - getRent());
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }
}
