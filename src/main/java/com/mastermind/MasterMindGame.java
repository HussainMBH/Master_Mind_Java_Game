package com.mastermind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;



 

public class MasterMindGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // select game number
        System.out.println("Welcome to Master Mind Game!");
        System.out.println("Enter the number of pawns (4 or 5):");
        int numPawns = scanner.nextInt();
        System.out.println("Enter the number of colors (6 to 8):");
        int numColors = scanner.nextInt();
        System.out.println("Enter the number of attempts (10 or 12):");
        int numAttempts = scanner.nextInt();

        // create secret combination
        Combination secretCombination = generateSecretCombination(numPawns, numColors);

        // create board
        Board board = new Board(secretCombination);

        // play the game
        for (int attempt = 1; attempt <= numAttempts; attempt++) {
            System.out.println("Attempt " + attempt);
            System.out.println("Enter your combination of colors (1-" + numColors + "):");
            List<Pawn> attemptPawns = readAttempt(numPawns, numColors, scanner);
            Combination playerAttempt = new Combination(attemptPawns);
            AttemptResult result = secretCombination.validateAttempt(playerAttempt);
            board.addAttempt(playerAttempt);
            GameDisplay.displayBoard(board);
            if (result.getCorrectColorAndPosition() == numPawns) {
                System.out.println("Congratulations! You guessed the combination!");
                break;
            } else {
                System.out.println("Correct position: " + result.getCorrectColorAndPosition() +
                        ", Correct color: " + result.getCorrectColor());
            }
            if (attempt == numAttempts) {
                System.out.println("Game over! You've run out of attempts. The secret combination was:");
                for (Pawn pawn : secretCombination.getPawns()) {
                    System.out.print(pawn.getColor() + " ");
                }
            }
        }
        scanner.close();
         
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

    public void save(Path path, Board board, int[] scores, int currentPlayer) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            // write game parameters
            writer.write(board.getSecretCombination().getPawns().size() + " " +
                    board.getSecretCombination().getPawns().get(0).getColor().length() + " " +
                    board.getAttempts().size() + " " +
                    scores.length + " " +
                    currentPlayer);
            writer.newLine();

            // Write secret combination
            for (Pawn pawn : board.getSecretCombination().getPawns()) {
                writer.write(pawn.getColor());
                writer.newLine();
            }

            // write attempts
            for (Combination attempt : board.getAttempts()) {
                for (Pawn pawn : attempt.getPawns()) {
                    writer.write(pawn.getColor());
                    writer.write(" ");
                }
                writer.newLine();
            }

            // write scores
            for (int score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(Path path, Board board, int[] scores, int[] currentPlayer) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            // read game parameters
            String[] params = reader.readLine().split(" ");
            int numPawns = Integer.parseInt(params[0]);
            int pawnColorLength = Integer.parseInt(params[1]);
            int numAttempts = Integer.parseInt(params[2]);
            int numPlayers = Integer.parseInt(params[3]);
            currentPlayer[0] = Integer.parseInt(params[4]);

            // read secret combination
            List<Pawn> secretPawns = new ArrayList<>();
            for (int i = 0; i < numPawns; i++) {
                String color = reader.readLine();
                secretPawns.add(new Pawn(color));
            }
            Combination secretCombination = new Combination(secretPawns);
            board.setSecretCombination(secretCombination);

            // read attempts
            List<Combination> attempts = new ArrayList<>();
            for (int i = 0; i < numAttempts; i++) {
                String[] colors = reader.readLine().split(" ");
                List<Pawn> attemptPawns = new ArrayList<>();
                for (String color : colors) {
                    attemptPawns.add(new Pawn(color));
                }
                attempts.add(new Combination(attemptPawns));
            }
            board.setAttempts(attempts);

            // read scores
            for (int i = 0; i < numPlayers; i++) {
                scores[i] = Integer.parseInt(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(Path path) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            // write game state to the file
            outputStream.writeObject(this);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save the game: " + e.getMessage());
        }
    }

    public static MasterMindGame load(Path path) {
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
            // read game state from the file
            MasterMindGame game = (MasterMindGame) inputStream.readObject();
            System.out.println("Game loaded successfully!");
            return game;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load the game: " + e.getMessage());
            return null;
        }
    }
}
