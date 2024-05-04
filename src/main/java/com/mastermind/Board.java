package com.mastermind;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Combination secretCombination;
    private List<Combination> attempts;
    private List<AttemptResult> attemptResults;

    public Board(Combination secretCombination) {
        this.secretCombination = secretCombination;
        this.attempts = new ArrayList<>();
    }

    public Combination getSecretCombination() {
        return secretCombination;
    }
    public void setSecretCombination(Combination secretCombination) {
        this.secretCombination = secretCombination;
    }

    public List<Combination> getAttempts() {
        return attempts;
    }
    public void setAttempts(List<Combination> attempts) {
        this.attempts = attempts;
    }

    public void addAttempt(Combination attempt) {
        attempts.add(attempt);
    }

    public void setAttemptResults(List<AttemptResult> attemptResults) {
        this.attemptResults = attemptResults;
    }

    // Method to get attempt results
    public List<AttemptResult> getAttemptResults() {
        return attemptResults;
}
}
