package src.test.unitTests.typeCheck.decimalType.and;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.scanner.*;
import src.classes.types.*;

public class NoErrorsAndBeforeValuesCheckTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test = 10.0 AND Test IS DECIMAL")
    public void equalsAndIs() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        
        List<Double> expected = new ArrayList<Double>();
        expected.add(10.0);
        
        firstDoub.compareTypesOr( id, secondDoub, parentNode);
        List<Double> actual = firstDoub.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test > 10.0 AND Test IS DECIMAL")
    public void biggerThanAndIs() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10.0 AND Test < 11.0")
    public void biggerThanAndSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 11.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10.0 AND Test <= 11.0")
    public void biggerThanAndSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 11.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0 AND Test IS DECIMAL")
    public void biggerThanOrEqualAndIs() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0 AND Test < 11.0")
    public void biggerThanOrEqualAndSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 11.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0 AND Test <= 11.0")
    public void biggerThanOrEqualAndSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 11.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0 AND Test IS DECIMAL")
    public void smallerThanAndIs() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0 AND Test > 9.0")
    public void smallerThanAndBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 9.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0 AND Test >= 9.0")
    public void  smallerThanAndBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 9.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0 AND Test IS DECIMAL")
    public void smallerThanOrEqualAndIs() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0 AND Test > 9.0")
    public void smallerThanOrEqualAndBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 9.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0 AND Test >= 9.0")
    public void  smallerThanOrEqualAndBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 9.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }
}
