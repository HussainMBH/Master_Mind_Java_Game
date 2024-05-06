package com.mastermind;

import java.util.List;

public class GameDisplay {


    public static void displayBoard(Board board) {
        Combination secretCombination = board.getSecretCombination();
        List<Combination> attempts = board.getAttempts();
        List<AttemptResult> attemptResults = board.getAttemptResults();


//        // display secret combination
//        System.out.print("Secret Combination: ");
//        for (Pawn pawn : secretCombination.getPawns()) {
//            System.out.print(getColoredPawnSymbol(pawn) + " ");
//        }
//        System.out.println();

        // display attempts
        System.out.println("Attempts:");

        for (int i = 0; i < attempts.size(); i++) {
            System.out.print("Attempt " + (i + 1) + ": ");
            Combination attempt = attempts.get(i);
            for (Pawn pawn : attempt.getPawns()) {
                System.out.print(getColoredPawnSymbol(pawn) + " ");
            }
            System.out.print("   ");


            // Display attempt result
            AttemptResult result = attemptResults.get(i);
            System.out.println("Correct position: " + result.getCorrectPosition() +
                    ", Correct color: " + result.getCorrectColor());
            System.out.println();

        }

    }

    private static String getColoredPawnSymbol(Pawn pawn) {
         
        return pawn.getColor();
    }

    private static AttemptResult calculateAttemptResult(Combination secretCombination, Combination attempt) {
         
        return new AttemptResult(0, 0);
    }
}
