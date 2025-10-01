package academy.hangman.models;

public record DifficultyConfig(
    String name,
    String displayName,
    int maxAttempts,
    int minWordLength,
    int maxWordLength,
    boolean allowSpecialChars,
    boolean caseSensitive
) {
    // Предопределенные конфигурации
    public static final DifficultyConfig EASY = new DifficultyConfig(
        "easy", "Легкий", 8, 3, 5, false, false
    );

    public static final DifficultyConfig MEDIUM = new DifficultyConfig(
        "medium", "Средний", 6, 5, 8, false, false
    );

    public static final DifficultyConfig HARD = new DifficultyConfig(
        "hard", "Сложный", 4, 7, 15, true, true
    );

    public static DifficultyConfig getByName(String name) {
        return switch (name.toLowerCase()) {
            case "easy", "легкий" -> EASY;
            case "hard", "сложный" -> HARD;
            default -> MEDIUM;
        };
    }
}


