package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class DecimalTest {
    
    @Test
    @DisplayName("ToString returnerer \"Decimal\"")
    public void toStringDecimal() {
        DecimalType decimal = new DecimalType();
        String expected = "Decimal";

        String actual = decimal.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Minimum value set correctly")
    public void setMinimumValue() {
        DecimalType decimal = new DecimalType();
        String expected = "10.0";

        decimal.SetValue(">", 10.0);
        String actual = decimal.toString("min");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Minimum value including value set correctly")
    public void setMinimumValueInclusive() {
        DecimalType decimal = new DecimalType();
        String expected = "10.0";

        decimal.SetValue(">=", 10.0);
        String actual = decimal.toString("min");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Maximum value set correctly")
    public void setMaximumValue() {
        DecimalType decimal = new DecimalType();
        String expected = "10.0";

        decimal.SetValue("<", 10.0);
        String actual = decimal.toString("max");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Maximum value including value set correctly")
    public void setMaximumValueInclusive() {
        DecimalType decimal = new DecimalType();
        String expected = "10.0";

        decimal.SetValue("<=", 10.0);
        String actual = decimal.toString("max");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Equal value set correctly")
    public void setEqualValue() {
        DecimalType decimal = new DecimalType();
        String expected = "[10.0]";

        decimal.SetValue("=", 10.0);
        String actual = decimal.toString("equal");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Multiple equal values set correctly")
    public void setMultipleEqualValues() {
        DecimalType decimal = new DecimalType();
        String expected = "[10.0, 11.0]";

        decimal.SetValue("=", 10.0);
        decimal.SetValue("=", 11.0);
        String actual = decimal.toString("equal");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two decimal objects are equal")
    public void decimalEqualsDecimal() {
        DecimalType firstDecimal = new DecimalType();
        DecimalType secondDecimal = new DecimalType();
        boolean expected = true;

        boolean actual = firstDecimal.equals(secondDecimal);

        assertEquals(expected, actual);
    }
}
