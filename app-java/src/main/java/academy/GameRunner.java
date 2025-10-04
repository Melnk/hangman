package academy;

import academy.hangman.models.DifficultyLevel;
import academy.hangman.services.GameSession;
import academy.hangman.services.WordGenerator;
import java.util.Scanner;

public class GameRunner {

    private final Scanner scanner;
    private final WordGenerator generator;

    public GameRunner() {
        this.scanner = new Scanner(System.in);
        this.generator = new WordGenerator();
    }

    public void run(String[] args) {
        if (args.length == 2) {
            runTestMode(args[0], args[1]);
        } else {
            runInteractiveMode();
        }
    }

    private void runInteractiveMode() {
        DifficultyLevel difficultyLevel = chooseDifficulty();
        String secretWord = chooseCategoryAndWord(difficultyLevel);

        GameSession session = new GameSession(secretWord, difficultyLevel.getMaxAttempts());
        System.out.println("\nИгра началась! Слово загадано. Сосал?\n");

        // TODO:
    }

    private DifficultyLevel chooseDifficulty() {
        System.out.println("Вебрите сложность(легкий, средний, сложный) или Enter для среднего: ");
        String diffInput = scanner.nextLine().toLowerCase().trim();
        if (diffInput.isEmpty()) {
            return DifficultyLevel.MEDIUM;
        } else {
            return DifficultyLevel.fromString(diffInput);
        }
    }

    private String chooseCategoryAndWord(DifficultyLevel diff){
        System.out.println("Доступные категории: " + generator.getCategories().keySet());
        System.out.println("Введите категорию или Enter для случайности: ");
        String categoryInput = scanner.nextLine().trim();

        if (!categoryInput.isEmpty()) {
            return generator.getRandomWordFromCategory(categoryInput, diff);
        } else {
            return generator.getRandomWord(diff);
        }
    }
    private void runTestMode(String secretWord, String guessed) {
        GameSession session = new GameSession(secretWord, 6);
        for (char c : guessed.toLowerCase().toCharArray()) {
            session.guessLetter(c);
        }

        String masked = session.getCurrentMaskedWord();
        String result;
        if (session.isWon()) {
            result = "Победа!";
        } else if (session.isLost()) {
            result = "Поражение!";
        } else {
            result = "Продолжаем!!!";
        }
        System.out.println(masked + "; " + result);
    }
}
