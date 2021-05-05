package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class EmptyTest {
    
    @Test
    @DisplayName("ToString returnerer \"Empty\" når den typen er Empty")
    public void toStringEmpty() {
        EmptyType empty = new EmptyType(false);
        String expected = "Empty";

        String actual = empty.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ToString returnerer \"Not empty\" når den typen er NotEmpty")
    public void toStringNotEmpty() {
        EmptyType empty = new EmptyType(true);
        String expected = "Not Empty";

        String actual = empty.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two empty objects are equal")
    public void emptyEqualsEmpty() {
        EmptyType firstEmpty = new EmptyType(false);
        EmptyType secondEmpty = new EmptyType(false);
        boolean expected = true;

        boolean actual = firstEmpty.equals(secondEmpty);

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Two NotEmpty objects are equal")
    public void notEmptyEqualsNotEmpty() {
        EmptyType firstEmpty = new EmptyType(true);
        EmptyType secondEmpty = new EmptyType(true);
        boolean expected = true;

        boolean actual = firstEmpty.equals(secondEmpty);

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("An empty and not empty object are not equal")
    public void emptyNotEqualNotEmpty() {
        EmptyType firstEmpty = new EmptyType(false);
        EmptyType secondEmpty = new EmptyType(true);
        boolean expected = false;

        boolean actual = firstEmpty.equals(secondEmpty);

        assertEquals(expected, actual);
    }
}
