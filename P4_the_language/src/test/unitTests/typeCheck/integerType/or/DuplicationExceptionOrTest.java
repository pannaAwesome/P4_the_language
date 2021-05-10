package src.test.unitTests.typeCheck.integerType.or;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import src.classes.exceptions.DuplicationException;
import src.classes.scanner.*;
import src.classes.types.*;

public class DuplicationExceptionOrTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    @Test
    @DisplayName("Test IS INTEGER OR Test IS INTEGER")
    public void isOrIs() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        CreateOrExpression("IS", null, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined as Integer.\n";
        expected += "At line: rule1: test IS INTEGER OR test IS INTEGER\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10 OR Test = 10")
    public void equalsOrEquals() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        CreateOrExpression("=", 10, "=", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined as 10.\n";
        expected += "At line: rule1: test = 10 OR test = 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10 OR Test > 10")
    public void biggerThanOrBiggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 10);
        CreateOrExpression(">", 10, ">", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined to be bigger than 10\n";
        expected += "At line: rule1: test > 10 OR test > 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10 OR Test >= 10")
    public void biggerThanOrEqualOrBiggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 10);
        CreateOrExpression(">=", 10, ">=", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined to be bigger than or equal to 10\n";
        expected += "At line: rule1: test >= 10 OR test >= 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10 OR Test < 10")
    public void smallerThanOrSmallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue(">", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        CreateOrExpression("<", 10, "<", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined to be less than 10\n";
        expected += "At line: rule1: test < 10 OR test < 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10 OR Test <= 10")
    public void smalleThanOrEqualOrSmallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();
        firstInt.SetValue("<=", 10);

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 10);
        CreateOrExpression("<=", 10, "<=", 10);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined to be less than or equal to 10\n";
        expected += "At line: rule1: test <= 10 OR test <= 10\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
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
