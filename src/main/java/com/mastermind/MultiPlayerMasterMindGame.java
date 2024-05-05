package com.mastermind;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MultiPlayerMasterMindGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // choose game parameters
        System.out.println("Welcome to Multiplayer Master Mind!");
        System.out.println("Enter the number of players:");
        int numPlayers = scanner.nextInt();
        System.out.println("Enter the number of pawns (4 or 5):");
        int numPawns = scanner.nextInt();
        System.out.println("Enter the number of colors (6 to 8):");
        int numColors = scanner.nextInt();
        System.out.println("Enter the number of attempts (10 or 12):");
        int numAttempts = scanner.nextInt();
        System.out.println("Enter the number of games to play:");
        int numGames = scanner.nextInt();

        // play multiple games
        int[] totalScores = new int[numPlayers];
        for (int game = 1; game <= numGames; game++) {
            System.out.println("Game " + game);
            int[] scores = playGame(numPlayers, numPawns, numColors, numAttempts, scanner);
            // update total scores after each game
            for (int i = 0; i < numPlayers; i++) {
                totalScores[i] += scores[i];
            }
            // display scores after each game
            System.out.println("Scores after Game " + game + ":");
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Player " + (i + 1) + ": " + scores[i]);
            }
        }
        // display total scores after all games
        System.out.println("Total Scores:");
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + ": " + totalScores[i]);
        }

        scanner.close();
    }

    private static int[] playGame(int numPlayers, int numPawns, int numColors, int numAttempts, Scanner scanner) {
        int[] scores = new int[numPlayers];
        for (int round = 1; round <= numPlayers; round++) {
            System.out.println("Player " + round + "'s turn:");
            // create secret combination
            Combination secretCombination = generateSecretCombination(numPawns, numColors);
            Board board = new Board(secretCombination);

            // play the game
            for (int attempt = 1; attempt <= numAttempts; attempt++) {
                System.out.println("Attempt " + attempt);
                System.out.println("Enter your combination of colors (1-" + numColors + "):");
                List<Pawn> attemptPawns = readAttempt(numPawns, numColors, scanner);
                Combination playerAttempt = new Combination(attemptPawns);
                AttemptResult result = secretCombination.validateAttempt(playerAttempt);
                board.addAttempt(playerAttempt);
                // display board and feedback
                GameDisplay.displayBoard(board);
                if (result.getCorrectPosition() == numPawns) {
                    System.out.println("Congratulations! Player " + round + " guessed the combination!");
                    scores[round - 1] += numAttempts - attempt + 1;
                    break;
                } else {
                    System.out.println("Correct position: " + result.getCorrectPosition() +
                            ", Correct color: " + result.getCorrectColor());
                }
                if (attempt == numAttempts) {
                    System.out.println("Game over! Player " + round + " has run out of attempts. The secret combination was:");
                    for (Pawn pawn : secretCombination.getPawns()) {
                        System.out.print(pawn.getColor() + " ");
                    }
                    System.out.println();
                }
            }
        }
        return scores;
    }

    private static Combination generateSecretCombination(int numPawns, int numColors) {
        List<Pawn> pawns = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numPawns; i++) {
            int colorIndex = random.nextInt(numColors) + 1;
            pawns.add(new Pawn(Integer.toString(colorIndex)));
        }
        return new Combination(pawns);
    }

    private static List<Pawn> readAttempt(int numPawns, int numColors, Scanner scanner) {
        List<Pawn> attemptPawns = new ArrayList<>();
        for (int i = 0; i < numPawns; i++) {
            int color = scanner.nextInt();
            if (color < 1 || color > numColors) {
                System.out.println("Invalid color! Please enter a number between 1 and " + numColors);
                i--; // repeat the loop iteration to re-enter the color
                continue;
            }
            attemptPawns.add(new Pawn(Integer.toString(color)));
        }
        return attemptPawns;
    }
}
