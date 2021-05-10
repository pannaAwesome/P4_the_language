package src.test.unitTests.typeCheck.decimalType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.scanner.*;
import src.classes.types.*;

public class DecimalTypeCheckTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test IS DECIMAL")
    public void isDouble() throws Exception {
        DecimalType firstDoub = new DecimalType();

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        
        boolean expected = true;
        
        boolean actual = firstDoub.compareTypesAnd( id, secondDoub, parentNode);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test = 10.0")
    public void Equals() throws Exception {
        DecimalType firstDoub = new DecimalType();

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 10.0);
        
        List<Double> expected = new ArrayList<Double>();
        expected.add(10.0);
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        List<Double> actual = firstDoub.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test > 10.0")
    public void biggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 10.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10.0")
    public void biggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 10.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.minValue;
        boolean actualWith = firstDoub.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10.0")
    public void smallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 10.0);
        
        Double expected = 10.0;
        boolean expectedWith = false;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10.0")
    public void smallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 10.0);
        
        Double expected = 10.0;
        boolean expectedWith = true;
        
        firstDoub.compareTypesAnd( id, secondDoub, parentNode);
        Double actual = firstDoub.maxValue;
        boolean actualWith = firstDoub.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }
}
