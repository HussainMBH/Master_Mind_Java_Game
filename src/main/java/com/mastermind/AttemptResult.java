package com.mastermind;

public class AttemptResult {
    private int correctColorAndPosition;
    private int correctColor;

    public AttemptResult(int correctColorAndPosition, int correctColor) {
        this.correctColorAndPosition = correctColorAndPosition;
        this.correctColor = correctColor;
    }

    //in here used get method for correct color and position
    public int getCorrectColorAndPosition() {
        return correctColorAndPosition;
    }

    public int getCorrectColor() {
        return correctColor;
    }

     
}
