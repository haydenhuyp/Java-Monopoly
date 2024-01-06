package upei.project;

import java.util.Objects;

public class SimulationExperiment {
    private static final int NUM_OF_TRIALS = 100;
    private static final byte[][] player1Wins = new byte[3][3];
    private static final byte[][] player2Wins = new byte[3][3];
    private static final byte[][] player3Wins = new byte[3][3];
    private static final byte[][] moreThanOnePlayerLeft = new byte[3][3];
    public static void main(String[] args) {
        Board board = new Board();
        // There are 3 strategies
        // For each run of the experiment, the program will run 100 turns.
        // The program will then record the number of times each player wins, as well as the number of times the game goes pass 10000 turns,
        // and then take the average.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < NUM_OF_TRIALS; j++) {
                board.reset();
                board.playStrategy1();
                if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 1")) {
                    player1Wins[i][0]++;
                } else if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 2")) {
                    player2Wins[i][0]++;
                } else if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 3")) {
                    player3Wins[i][0]++;
                } else {
                    moreThanOnePlayerLeft[i][0]++;
                }

            }
            for (int j = 0; j < NUM_OF_TRIALS; j++) {
                board.reset();
                board.playStrategy1();
                if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 1")) {
                    player1Wins[i][1]++;
                } else if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 2")) {
                    player2Wins[i][1]++;
                } else if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 3")) {
                    player3Wins[i][1]++;
                } else {
                    moreThanOnePlayerLeft[i][1]++;
                }
            }
            for (int j = 0; j < NUM_OF_TRIALS; j++) {
                board.reset();
                board.playStrategy1();
                if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 1")) {
                    player1Wins[i][2]++;
                } else if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 2")) {
                    player2Wins[i][2]++;
                } else if (Objects.equals(board.getPlayersOrder().get(0).getName(), "Player 3")) {
                    player3Wins[i][2]++;
                } else {
                    moreThanOnePlayerLeft[i][2]++;
                }
            }
        }

        // print out the result table
        System.out.println("Strategy 1");
        System.out.println("Player 1 wins: " + player1Wins[0][0] + " " + player1Wins[0][1] + " " + player1Wins[0][2]);
        System.out.println("Player 2 wins: " + player2Wins[0][0] + " " + player2Wins[0][1] + " " + player2Wins[0][2]);
        System.out.println("Player 3 wins: " + player3Wins[0][0] + " " + player3Wins[0][1] + " " + player3Wins[0][2]);
        System.out.println("More than one player left: " + moreThanOnePlayerLeft[0][0] + " " + moreThanOnePlayerLeft[0][1] + " " + moreThanOnePlayerLeft[0][2]);
        System.out.println("Strategy 2");
        System.out.println("Player 1 wins: " + player1Wins[1][0] + " " + player1Wins[1][1] + " " + player1Wins[1][2]);
        System.out.println("Player 2 wins: " + player2Wins[1][0] + " " + player2Wins[1][1] + " " + player2Wins[1][2]);
        System.out.println("Player 3 wins: " + player3Wins[1][0] + " " + player3Wins[1][1] + " " + player3Wins[1][2]);
        System.out.println("More than one player left: " + moreThanOnePlayerLeft[1][0] + " " + moreThanOnePlayerLeft[1][1] + " " + moreThanOnePlayerLeft[1][2]);
        System.out.println("Strategy 3");
        System.out.println("Player 1 wins: " + player1Wins[2][0] + " " + player1Wins[2][1] + " " + player1Wins[2][2]);
        System.out.println("Player 2 wins: " + player2Wins[2][0] + " " + player2Wins[2][1] + " " + player2Wins[2][2]);
        System.out.println("Player 3 wins: " + player3Wins[2][0] + " " + player3Wins[2][1] + " " + player3Wins[2][2]);
        System.out.println("More than one player left: " + moreThanOnePlayerLeft[2][0] + " " + moreThanOnePlayerLeft[2][1] + " " + moreThanOnePlayerLeft[2][2]);
    }
}
