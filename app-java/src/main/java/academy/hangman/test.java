package academy.hangman;

import academy.hangman.services.WordGenerator;
import java.io.FileNotFoundException;

public class test {
    public static void main(String[] args) throws FileNotFoundException {
        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.getRandomWord();
    }
}
