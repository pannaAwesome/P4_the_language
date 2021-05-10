package src.test.unitTests.typeCheck.integerType.or;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import src.classes.exceptions.RedundantSyntaxException;
import src.classes.scanner.*;
import src.classes.types.*;

public class RedundantSyntaxExceptionOrTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test IS INTEGER OR Test = 10")
    public void isOrEquals() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateOrExpression("IS", null, "=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as Integer and equal to 10. This can be simplified\n";
        expected += "At line: rule1: test IS INTEGER OR test = 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS INTEGER OR Test > 10")
    public void isOrBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 10);
        CreateOrExpression("IS", null, ">", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as Integer and bigger than 10. This can be simplified\n";
        expected += "At line: rule1: test IS INTEGER OR test > 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS INTEGER OR Test >= 10")
    public void isOrBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 10);
        CreateOrExpression("IS", null, ">=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as Integer and bigger than or equal to 10. This can be simplified\n";
        expected += "At line: rule1: test IS INTEGER OR test >= 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS INTEGER OR Test < 10")
    public void isOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        CreateOrExpression("IS", null, "<", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as Integer and less than 10. This can be simplified\n";
        expected += "At line: rule1: test IS INTEGER OR test < 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS INTEGER OR Test <= 10")
    public void isOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 10);
        CreateOrExpression("IS", null, "<=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as Integer and less than or equal to 10. This can be simplified\n";
        expected += "At line: rule1: test IS INTEGER OR test <= 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 OR Test IS INTEGER")
    public void equalsOrIs() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10); 

        String id = "test";
        IntegerType secondInt = new IntegerType();
        CreateOrExpression("=", 10, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as 10 and Integer. This can be simplified\n";
        expected += "At line: rule1: test = 10 OR test IS INTEGER\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 OR Test > 9")
    public void equalsOrBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        firstInt.SetValue(">", 9);
        CreateOrExpression("=", 10, ">", 9);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as 10 and bigger than 9. This can be simplified\n";
        expected += "At line: rule1: test = 10 OR test > 9\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 OR Test >= 10")
    public void equalsOrBiggerThanOrEquals() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        firstInt.SetValue(">=", 10);
        CreateOrExpression("=", 10, ">=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as 10 and bigger than or equal to 10. This can be simplified\n";
        expected += "At line: rule1: test = 10 OR test >= 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 OR Test < 11")
    public void equalsOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        firstInt.SetValue("<", 11);
        CreateOrExpression("=", 10, "<", 11);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as 10 and less than 11. This can be simplified\n";
        expected += "At line: rule1: test = 10 OR test < 11\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 OR Test <= 11")
    public void equalsOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        firstInt.SetValue("<=", 10);
        CreateOrExpression("=", 10, "<=", 10);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has already been defined as 10 and less than or equal to 10. This can be simplified\n";
        expected += "At line: rule1: test = 10 OR test <= 10\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    //#region Exceptions in or expressions
    private void CreateOrExpression(String firstOperator, Integer firstValue, String secondOperator, Integer secondValue) {
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
