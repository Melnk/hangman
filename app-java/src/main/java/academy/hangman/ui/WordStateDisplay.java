package academy.hangman.ui;

public class WordStateDisplay {

    public static String getMaskedWord(String secretWord, String guessedLetters) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guessedLetters.charAt(i)) {
                sb.append(secretWord.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString().trim();
    }
}
