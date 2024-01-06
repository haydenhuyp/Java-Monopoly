package upei.project;

import java.util.ArrayList;

public class Player {
    private String name;
    private int balance = 1500;
    private boolean hasFreeFromPrisonCard = false;
    private boolean isInPrison = false;

    public boolean isInPrison() {
        return isInPrison;
    }

    public void setInPrison(boolean inPrison) {
        isInPrison = inPrison;
    }

    public boolean isHasFreeFromPrisonCard() {
        return hasFreeFromPrisonCard;
    }

    public void setHasFreeFromPrisonCard(boolean hasFreeFromPrisonCard) {
        this.hasFreeFromPrisonCard = hasFreeFromPrisonCard;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
