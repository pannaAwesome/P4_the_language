package src.test.unitTests.typeCheck.integerType.or;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.scanner.*;
import src.classes.types.*;

public class NoErrorsOrIntTypeTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test = 10 OR Test = 11")
    public void equalsOrEquals() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 11);
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        expected.add(11);
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test = 10 OR Test > 11")
    public void equalsOrBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 11);

        int expected = 11;
        boolean expectedWith = false;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test = 10 OR Test >= 12")
    public void equalsOrBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 12);

        int expected = 12;
        boolean expectedWith = true;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;
        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test = 10 OR Test < 9")
    public void equalsOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 9);

        int expected = 9;
        boolean expectedWith = false;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test = 10 OR Test <= 8")
    public void equalsOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 8);

        int expected = 8;
        boolean expectedWith = true;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10 OR Test = 9")
    public void biggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 9);
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(9);
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test > 10 OR Test < 9")
    public void biggerThanOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 9);

        int expected = 9;
        boolean expectedWith = false;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10 OR Test <= 9")
    public void biggerThanOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 9);

        int expected = 9;
        boolean expectedWith = true;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10 OR Test = 8")
    public void biggerThanOrEqualOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 8);
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(8);
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test >= 10 OR Test < 9")
    public void biggerThanOrEqualOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 9);

        int expected = 9;
        boolean expectedWith = false;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10 OR Test <= 8")
    public void biggerThanOrEqualOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 8);

        int expected = 8;
        boolean expectedWith = true;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10 OR Test = 11")
    public void smallerThanOrEquals() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 11);
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(11);
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test < 10 OR Test > 11")
    public void smallerThanOrBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 11);

        int expected = 11;
        boolean expectedWith = false;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10 OR Test >= 11")
    public void smallerThanOrBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 11);

        int expected = 11;
        boolean expectedWith = true;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10 OR Test = 12")
    public void smallerThanOrEqualsOrEquals() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 12);
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(12);
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test <= 10 OR Test > 11")
    public void smallerThanOrEqualOrBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 11);

        int expected = 11;
        boolean expectedWith = false;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10 OR Test >= 12")
    public void smallerThanOrEqualsOrBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 12);

        int expected = 12;
        boolean expectedWith = true;
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    //#region Exceptions in or expressions
    private void CreateOrExpression(String firstOperator, int firstValue, String secondOperator, int secondValue) {
        OR andNode = new OR(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        STRING firstInt = new STRING(4);
        firstInt.value = firstValue;
        firstExpr.jjtAddChild(firstInt, 1);
        andNode.jjtAddChild(firstExpr, 0);

        VALEXPR secondExpr = new VALEXPR(4);
        secondExpr.value = secondOperator;
        IDEN secondId = new IDEN(3);
        secondId.value = "test";
        secondExpr.jjtAddChild(secondId, 0);
        STRING secondInt = new STRING(4);
        secondInt.value = secondValue;
        secondExpr.jjtAddChild(secondInt, 1);
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }
    //#endregion
}
