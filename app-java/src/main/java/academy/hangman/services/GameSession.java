package academy.hangman.services;

import academy.hangman.ui.WordStateDisplay;

public class GameSession {
    private final String secretWord;
    private final int maxAttempts;
    private int attemptsLeft;
    private String guessedLetters;

    public GameSession(String secretWord, int maxAttempts) {
        if (secretWord == null || secretWord.isEmpty()) {
            throw new IllegalArgumentException("Секретное слово не должно быть пустым!");
        }
        this.secretWord = secretWord.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.attemptsLeft = maxAttempts;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.secretWord.length(); i++) sb.append('*');
        this.guessedLetters = sb.toString();
    }

    public boolean guessLetter(char letter) {
        letter = Character.toLowerCase(letter);
        if (guessedLetters.indexOf(letter) >= 0) {
            return true;
        }

        boolean found = false;
        StringBuilder sb = new StringBuilder(guessedLetters);

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                sb.setCharAt(i, letter);
                found = true;
            }
        }

        guessedLetters = sb.toString();

        if (!found) {
            attemptsLeft--;
        }
        return found;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getGuessedLetters() {
        return guessedLetters;
    }

    public String getCurrentMaskedWord() {
        return WordStateDisplay.getMaskedWord(secretWord, guessedLetters);
    }

    public boolean isWon() {
        return !WordStateDisplay.getMaskedWord(secretWord, guessedLetters).contains("*");
    }

    public boolean isLost() {
        return attemptsLeft == 0;
    }
}
