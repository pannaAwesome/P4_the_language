package src.test.unitTests.typeCheck.integerType.and;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.exceptions.ConstraintException;
import src.classes.scanner.*;
import src.classes.types.*;

public class ConstrainExceptionAndTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test = 10 AND Test = 11")
    public void equalAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 11);
        CreateAndExpression("=", 10, "=", 11);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be 11 because it is already defined as 10\n";
        expected += "At line: rule1: test = 10 AND test = 11\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test > 10")
    public void equalAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 10);
        CreateAndExpression("=", 10, ">", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be bigger than 10 because it is already defined as 10\n";
        expected += "At line: rule1: test = 10 AND test > 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test >= 11")
    public void equalAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 11);
        CreateAndExpression("=", 10, ">=", 11);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be bigger than or equal to 11 because it is already defined as 10\n";
        expected += "At line: rule1: test = 10 AND test >= 11\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test < 10")
    public void equalAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        CreateAndExpression("=", 10, "<", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be less than 10 because it is already defined as 10\n";
        expected += "At line: rule1: test = 10 AND test < 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test <= 9")
    public void equalAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 9);
        CreateAndExpression("=", 10, "<=", 9);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be less than or equal to 9 because it is already defined as 10\n";
        expected += "At line: rule1: test = 10 AND test <= 9\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test = 10")
    public void biggerThanAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateAndExpression(">", 10, "=", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be equal to 10 because it is already defined as bigger than 10\n";
        expected += "At line: rule1: test > 10 AND test = 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    // @Test
    // @DisplayName("Test > 10 AND Test >= 10")
    // public void biggerThanAndBiggerThanOrEqual() throws Exception {
    //     IntegerType firstInt = new IntegerType();
    //     firstInt.SetValue(">", 10);

    //     String id = "test";
    //     IntegerType secondInt = new IntegerType();
    //     secondInt.SetValue(">=", 10);
    //     CreateAndExpression(">", 10, ">=", 10);
        
    //     String expected = "ERROR:\n";
    //     expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be bigger than or equal to 10 because it is already defined as bigger than 10\n";
    //     expected += "At line: rule1: test > 10 AND test >= 10\n\n";
        
    //     Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
    //     assertEquals(expected, thrown.getMessage());
    // }

    @Test
    @DisplayName("Test > 10 AND Test < 10")
    public void biggerThanAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        CreateAndExpression(">", 10, "<", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 10\n";
        expected += "At line: rule1: test > 10 AND test < 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test < 11")
    public void biggerThanAndSmallerThanNoneFound() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 11);
        CreateAndExpression(">", 10, "<", 11);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: There are no integer values that are bigger than 10 and smaller than 11 for \"test\"\n";
        expected += "At line: rule1: test > 10 AND test < 11\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test <= 10")
    public void biggerThanOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 10);
        CreateAndExpression(">", 10, "<=", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 10\n";
        expected += "At line: rule1: test > 10 AND test <= 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10 AND Test = 9")
    public void biggerThanOrEqualAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 9);
        CreateAndExpression(">=", 10, "=", 9);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be  9 because it is already defined as bigger than or equal to 10\n";
        expected += "At line: rule1: test >= 10 AND test = 9\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    // @Test
    // @DisplayName("Test >= 10 AND Test > 10")
    // public void biggerThanOrEqualAndBiggerThan() throws Exception {
    //     IntegerType firstInt = new IntegerType();
    //     firstInt.SetValue(">=", 10);

    //     String id = "test";
    //     IntegerType secondInt = new IntegerType();
    //     secondInt.SetValue(">", 10);
    //     CreateAndExpression(">=", 10, ">", 10);
        
    //     String expected = "ERROR:\n";
    //     expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be bigger than 10 because it is already defined as bigger than or equal to 10\n";
    //     expected += "At line: rule1: test >= 10 AND test > 10\n\n";
        
    //     Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
    //     assertEquals(expected, thrown.getMessage());
    // }

    @Test
    @DisplayName("Test >= 10 AND Test < 10")
    public void biggerThanOrEqualAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        CreateAndExpression(">=", 10, "<", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 10\n";
        expected += "At line: rule1: test >= 10 AND test < 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10 AND Test <= 9")
    public void biggerThanOrEqualAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 9);
        CreateAndExpression(">=", 10, "<=", 9);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 9, which is smaller than the min value of 10\n";
        expected += "At line: rule1: test >= 10 AND test <= 9\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 AND Test = 10")
    public void smallerThanAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateAndExpression("<", 10, "=", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be 10 because it is already defined as less than 10\n";
        expected += "At line: rule1: test < 10 AND test = 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    // @Test
    // @DisplayName("Test < 10 AND Test <= 10")
    // public void smallerThanAndSmallerThanOrEqual() throws Exception {
    //     IntegerType firstInt = new IntegerType();
    //     firstInt.SetValue("<", 10);

    //     String id = "test";
    //     IntegerType secondInt = new IntegerType();
    //     secondInt.SetValue("<=", 10);
    //     CreateAndExpression("<", 10, "<=", 10);
        
    //     String expected = "ERROR:\n";
    //     expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be less than or equal to 10 because it is already defined as less than 10\n";
    //     expected += "At line: rule1: test < 10 AND test <= 10\n\n";
        
    //     Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
    //     assertEquals(expected, thrown.getMessage());
    // }

    @Test
    @DisplayName("Test < 10 AND Test > 10")
    public void smallerThanAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 10);
        CreateAndExpression("<", 10, ">", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 10\n";
        expected += "At line: rule1: test < 10 AND test > 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 AND Test > 9")
    public void smallerThanAndBiggerThanNoneFound() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 9);
        CreateAndExpression("<", 10, ">", 9);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: There are no integer values that are bigger than 9 and smaller than 10 for \"test\"\n";
        expected += "At line: rule1: test < 10 AND test > 9\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 AND Test >= 10")
    public void smallerThanAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 10);
        CreateAndExpression("<", 10, ">=", 10);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 10\n";
        expected += "At line: rule1: test < 10 AND test >= 10\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 AND Test = 11")
    public void smallerThanOrEqualAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 11);
        CreateAndExpression("<=", 10, "=", 11);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be  11 because it is already defined as less than or equal to 10\n";
        expected += "At line: rule1: test <= 10 AND test = 11\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    // @Test
    // @DisplayName("Test <= 10 AND Test < 10")
    // public void smallerThanOrEqualAndSmallerThan() throws Exception {
    //     IntegerType firstInt = new IntegerType();
    //     firstInt.SetValue("<=", 10);

    //     String id = "test";
    //     IntegerType secondInt = new IntegerType();
    //     secondInt.SetValue("<", 10);
    //     CreateAndExpression("<=", 10, "<", 10);
        
    //     String expected = "ERROR:\n";
    //     expected += "CONSTRAINT ERROR: Cannot redefine \"test\" to be less than 10 because it is already defined as less than or equal to 10\n";
    //     expected += "At line: rule1: test <= 10 AND test < 10\n\n";
        
    //     Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
    //     assertEquals(expected, thrown.getMessage());
    // }

    @Test
    @DisplayName("Test <= 10 AND Test > 11")
    public void smalletThanOrEqualAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 11);
        CreateAndExpression("<=", 10, ">", 11);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 11\n";
        expected += "At line: rule1: test <= 10 AND test > 11\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 AND Test >= 11")
    public void smallerThanOrEqualAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 11);
        CreateAndExpression("<=", 10, ">=", 11);
        
        String expected = "ERROR:\n";
        expected += "CONSTRAINT ERROR: \"test\" cannot have a max value of 10, which is smaller than the min value of 11\n";
        expected += "At line: rule1: test <= 10 AND test >= 11\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }


    //#region Exceptions in and expressions
    private void CreateAndExpression(String firstOperator, int firstValue, String secondOperator, int secondValue) {
        AND andNode = new AND(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        if (firstOperator.equals("IS")) {
            CONSTRAINTS firstInt = new CONSTRAINTS(4);
            firstInt.value = "INTEGER";
            firstExpr.jjtAddChild(firstInt, 1);
        } else {
            INTEGER firstInt = new INTEGER(4);
            firstInt.value = firstValue;
            firstExpr.jjtAddChild(firstInt, 1);
        }
        
        andNode.jjtAddChild(firstExpr, 0);

        VALEXPR secondExpr = new VALEXPR(4);
        secondExpr.value = secondOperator;
        IDEN secondId = new IDEN(3);
        secondId.value = "test";
        secondExpr.jjtAddChild(secondId, 0);
        if (secondOperator.equals("IS")) {
            CONSTRAINTS secondInt = new CONSTRAINTS(4);
            secondInt.value = "INTEGER";
            secondExpr.jjtAddChild(secondInt, 1);
        } else {
            INTEGER secondInt = new INTEGER(4);
            secondInt.value = secondValue;
            secondExpr.jjtAddChild(secondInt, 1);
        }
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }
    //#endregion
}
