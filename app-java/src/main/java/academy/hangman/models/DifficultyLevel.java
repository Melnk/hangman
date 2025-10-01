package academy.hangman.models;

public enum DifficultyLevel {
    EASY(1, "Легкий", 8, 1),
    MEDIUM(2, "Средний", 6, 7),
    HARD(3, "Сложный", 4, 5);

    private final int id;
    private final String displayName;
    private final int maxAttempts;
    private final int minWordLength;

    DifficultyLevel(int id, String displayName, int maxAttempts, int minWordLength) {
        this.id = id;
        this.displayName = displayName;
        this.maxAttempts = maxAttempts;
        this.minWordLength = minWordLength;
    }

    public int getId() { return id; }
    public String getDisplayName() { return displayName; }
    public int getMaxAttempts() { return maxAttempts; }
    public int getMinWordLength() { return minWordLength; }

    public static DifficultyLevel fromString(String name) {
        for (DifficultyLevel level : values()) {
            if (level.name().equalsIgnoreCase(name) ||
                level.displayName.equalsIgnoreCase(name)) {
                return level;
            }
        }
        return MEDIUM;
    }
}


