package src.test.unitTests.typeCheck;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import src.classes.scanner.*;
import src.classes.types.*;

public class IntegerTypeCheckTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
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

    //#region Exceptions in or expressions
    private void CreateOrExpression(String firstOperator, int firstValue, String secondOperator, int secondValue) {
        AND andNode = new AND(1);
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
