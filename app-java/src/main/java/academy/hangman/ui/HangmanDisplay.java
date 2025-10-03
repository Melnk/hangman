package academy.hangman.ui;

public class HangmanDisplay {
    private static final String[] HANGMAN_IMAGE = {
        """
         +---+
         |   |
             |
             |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
             |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
         |   |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|   |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|\\  |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|\\  |
        /    |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|\\  |
        / \\  |
             |
        =========
        """
    };

    public static void printHangman(int attemptsLeft, int maxAttempts) {
        int stage = maxAttempts - attemptsLeft;
        if (maxAttempts < HANGMAN_IMAGE.length - 1) {
            stage = HANGMAN_IMAGE.length - attemptsLeft - 1;
        }
        if (stage >= HANGMAN_IMAGE.length) {
            stage = HANGMAN_IMAGE.length - 1;
        }

        System.out.println(HANGMAN_IMAGE[stage]);
    }
}

