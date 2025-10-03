package academy.hangman.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class HangmanDisplayTest {

    @Test
    @DisplayName("Should print correct hangman stage for valid attempts")
    void printHangman_ValidAttempts_PrintsCorrectStage() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act
            HangmanDisplay.printHangman(6, 6); // Stage 0
            String result = outputStream.toString();

            // Assert
            assertTrue(result.contains("+---+"));
            assertTrue(result.contains("|   |"));
            assertFalse(result.contains("O")); // No body parts at stage 0
        } finally {
            System.setOut(originalOut);
        }
    }

    @ParameterizedTest
    @DisplayName("Should handle boundary values correctly")
    @CsvSource({
        "0, 6", // Last stage (game over)
        "6, 6", // First stage (no mistakes)
        "3, 6"  // Middle stage
    })
    void printHangman_BoundaryValues_PrintsWithoutException(int attemptsLeft, int maxAttempts) {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act & Assert - should not throw exception
            assertDoesNotThrow(() -> HangmanDisplay.printHangman(attemptsLeft, maxAttempts));

            String result = outputStream.toString();
            assertNotNull(result);
            assertFalse(result.isEmpty());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Should cap stage at maximum image index when attempts exceed image count")
    void printHangman_ExcessiveAttempts_CapsAtMaxStage() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act - This would calculate stage = 10 - 0 = 10, but should cap at 6
            HangmanDisplay.printHangman(0, 10);
            String result = outputStream.toString();

            // Assert - Should show the final stage with full body
            assertTrue(result.contains("/ \\")); // Final stage has both legs
            assertTrue(result.contains("/|\\")); // Final stage has both arms
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Should handle negative attempts by capping at first stage")
    void printHangman_NegativeAttempts_CapsAtFirstStage() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act - Negative attempts left
            HangmanDisplay.printHangman(-1, 6);
            String result = outputStream.toString();

            // Assert - Should show the final stage (capped)
            assertTrue(result.contains("/ \\")); // Final stage elements
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Should handle zero max attempts")
    void printHangman_ZeroMaxAttempts_PrintsFinalStage() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act
            HangmanDisplay.printHangman(0, 0);
            String result = outputStream.toString();

            // Assert - Should cap at final stage
            assertTrue(result.contains("/ \\")); // Final stage elements
        } finally {
            System.setOut(originalOut);
        }
    }
}
