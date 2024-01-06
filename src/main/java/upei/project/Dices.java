package upei.project;

public class Dices {
    private byte dice1;
    private byte dice2;
    public Dices() {}

    /**
     * Roll two dices and return the sum of the two dices
     *
     * @return Sum of the two dices
     */
    public byte roll(){
        dice1 = (byte) (Math.random() * 6 + 1);
        dice2 = (byte) (Math.random() * 6 + 1);
        return (byte) (dice1 + dice2);
    }

    /**
     * Check if the two dices are equal
     *
     * @return True if the two dices are equal, false otherwise
     */
    public boolean areTwoDicesEqual(){
        if (dice1 == dice2){
            return true;
        }
        return false;
    }
}
