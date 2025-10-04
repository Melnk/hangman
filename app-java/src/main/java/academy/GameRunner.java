package academy;

import academy.hangman.models.DifficultyLevel;
import academy.hangman.services.GameSession;
import academy.hangman.services.WordGenerator;
import academy.hangman.ui.HangmanDisplay;
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
        } else if (args.length == 0) {
            runInteractiveMode();
        } else {
            System.out.println("Неверное количество аргументов.");
            System.out.println("Использование:");
            System.out.println("java -jar hangman.jar              # интерактивный режим");
            System.out.println("java -jar hangman.jar кот ко       # тестовый режим");
        }
    }

    private void runInteractiveModeMenu() {
        System.out.println("Выберите режим игры:");
        System.out.println("1. Компьютер загадывает слово");
        System.out.println("2. Вы сами загадываете слово");
        System.out.print("Введите 1 или 2: ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                runInteractiveMode();
                break;
            case "2":
                runUserWordMode();
                break;
            default:
                System.out.println("Неверный выбор. Запускаем обычный режим.");
                runInteractiveMode();
        }
    }

    private void runUserWordMode() {
        System.out.println("Введите слово, которое нужно загадать: ");
        String secretWord = scanner.nextLine().trim().toLowerCase();

        for (int i = 0; i < 50; i++) System.out.println();

        DifficultyLevel difficultyLevel = chooseDifficulty();

        GameSession session = new GameSession(secretWord, difficultyLevel.getMaxAttempts());
        System.out.println("\nСлово загадано! Начинаем игру.\n");

        while (!session.isLost() && !session.isWon()) {
            HangmanDisplay.printHangman(session.getAttemptsLeft(), session.getMaxAttempts());
            System.out.println("Слово: " + session.getCurrentMaskedWord());
            System.out.println("Осталось попыток: " + session.getAttemptsLeft());
            System.out.print("Введите букву: ");

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.length() != 1) {
                System.out.println("Введите только одну букву!");
                continue;
            }

            char guess = input.charAt(0);
            boolean correct = session.guessLetter(guess);

            if (correct) {
                System.out.println("Верно!");
            } else {
                System.out.println("Не угадал!");
            }
        }

        if (session.isWon()) {
            System.out.println("Поздравляем! Вы угадали слово: " + session.getSecretWord());
        } else {
            HangmanDisplay.printHangman(session.getAttemptsLeft(), session.getMaxAttempts());
            System.out.println("Вы проиграли. Слово было: " + session.getSecretWord());
        }
    }


    private void runInteractiveMode() {
        DifficultyLevel difficultyLevel = chooseDifficulty();
        String secretWord = chooseCategoryAndWord(difficultyLevel);

        GameSession session = new GameSession(secretWord, difficultyLevel.getMaxAttempts());
        System.out.println("\nИгра началась! Слово загадано. Сосал?\n");

        while (!session.isLost() && !session.isWon()){
            HangmanDisplay.printHangman(session.getAttemptsLeft(), session.getMaxAttempts());
            System.out.println("Слово: " + session.getCurrentMaskedWord());
            System.out.println("Осталось попыток: " + session.getAttemptsLeft());
            System.out.println("Введите букву: ");

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.length() != 1) {
                System.out.println("Введите только одну букву!");
                continue;
            }

            char guess = input.charAt(0);
            boolean correct = session.guessLetter(guess);

            if (correct) {
                System.out.println("Верно");
            } else {
                System.out.println("Не угадал");
            }
        }

        if (session.isWon()) {
            System.out.println("Поздравляем вы отгодали слово: " + session.getSecretWord());
        } else {
            HangmanDisplay.printHangman(session.getAttemptsLeft(), session.getMaxAttempts());
            System.out.println("Вы проиграли, слово было: " + session.getSecretWord());
        }
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
