package src.test.unitTests.typeCheck.integerType.and;

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
    @DisplayName("Test = 10 AND Test IS INTEGER")
    public void equalsAndIs() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        
        firstInt.compareTypesOr( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test > 10 AND Test IS INTEGER")
    public void biggerThanAndIs() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        
        Integer expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10 AND Test < 15")
    public void biggerThanAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 15);
        
        Integer expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test > 10 AND Test <= 15")
    public void biggerThanAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 15);
        
        Integer expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10 AND Test IS INTEGER")
    public void biggerThanOrEqualAndIs() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        
        Integer expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10 AND Test < 15")
    public void biggerThanOrEqualAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 15);
        
        Integer expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10 AND Test <= 15")
    public void biggerThanOrEqualAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 15);
        
        Integer expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10 AND Test IS INTEGER")
    public void smallerThanAndIs() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        
        Integer expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10 AND Test > 15")
    public void smallerThanAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 15);
        
        Integer expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10 AND Test >= 15")
    public void  smallerThanAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 15);
        
        Integer expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10 AND Test IS INTEGER")
    public void smallerThanOrEqualAndIs() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        
        Integer expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10 AND Test > 15")
    public void smallerThanOrEqualAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 15);
        
        Integer expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10 AND Test >= 15")
    public void  smallerThanOrEqualAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 15);
        
        Integer expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        Integer actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    //#region Exceptions in and expressions
    private void CreateAndExpression(String firstOperator, Integer firstValue, String secondOperator, Integer secondValue) {
        AND andNode = new AND(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        INTEGER firstInt = new INTEGER(4);
        firstInt.value = firstValue;
        firstExpr.jjtAddChild(firstInt, 1);
        andNode.jjtAddChild(firstExpr, 0);

        VALEXPR secondExpr = new VALEXPR(4);
        secondExpr.value = secondOperator;
        IDEN secondId = new IDEN(3);
        secondId.value = "test";
        secondExpr.jjtAddChild(secondId, 0);
        INTEGER secondInt = new INTEGER(4);
        secondInt.value = secondValue;
        secondExpr.jjtAddChild(secondInt, 1);
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }
    //#endregion
}
