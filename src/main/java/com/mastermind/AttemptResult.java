package com.mastermind;

public class AttemptResult {
    private int correctPosition;
    private int correctColor;

    public AttemptResult(int correctPosition, int correctColor) {
        this.correctPosition = correctPosition;
        this.correctColor = correctColor;
    }

    //in here used get method for correct color and position
    public int getCorrectPosition() {
        return correctPosition;
    }

    public int getCorrectColor() {
        return correctColor;
    }

     
}
