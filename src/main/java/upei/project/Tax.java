package upei.project;

public class Tax extends Tile{
    private int taxAmount;
    public Tax(String name, int taxAmount) {
        super(name);
        this.taxAmount = taxAmount;
    }

    public int getTaxAmount() {
        return taxAmount;
    }
}
