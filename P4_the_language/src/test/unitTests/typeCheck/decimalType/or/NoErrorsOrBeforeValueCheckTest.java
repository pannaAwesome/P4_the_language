package src.test.unitTests.typeCheck.decimalType.or;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.scanner.*;
import src.classes.types.*;

public class NoErrorsOrBeforeValueCheckTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test = 10.0 OR Test > 11.0")
    public void equalsOrBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 11.0);

        List<Double> expected = new ArrayList<Double>();
        expected.add(10.0);
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        List<Double> actual = firstDoub.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test = 10.0 OR Test >= 12.0")
    public void equalsOrBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 12.0);

        List<Double> expected = new ArrayList<Double>();
        expected.add(10.0);
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        List<Double> actual = firstDoub.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test = 10.0 OR Test < 9.0")
    public void equalsOrSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 9.0);

        List<Double> expected = new ArrayList<Double>();
        expected.add(10.0);
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        List<Double> actual = firstDoub.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test = 10.0 OR Test <= 8.0")
    public void equalsOrSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 8.0);

        List<Double> expected = new ArrayList<Double>();
        expected.add(10.0);
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        List<Double> actual = firstDoub.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test > 10.0 OR Test = 9.0")
    public void biggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 9.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10.0 OR Test < 9.0")
    public void biggerThanOrSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 9.0);

        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10.0 OR Test <= 9.0")
    public void biggerThanOrSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 9.0);

        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test = 8.0")
    public void biggerThanOrEqualOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 8.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test < 9.0")
    public void biggerThanOrEqualOrSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 9.0);

        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test <= 8.0")
    public void biggerThanOrEqualOrSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 8.0);

        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0 OR Test = 11.0")
    public void smallerThanOrEquals() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 11.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0 OR Test > 11.0")
    public void smallerThanOrBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 11.0);

        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0 OR Test >= 11.0")
    public void smallerThanOrBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 11.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;
        
        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test = 12.0")
    public void smallerThanOrEqualsOrEquals() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 12.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test > 11.0")
    public void smallerThanOrEqualOrBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 11.0);

        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test >= 12.0")
    public void smallerThanOrEqualsOrBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 12.0);

        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }
}
