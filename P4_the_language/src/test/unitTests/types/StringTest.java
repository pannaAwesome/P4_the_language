package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class StringTest {
    
    @Test
    @DisplayName("ToString returnerer \"String\"")
    public void toStringString() {
        StringType string = new StringType();
        String expected = "String";

        String actual = string.toString();

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Contains string value set correctly")
    public void containsValuesSetCorrect() {
        StringType string = new StringType();
        List<String> expected = new ArrayList<String>();
        expected.add("hej");

        string.setStringValues("CONTAINS", "hej");
        List<String> actual = string.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Equal string value set correctly")
    public void equalValuesSetCorrect() {
        StringType string = new StringType();
        List<String> expected = new ArrayList<String>();
        expected.add("hej");

        string.setStringValues("=", "hej");
        List<String> actual = string.exactValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Two string objects are equal")
    public void stringEqualsString() {
        StringType firstString = new StringType();
        StringType secondString = new StringType();
        boolean expected = true;

        boolean actual = firstString.equals(secondString);

        assertEquals(expected, actual);
    }
}
