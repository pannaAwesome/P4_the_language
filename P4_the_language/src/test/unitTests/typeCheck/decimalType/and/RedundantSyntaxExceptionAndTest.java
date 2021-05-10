package src.test.unitTests.typeCheck.decimalType.and;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import src.classes.exceptions.RedundantSyntaxException;
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
    @DisplayName("Test = 10.0 AND Test > 9.0")
    public void equalAndBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 9.0);
        CreateAndExpression("=", 10.0, ">", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test = 10.0 AND test > 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 AND Test >= 10.0")
    public void equalAndBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 10.0);
        CreateAndExpression("=", 10.0, ">=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test = 10.0 AND test >= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 AND Test < 11.0")
    public void equalAndSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 11.0);
        CreateAndExpression("=", 10.0, "<", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test = 10.0 AND test < 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 AND Test <= 10.0")
    public void equalAndSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 10.0);
        CreateAndExpression("=", 10.0, "<=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test = 10.0 AND test <= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 AND Test = 11.0")
    public void biggerThanAndEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 11.0);
        CreateAndExpression(">", 10.0, "=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 11.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test > 10.0 AND test = 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 AND Test > 11.0")
    public void biggerThanAndBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 11.0);
        CreateAndExpression(">", 10.0, ">", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 and bigger than 11.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 AND test > 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 AND Test >= 11.0")
    public void biggerThanAndBiggerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">=", 11.0);
        CreateAndExpression(">", 10.0, ">=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 and bigger than or equal to 11.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 AND test >= 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 AND Test = 10.0")
    public void biggerThanOrEqualAndEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 10.0);
        CreateAndExpression(">=", 10.0, "=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test >= 10.0 AND test = 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 AND Test > 9.0")
    public void biggerThanOrEqualAndBiggerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue(">", 9.0);
        CreateAndExpression(">=", 10.0, ">", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 and bigger than 9.0. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 AND test > 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 AND Test = 10.0")
    public void smallerThanAndEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 10.0);
        CreateAndExpression("<", 10.0, "=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test < 10.0 AND test = 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 AND Test < 11.0")
    public void smallerThanAndSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 11.0);
        CreateAndExpression("<", 10.0, "<", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 and less than 11.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 AND test < 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 AND Test <= 9.0")
    public void smallerThanAndSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 9.0);
        CreateAndExpression("<", 10.0, "<=", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 and less than or equal to 9.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 AND test <= 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 AND Test = 10.0")
    public void smallerThanOrEqualAndEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("=", 10.0);
        CreateAndExpression("<=", 10.0, "=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" will always be equal to 10.0 and can be rewritten as \"test = 10.0\"\n";
        expected += "At line: rule1: test <= 10.0 AND test = 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 AND Test < 9.0")
    public void smallerThanOrEqualAndSmallerThan() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<", 9.0);
        CreateAndExpression("<=", 10.0, "<", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 and less than 9.0. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 AND test < 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 AND Test <= 11.0")
    public void smallerThanOrEqualAndSmallerThanOrEqual() throws Exception {
        DecimalType firstDoub = new DecimalType();
        firstDoub.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondDoub = new DecimalType();
        secondDoub.SetValue("<=", 11.0);
        CreateAndExpression("<=", 10.0, "<=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 and less than or equal to 11.0 This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 AND test <= 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstDoub.compareTypesAnd( id, secondDoub, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    //#region Exceptions in and expressions
    private void CreateAndExpression(String firstOperator, Double firstValue, String secondOperator, Double secondValue) {
        AND andNode = new AND(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        if (firstOperator.equals("IS")) {
            CONSTRAINTS firstDoub = new CONSTRAINTS(4);
            firstDoub.value = "DECIMAL";
            firstExpr.jjtAddChild(firstDoub, 1);
        } else {
            FLOATY firstDoub = new FLOATY(4);
            firstDoub.value = firstValue;
            firstExpr.jjtAddChild(firstDoub, 1);
        }
        
        andNode.jjtAddChild(firstExpr, 0);

        VALEXPR secondExpr = new VALEXPR(4);
        secondExpr.value = secondOperator;
        IDEN secondId = new IDEN(3);
        secondId.value = "test";
        secondExpr.jjtAddChild(secondId, 0);
        if (secondOperator.equals("IS")) {
            CONSTRAINTS secondDoub = new CONSTRAINTS(4);
            secondDoub.value = "DECIMAL";
            secondExpr.jjtAddChild(secondDoub, 1);
        } else {
            FLOATY secondDoub = new FLOATY(4);
            secondDoub.value = secondValue;
            secondExpr.jjtAddChild(secondDoub, 1);
        }
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }
    //#endregion
}
