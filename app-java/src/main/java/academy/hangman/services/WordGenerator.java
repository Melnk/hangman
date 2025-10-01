package academy.hangman.services;

import academy.hangman.models.DifficultyConfig;
import academy.hangman.models.DifficultyLevel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordGenerator {
    private final Map<String, List<String>> categories;
    private final Map<DifficultyLevel, List<String>> wordsByDifficulty;
    private final Random random;
    private final Path WORDS_DIRECTORY_PATH = Paths.get("src", "main", "resources", "words");

    public WordGenerator() {
        this.categories = loadCategories();
        this.wordsByDifficulty = categorizeWordsByDifficulty();
        this.random = new Random();
    }

    private Map<String, List<String>> loadCategories() {
        Map<String, List<String>> loadedCategories = new HashMap<>();

        if (Files.exists(WORDS_DIRECTORY_PATH)) {
            System.err.println("Заданная папка не найдена: " + WORDS_DIRECTORY_PATH);
            return backupDictionary();
        }

        try {
            Files.list(WORDS_DIRECTORY_PATH)
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(file -> {
                    String categoryName = getCategoryName(file);
                    try {
                        List<String> words = Files.readAllLines(file)
                            .stream()
                            .map(String::trim) // Удаляет начальные и конечные пробелы
                            .filter(word -> !word.isEmpty())
                            .map(String::toLowerCase)
                            .toList();
                        loadedCategories.put(categoryName, words);
                    } catch (IOException e) {
                        System.err.println("Files.readAllLines не отработал");
                        throw new RuntimeException(e);
                    }
                });
        } catch (IOException e) {
            System.err.println("Files.list не отработал");
            throw new RuntimeException(e);
        }

        return loadedCategories;
    }

    private Map<String, List<String>> backupDictionary() {
        Map<String, List<String>> backupMap = new HashMap<>();

        backupMap.put("animals", Arrays.asList(
            "слон", "сосал", "жираф", "кролик", "лев"
        ));

        backupMap.put("countries", Arrays.asList(
            "россия", "сша", "германия", "беларусь", "украина"
        ));

        backupMap.put("food", Arrays.asList(
            "пицца", "суши", "картошка", "котлета", "курица"
        ));

        backupMap.put("hard", Arrays.asList(
            "водопровод", "реализовать", "прокрастинация", "оркестрация", "субординация"
        ));

        backupMap.put("random", Arrays.asList(
            "стол", "шершень", "стул", "пол", "дом"
        ));

        return backupMap;
    }

    private Map<DifficultyLevel, List<String>> categorizeWordsByDifficulty() {
        Map<DifficultyLevel, List<String>> categorized = new EnumMap<>(DifficultyLevel.class);

        List<String> allWords = categories.values().stream()
            .flatMap(List::stream)
            .distinct()
            .toList();


        for (DifficultyLevel level : DifficultyLevel.values()) {
            List<String> wordsForLevel = filterWordsForDifficult(allWords, level);
            categorized.put(level, wordsForLevel);
        }


        return categorized;
    }

    private List<String> filterWordsForDifficult(List<String> words, DifficultyLevel level) {
        return words.stream()
            .filter(word -> isCorrectLevelForWord(word, level))
            .toList();
    }

    private boolean isCorrectLevelForWord(String word, DifficultyLevel level) {
        boolean hasRareLetters = hasRareLetters(word);
        boolean hasComplexStructure = hasComplexStructure(word);

        return switch (level) {
            case EASY -> word.length() >= level.getMinWordLength() &&
                !hasComplexStructure && !hasRareLetters && word.length() <= 5;
            case MEDIUM -> word.length() >= level.getMinWordLength() &&
                word.length() <= 8 &&
                !hasComplexStructure;
            case HARD -> word.length() >= level.getMinWordLength() ||
                hasRareLetters || hasComplexStructure;
        };
    }


    private boolean hasComplexStructure(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == word.charAt(i + 1)) {
                return true;
            }
        }

        String complexPatterns = "ств|здн|стн|рдц|стл";
        return word.matches(".*(" + complexPatterns + ").*");
    }

    private boolean hasRareLetters(String word) {
        String rareLetters = "фэщцюшжхч";
        return word.chars().anyMatch(ch -> rareLetters.indexOf(ch) >= 0);
    }

    public String getRandomWord() {
        return getRandomWord(DifficultyLevel.MEDIUM);
    }

    public String getRandomWord(DifficultyLevel level) {
        List<String> words = wordsByDifficulty.get(level);

        if (words.isEmpty()) {
            System.err.println("Нет слов сложности " + level.getDisplayName() +
                ". Будет использована средняя сложность");
            return getRandomWord(DifficultyLevel.MEDIUM);
        }

        return words.get(random.nextInt(words.size()));
    }

    public String getRandomWordFromCategory(String category,  DifficultyLevel level) {
        List<String> categoryWords = categories.get(category);

        if (categoryWords.isEmpty() || categoryWords.size() == 0) {
            System.err.println("Категория не найдена: " + category);
            return getRandomWord(level);
        }

        List<String> inter = categoryWords.stream()
            .filter(word -> isCorrectLevelForWord(word, level))
            .toList();

        return inter.get(random.nextInt(inter.size()));
    }




    /*
    ----------------------------------------------------------------------------------
                                 ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    ----------------------------------------------------------------------------------
     */

    private String getCategoryName(Path file) {
        String categoryName = file.getFileName().toString();
        return categoryName.substring(0, categoryName.indexOf("."));
    }

    /*
    ----------------------------------------------------------------------------------
                                 Геттеры
    ----------------------------------------------------------------------------------
     */

    public Map<String, List<String>> getCategories() {
        return categories;
    }

    public Map<DifficultyLevel, List<String>> getWordsByDifficulty() {
        return wordsByDifficulty;
    }

    public Path getWORDS_DIRECTORY_PATH() {
        return WORDS_DIRECTORY_PATH;
    }
}
