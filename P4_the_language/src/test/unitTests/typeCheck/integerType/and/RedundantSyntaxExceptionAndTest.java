package src.test.unitTests.typeCheck.integerType.and;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.exceptions.RedundantSyntaxException;
import src.classes.exceptions.DuplicationException;
import src.classes.scanner.*;
import src.classes.types.*;

public class RedundantSyntaxExceptionAndTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test = 10 AND Test > 9")
    public void equalAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 9);
        CreateAndExpression("=", 10, ">", 9);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test = 10 AND test > 9\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test >= 10")
    public void equalAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 10);
        CreateAndExpression("=", 10, ">=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test = 10 AND test >= 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test < 11")
    public void equalAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 11);
        CreateAndExpression("=", 10, "<", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test = 10 AND test < 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test <= 10")
    public void equalAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 10);
        CreateAndExpression("=", 10, "<=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test = 10 AND test <= 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test = 11")
    public void biggerThanAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 11);
        CreateAndExpression(">", 10, "=", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 11 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test > 10 AND test = 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test > 11")
    public void biggerThanAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 11);
        CreateAndExpression(">", 10, ">", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as bigger than 10 and bigger than 11. This can be simplified\n";
        expected += "At line: rule1: test > 10 AND test > 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test >= 11")
    public void biggerThanAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 11);
        CreateAndExpression(">", 10, ">=", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as bigger than 10 and bigger than or equal to 11. This can be simplified\n";
        expected += "At line: rule1: test > 10 AND test >= 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10 AND Test = 10")
    public void biggerThanOrEqualAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateAndExpression(">=", 10, "=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test >= 10 AND test = 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10 AND Test > 9")
    public void biggerThanOrEqualAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 9);
        CreateAndExpression(">=", 10, ">", 9);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as bigger than or equal to 10 and bigger than 9. This can be simplified\n";
        expected += "At line: rule1: test >= 10 AND test > 9\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
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
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test < 10 AND test = 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 AND Test < 11")
    public void smallerThanAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 11);
        CreateAndExpression("<", 10, "<", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as less than 10 and less than 11. This can be simplified\n";
        expected += "At line: rule1: test < 10 AND test < 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 AND Test <= 9")
    public void smallerThanAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 9);
        CreateAndExpression("<", 10, "<=", 9);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as less than 10 and less than or equal to 9. This can be simplified\n";
        expected += "At line: rule1: test < 10 AND test <= 9\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 AND Test = 10")
    public void smallerThanOrEqualAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateAndExpression("<=", 10, "=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10 and can be rewritten as \"test = 10\"\n";
        expected += "At line: rule1: test <= 10 AND test = 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 AND Test < 9")
    public void smallerThanOrEqualAndSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 9);
        CreateAndExpression("<=", 10, "<", 9);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as less than or equal to 10 and less than 9. This can be simplified\n";
        expected += "At line: rule1: test <= 10 AND test < 9\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 AND Test <= 11")
    public void smallerThanOrEqualAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 11);
        CreateAndExpression("<=", 10, "<=", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as less than or equal to 10 and less than or equal to 11 This can be simplified\n";
        expected += "At line: rule1: test <= 10 AND test <= 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
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
