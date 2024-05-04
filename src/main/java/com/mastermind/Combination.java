package com.mastermind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combination {
    private List<Pawn> pawns;

    public Combination(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public AttemptResult validateAttempt(Combination attempt) {
        // Initialize variables to count correct color and position, and only correct color
        int correctColorAndPosition = 0;
        int correctColor = 0;

        Map<String, Integer> secretColorCount = new HashMap<>();
        Map<String, Integer> attemptColorCount = new HashMap<>();

        // seret combination's color count function
        for (Pawn pawn : pawns) {
            String color = pawn.getColor();
            secretColorCount.put(color, secretColorCount.getOrDefault(color, 0) + 1);
        }

        // check how many times each color appears in the attempt
        for (Pawn pawn : attempt.getPawns()) {
            String color = pawn.getColor();
            attemptColorCount.put(color, attemptColorCount.getOrDefault(color, 0) + 1);
        }

        // calculate the number of pawns with correct color and position
        for (int i = 0; i < pawns.size(); i++) {
            if (pawns.get(i).getColor().equals(attempt.getPawns().get(i).getColor())) {
                correctColorAndPosition++;
                // Decrement the count for this color in both maps
                String color = pawns.get(i).getColor();
                secretColorCount.put(color, secretColorCount.get(color) - 1);
                attemptColorCount.put(color, attemptColorCount.get(color) - 1);
            }
        }

        // calculate the quantity of pawns at the wrongg location but with the proper color
        for (String color : attemptColorCount.keySet()) {
            if (secretColorCount.containsKey(color)) {
                correctColor += Math.min(secretColorCount.get(color), attemptColorCount.get(color));
            }
        }

        return new AttemptResult(correctColorAndPosition, correctColor);
    }
}
