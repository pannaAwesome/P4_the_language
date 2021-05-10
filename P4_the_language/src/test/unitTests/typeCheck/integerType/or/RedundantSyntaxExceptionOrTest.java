package src.test.unitTests.typeCheck.integerType.or;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import src.classes.exceptions.DuplicationException;
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
    @DisplayName("Test IS INTEGER OR Test IS INTEGER")
    public void isOrIs() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        CreateOrExpression("IS", null, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "DUPLICATE WARNING: \"test\" has already been defined as Integer\n";
        expected += "At line: rule1: test IS INTEGER OR test IS INTEGER\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstInt.compareTypesAnd( id, secondInt, parentNode));
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
