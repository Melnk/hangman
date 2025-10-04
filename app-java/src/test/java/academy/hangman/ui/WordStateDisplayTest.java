package academy.hangman.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class WordStateDisplayTest {


    @Test
    @DisplayName("Should handle special characters and numbers")
    void getMaskedWord_SpecialCharacters_WorksCorrectly() {
        assertEquals("123**", WordStateDisplay.getMaskedWord("12345", "123xx"));
        assertEquals("*****", WordStateDisplay.getMaskedWord("test!", "xxx!x"));
        assertEquals("c**é", WordStateDisplay.getMaskedWord("café", "cxxéx"));
    }


    // Параметризованные тесты для различных сценариев
    @ParameterizedTest
    @CsvSource({
            "apple, axxle, a**le",
            "hello, hxxlo, h**lo",
            "test, test, test",
            "java, jxvx, j*v*",
            "a, a, a",
            "a, x, *"
    })
    @DisplayName("Parameterized tests for various word combinations")
    void getMaskedWord_VariousCombinations_ReturnsExpected(
            String secret, String guesses, String expected) {
        assertEquals(expected, WordStateDisplay.getMaskedWord(secret, guesses));
    }
}
