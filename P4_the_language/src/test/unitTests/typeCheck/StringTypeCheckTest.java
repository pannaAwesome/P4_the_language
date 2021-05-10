package src.test.unitTests.typeCheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.exceptions.*;
import src.classes.scanner.*;
import src.classes.types.*;

public class StringTypeCheckTest {
    private RULE parentNode;

    @BeforeEach
    public void setupRuleNode() {
        parentNode = new RULE(1);
        IDEN ruleName = new IDEN(2);
        ruleName.value = "rule1";

        parentNode.jjtAddChild(ruleName, 0);
    }

    //#region No exceptions in expressions
    
    @Test
    @DisplayName("Adding equal value to string type")
    public void addingEqualValue() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hejsa");

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        
        firstString.compareTypesAnd( id, secondString, parentNode);
        List<String> actual = firstString.exactValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding contains value to string type")
    public void addingContainsValue() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hejsa");
        

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        
        firstString.compareTypesAnd( id, secondString, parentNode);
        List<String> actual = firstString.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding contain value to contain value in and expression")
    public void addingAnotherContainValueAndExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        expected.add("goddag");
        
        firstString.compareTypesAnd( id, secondString, parentNode);
        List<String> actual = firstString.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding equal value to equal value in or expression")
    public void addingAnotherEqualValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "goddag");
        

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        expected.add("goddag");
        
        firstString.compareTypesOr( id, secondString, parentNode);
        List<String> actual = firstString.exactValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding contain value to equal value in or expression")
    public void addingContainToEqualValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        

        List<String> expectedExact = new ArrayList<String>();
        expectedExact.add("hejsa");
        List<String> expectedContain = new ArrayList<String>();
        expectedContain.add("goddag");
        
        firstString.compareTypesOr( id, secondString, parentNode);
        List<String> actualExact = firstString.exactValue;
        List<String> actualContain = firstString.containValue;

        assertIterableEquals(expectedExact, actualExact);
        assertIterableEquals(expectedContain, actualContain);
    }

    @Test
    @DisplayName("Adding contain value to contain value in or expression")
    public void addingAnotherContainValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        expected.add("goddag");
        
        firstString.compareTypesOr( id, secondString, parentNode);
        List<String> actual = firstString.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding equal value to contain value in or expression")
    public void addingEqualToContainValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "goddag");
        

        List<String> expectedContain = new ArrayList<String>();
        expectedContain.add("hejsa");
        List<String> expectedExact = new ArrayList<String>();
        expectedExact.add("goddag");
        
        firstString.compareTypesOr( id, secondString, parentNode);
        List<String> actualContain = firstString.containValue;
        List<String> actualExact = firstString.exactValue;
        
        assertIterableEquals(expectedContain, actualContain);
        assertIterableEquals(expectedExact, actualExact);
    }

    //#endregion

    //#region Exceptions in and expressions
    @Test
    @DisplayName("Having the same value twice in equal and expression")
    public void sameEqualValueAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hejsa");
        CreateAndExpression("=", "hejsa", "=", "hejsa");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "DUPLICATE WARNING: \"test\" has already been declared as \"hejsa\"\n";
        expectedMessage += "At line: rule1: test = \"hejsa\" AND test = \"hejsa\"\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having two different equal values in and expression")
    public void differentEqualValueAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "goddag");
        CreateAndExpression("=", "hejsa", "=", "goddag");

        String expectedMessage = "ERROR:\n";
        expectedMessage += "CONSTRAINT ERROR: \"test\" is already \"hejsa\", and can therefore not be \"goddag\"\n";
        expectedMessage += "At line: rule1: test = \"hejsa\" AND test = \"goddag\"\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having an equal value that is contained by contains value in and expression")
    public void equalValueSameAsContainsValueAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hej");
        CreateAndExpression("=", "hejsa", "CONTAINS", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" has been given the exact value \"hejsa\" and it should contain the value \"hej\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test = \"hejsa\" AND test CONTAINS \"hej\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having two different equal and contains values in and expression")
    public void differentEqualAndContainValueAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        CreateAndExpression("=", "hejsa", "CONTAINS", "goddag");

        String expectedMessage = "ERROR:\n";
        expectedMessage += "CONSTRAINT ERROR: \"test\" is already declared as \"hejsa\". Cannot change \"test\" to contain \"goddag\"\n";
        expectedMessage += "At line: rule1: test = \"hejsa\" AND test CONTAINS \"goddag\"\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having the same value twice in contains and expression")
    public void sameContainValueAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hej");
        CreateAndExpression("CONTAINS", "hej", "CONTAINS", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "DUPLICATE WARNING: \"test\" has already been declared to contain \"hej\"\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hej\" AND test CONTAINS \"hej\"\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having second contain value that contains first contain value in and expression")
    public void containedContainValuesAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hej");
        CreateAndExpression("CONTAINS", "hejsa", "CONTAINS", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" should contain both \"hejsa\" and \"hej\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hejsa\" AND test CONTAINS \"hej\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having first contain value that contains second contain value in and expression")
    public void containedfirstContainValuesAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hejsa");
        CreateAndExpression("CONTAINS", "hej", "CONTAINS", "hejsa");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" should contain both \"hej\" and \"hejsa\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hej\" AND test CONTAINS \"hejsa\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having different contains and equal values in and expression")
    public void sameContainAndEqualValueAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hej");
        CreateAndExpression("CONTAINS", "hejsa", "=", "hej");

        String expectedMessage = "ERROR:\n";
        expectedMessage += "CONSTRAINT ERROR: \"test\" is already declared to contain \"hejsa\". Cannot change \"test\" to be \"hej\"\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hejsa\" AND test = \"hej\"\n\n";
        
        Throwable thrown = assertThrows(ConstraintException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having one contain value that contains a new equal value in and expression")
    public void containedContainAndEqualValuesAnd() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hejsa");
        CreateAndExpression("CONTAINS", "hej", "=", "hejsa");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" has been given the exact value \"hejsa\" and it should contain the value \"hej\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hej\" AND test = \"hejsa\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesAnd( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    private void CreateAndExpression(String firstOperator, String firstValue, String secondOperator, String secondValue) {
        AND andNode = new AND(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        STRING firstString = new STRING(4);
        firstString.value = "\"" + firstValue + "\"";
        firstExpr.jjtAddChild(firstString, 1);
        andNode.jjtAddChild(firstExpr, 0);

        VALEXPR secondExpr = new VALEXPR(4);
        secondExpr.value = secondOperator;
        IDEN secondId = new IDEN(3);
        secondId.value = "test";
        secondExpr.jjtAddChild(secondId, 0);
        STRING secondString = new STRING(4);
        secondString.value = "\"" + secondValue + "\"";
        secondExpr.jjtAddChild(secondString, 1);
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }

    //#endregion

    //#region Exceptions in or expressions
    @Test
    @DisplayName("Having the same value twice in equals or expression")
    public void sameEqualsValueOr() {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hej");
        CreateOrExpression("=", "hej", "=", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "DUPLICATE WARNING: \"test\" has already been declared as \"hej\"\n";
        expectedMessage += "At line: rule1: test = \"hej\" OR test = \"hej\"\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstString.compareTypesOr( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having one equal value that is contained in contain value in or expression")
    public void containedEqualAndContainValuesOr() {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hej");
        CreateOrExpression("=", "hejsa", "CONTAINS", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" has been given the exact value \"hejsa\" and it should contain the value \"hej\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test = \"hejsa\" OR test CONTAINS \"hej\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesOr( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having the same value twice in contains or expression")
    public void sameContainValueOr() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hej");
        CreateOrExpression("CONTAINS", "hej", "CONTAINS", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "DUPLICATE WARNING: \"test\" has already been declared to contain \"hej\"\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hej\" OR test CONTAINS \"hej\"\n\n";
        
        Throwable thrown = assertThrows(DuplicationException.class, () -> firstString.compareTypesOr( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having second contain value which contain the first contained value in or expression")
    public void containedfirstContainValuesOr() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hej");
        CreateOrExpression("CONTAINS", "hejsa", "CONTAINS", "hej");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" should contain both \"hejsa\" and \"hej\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hejsa\" AND test CONTAINS \"hej\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesOr( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having first contain value which contain the second contained value in or expression")
    public void containedsecondContainValuesOr() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hejsa");
        CreateOrExpression("CONTAINS", "hej", "CONTAINS", "hejsa");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" should contain both \"hej\" and \"hejsa\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hej\" AND test CONTAINS \"hejsa\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesOr( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @DisplayName("Having one contain value that contains the new equal value or expression")
    public void containedContainAndEqualValuesOr() {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hej");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hejsa");
        CreateOrExpression("CONTAINS", "hej", "=", "hejsa");

        String expectedMessage = "WARNING:\n";
        expectedMessage += "REDUNDANT SYNTAX WARNING: \"test\" has been given the exact value \"hejsa\" and it should contain the value \"hej\", this is redundant. Therefore one of the operations can be omitted.\n";
        expectedMessage += "At line: rule1: test CONTAINS \"hej\" AND test = \"hejsa\"\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstString.compareTypesOr( id, secondString, parentNode));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    private void CreateOrExpression(String firstOperator, String firstValue, String secondOperator, String secondValue) {
        OR andNode = new OR(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        STRING firstString = new STRING(4);
        firstString.value = "\"" + firstValue + "\"";
        firstExpr.jjtAddChild(firstString, 1);
        andNode.jjtAddChild(firstExpr, 0);

        VALEXPR secondExpr = new VALEXPR(4);
        secondExpr.value = secondOperator;
        IDEN secondId = new IDEN(3);
        secondId.value = "test";
        secondExpr.jjtAddChild(secondId, 0);
        STRING secondString = new STRING(4);
        secondString.value = "\"" + secondValue + "\"";
        secondExpr.jjtAddChild(secondString, 1);
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }

    //#endregion
}
