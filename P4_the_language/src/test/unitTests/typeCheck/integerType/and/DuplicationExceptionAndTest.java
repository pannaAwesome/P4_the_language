package src.test.unitTests.typeCheck.integerType.and;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.exceptions.DuplicationException;
import src.classes.scanner.*;
import src.classes.types.*;

public class DuplicationExceptionAndTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test IS INTEGER AND Test IS INTEGER")
    public void isAndIs() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        CreateAndExpression("IS", null, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"Test\" has already been defined as Integer\n";
        expected += "At line: rule1: test IS INTEGER AND test IS INTEGER\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 AND Test = 10")
    public void equalAndEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateAndExpression("=", 10, "=", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined as 10\n";
        expected += "At line: rule1: test = 10 AND test = 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 AND Test > 10")
    public void biggerThanAndBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 10);
        CreateAndExpression(">", 10, ">", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"Test\" has already been defined to be bigger than 10\n";
        expected += "At line: rule1: test > 10 AND test > 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10 AND Test >= 10")
    public void biggerThanOrEqualAndBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 10);
        CreateAndExpression(">=", 10, ">=", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"Test\" has already been defined to be bigger than or equal to 10\n";
        expected += "At line: rule1: test >= 10 AND test >= 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 AND Test < 10")
    public void smallerThanOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        CreateAndExpression("<", 10, "<", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"Test\" has already been defined to be less than 10\n";
        expected += "At line: rule1: test < 10 AND test < 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 AND Test <= 10")
    public void smallerThanOrEqualAndSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 10);
        CreateAndExpression("<=", 10, "<=", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"Test\" has already been defined to be less than or equal to 10\n";
        expected += "At line: rule1: test <= 10 AND test <= 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
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
