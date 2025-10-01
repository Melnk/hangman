package academy.hangman.models;

import academy.hangman.services.WordGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void testDifficultyLevels() {
        assertEquals(8, DifficultyLevel.EASY.getMaxAttempts());
        assertEquals(6, DifficultyLevel.MEDIUM.getMaxAttempts());
        assertEquals(4, DifficultyLevel.HARD.getMaxAttempts());
    }

    @Test
    void testWordGeneratorWithDifficulty() {
        WordGenerator generator = new WordGenerator();

        String easyWord = generator.getRandomWord(DifficultyLevel.EASY);
        String hardWord = generator.getRandomWord(DifficultyLevel.HARD);

        assertTrue(easyWord.length() >= DifficultyLevel.EASY.getMinWordLength());
        assertTrue(hardWord.length() >= DifficultyLevel.HARD.getMinWordLength());
    }
}


