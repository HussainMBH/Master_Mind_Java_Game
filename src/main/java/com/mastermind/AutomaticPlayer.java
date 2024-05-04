package com.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutomaticPlayer {
    private int numPawns;
    private int numColors;
    private List<Pawn> secretCombination;

    public AutomaticPlayer(int numPawns, int numColors) {
        this.numPawns = numPawns;
        this.numColors = numColors;
        this.secretCombination = generateSecretCombination();
    }

    public List<Pawn> generateAttempt() {
        List<Pawn> attempt = new ArrayList<>();
        Random random = new Random();

        // generate random pawns for the attempt
        for (int i = 0; i < numPawns; i++) {
             
            int colorIndex = random.nextInt(numColors);
            String color = getColorByIndex(colorIndex);
            attempt.add(new Pawn(color));
        }

        return attempt;
    }

    private List<Pawn> generateSecretCombination() {
        List<Pawn> combination = new ArrayList<>();
        Random random = new Random();

        // generate random pawns for the secret combination
        for (int i = 0; i < numPawns; i++) {
             
            int colorIndex = random.nextInt(numColors);
            String color = getColorByIndex(colorIndex);
            combination.add(new Pawn(color));
        }

        return combination;
    }

    private String getColorByIndex(int index) {
         
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Orange", "Purple"};
        
        // return the color based on the index
        if (index >= 0 && index < colors.length) {
            return colors[index];
        } else {
            // handle the case where index is out of bounds
            return "Unknown";
        }
    }
    

    public List<Pawn> getSecretCombination() {
        return secretCombination;
    }

     
    public static void main(String[] args) {
        AutomaticPlayer automaticPlayer = new AutomaticPlayer(4, 6); 

        // show and print the secret combination
        System.out.println("Secret Combination:");
        List<Pawn> secretCombination = automaticPlayer.getSecretCombination();
        for (Pawn pawn : secretCombination) {
            System.out.print(pawn.getColor() + " ");
        }
        System.out.println();  

        //   print an attempt
        List<Pawn> attempt = automaticPlayer.generateAttempt();
        System.out.println("Automatic Player's Attempt:");
        for (Pawn pawn : attempt) {
            System.out.print(pawn.getColor() + " ");
        }
    }
}
