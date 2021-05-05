package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class IntegerTest {
    
    @Test
    @DisplayName("ToString returnerer \"Integer\"")
    public void toStringInteger() {
        IntegerType integer = new IntegerType();
        String expected = "Integer";

        String actual = integer.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Minimum value set correctly")
    public void setMinimumValue() {
        IntegerType integer = new IntegerType();
        String expected = "10";

        integer.SetValue(">", 10);
        String actual = integer.toString("min");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Minimum value including value set correctly")
    public void setMinimumValueInclusive() {
        IntegerType integer = new IntegerType();
        String expected = "10";

        integer.SetValue(">=", 10);
        String actual = integer.toString("min");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Maximum value set correctly")
    public void setMaximumValue() {
        IntegerType integer = new IntegerType();
        String expected = "10";

        integer.SetValue("<", 10);
        String actual = integer.toString("max");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Maximum value including value set correctly")
    public void setMaximumValueInclusive() {
        IntegerType integer = new IntegerType();
        String expected = "10";

        integer.SetValue("<=", 10);
        String actual = integer.toString("max");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Equal value set correctly")
    public void setEqualValue() {
        IntegerType integer = new IntegerType();
        String expected = "[10]";

        integer.SetValue("=", 10);
        String actual = integer.toString("equal");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Multiple equal values set correctly")
    public void setMultipleEqualValues() {
        IntegerType integer = new IntegerType();
        String expected = "[10, 11]";

        integer.SetValue("=", 10);
        integer.SetValue("=", 11);
        String actual = integer.toString("equal");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two integer objects are equal")
    public void integerEqualsInteger() {
        IntegerType firstInteger = new IntegerType();
        IntegerType secondInteger = new IntegerType();
        boolean expected = true;

        boolean actual = firstInteger.equals(secondInteger);

        assertEquals(expected, actual);
    }
}
