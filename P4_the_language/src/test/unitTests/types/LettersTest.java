package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class LettersTest {
    
    @Test
    @DisplayName("ToString returnerer \"Letter\"")
    public void toStringLetter() {
        LetterType letters = new LetterType();
        String expected = "Letters";

        String actual = letters.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two letters objects are equal")
    public void lettersEqualsLetter() {
        LetterType firstLetter = new LetterType();
        LetterType secondLetter = new LetterType();
        boolean expected = true;

        boolean actual = firstLetter.equals(secondLetter);

        assertEquals(expected, actual);
    }
}
