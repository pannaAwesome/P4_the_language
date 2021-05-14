package src.test.unitTests.typeCheck.decimalType.or;

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
    @DisplayName("Test IS DECIMAL OR Test = 10.0")
    public void isOrEquals() throws Exception {
        DecimalType firstInt = new DecimalType();

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("=", 10.0);
        CreateOrExpression("IS", null, "=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as Decimal or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test IS DECIMAL OR test = 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS DECIMAL OR Test > 10.0")
    public void isOrBiggerThan() throws Exception {
        DecimalType firstInt = new DecimalType();

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">", 10.0);
        CreateOrExpression("IS", null, ">", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as Decimal or bigger than 10.0. This can be simplified\n";
        expected += "At line: rule1: test IS DECIMAL OR test > 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS DECIMAL OR Test >= 10.0")
    public void isOrBiggerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">=", 10.0);
        CreateOrExpression("IS", null, ">=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as Decimal or bigger than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test IS DECIMAL OR test >= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS DECIMAL OR Test < 10.0")
    public void isOrSmallerThan() throws Exception {
        DecimalType firstInt = new DecimalType();

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<", 10.0);
        CreateOrExpression("IS", null, "<", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as Decimal or less than 10.0. This can be simplified\n";
        expected += "At line: rule1: test IS DECIMAL OR test < 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test IS DECIMAL OR Test <= 10.0")
    public void isOrSmallerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<=", 10.0);
        CreateOrExpression("IS", null, "<=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as Decimal or less than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test IS DECIMAL OR test <= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 OR Test IS DECIMAL")
    public void equalsOrIs() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("=", 10.0); 

        String id = "test";
        DecimalType secondInt = new DecimalType();
        CreateOrExpression("=", 10.0, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as 10.0 or Decimal. This can be simplified\n";
        expected += "At line: rule1: test = 10.0 OR test IS DECIMAL\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 OR Test > 9.0")
    public void equalsOrBiggerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">", 9.0);
        CreateOrExpression("=", 10.0, ">", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as 10.0 or bigger than 9.0. This can be simplified\n";
        expected += "At line: rule1: test = 10.0 OR test > 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 OR Test >= 10.0")
    public void equalsOrBiggerThanOrEquals() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">=", 10.0);
        CreateOrExpression("=", 10.0, ">=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as 10.0 or bigger than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test = 10.0 OR test >= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 OR Test < 11.0")
    public void equalsOrSmallerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<", 11.0);
        CreateOrExpression("=", 10.0, "<", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as 10.0 or less than 11.0. This can be simplified\n";
        expected += "At line: rule1: test = 10.0 OR test < 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test = 10.0 OR Test <= 10.0")
    public void equalsOrSmallerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<=", 10.0);
        CreateOrExpression("=", 10.0, "<=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as 10.0 or less than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test = 10.0 OR test <= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 OR Test IS DECIMAL")
    public void biggerThanOrIs() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        CreateOrExpression(">", 10.0, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 or Decimal. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 OR test IS DECIMAL\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 OR Test = 11.0")
    public void biggerThanOrEquals() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("=", 11.0);
        CreateOrExpression(">", 10.0, "=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 or equal to 11.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 OR test = 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 OR Test > 9.0")
    public void biggerThanOrBiggerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">", 9.0);
        CreateOrExpression(">", 10.0, ">", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 or bigger than 9.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 OR test > 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }
    
    @Test
    @DisplayName("Test > 10.0 OR Test >= 10.0")
    public void biggerThanOrBiggerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">=", 10.0);
        CreateOrExpression(">", 10.0, ">=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 or bigger than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 OR test >= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 OR Test < 11.0")
    public void biggerThanOrSmallerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<", 11.0);
        CreateOrExpression(">", 10.0, "<", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 or less than 11.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 OR test < 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test > 10.0 OR Test <= 11.0")
    public void biggerThanOrSmallerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<=", 11.0);
        CreateOrExpression(">", 10.0, "<=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than 10.0 or less than or equal to 11.0. This can be simplified\n";
        expected += "At line: rule1: test > 10.0 OR test <= 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test IS DECIMAL")
    public void biggerThanOrEqualOrIs() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        CreateOrExpression(">=", 10.0, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 or Decimal. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 OR test IS DECIMAL\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test = 10.0")
    public void biggerThanOrEqualOrEquals() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("=", 10.0);
        CreateOrExpression(">=", 10.0, "=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 OR test = 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test > 10.0")
    public void biggerThanOrEqualOrBiggerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">", 10.0);
        CreateOrExpression(">=", 10.0, ">", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 or bigger than 10.0. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 OR test > 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }
    
    @Test
    @DisplayName("Test >= 10.0 OR Test >= 11.0")
    public void biggerThanOrEqualOrBiggerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">=", 11.0);
        CreateOrExpression(">=", 10.0, ">=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 or bigger than or equal to 11.0. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 OR test >= 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test < 11.0")
    public void biggerThanOrEqualOrSmallerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<", 11.0);
        CreateOrExpression(">=", 10.0, "<", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 or less than 11.0. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 OR test < 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test >= 10.0 OR Test <= 10.0")
    public void biggerThanOrEqualOrSmallerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue(">=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<=", 10.0);
        CreateOrExpression(">=", 10.0, "<=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as bigger than or equal to 10.0 or less than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test >= 10.0 OR test <= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 OR Test IS DECIMAL")
    public void smallerThanOrIs() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        CreateOrExpression("<", 10.0, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 or Decimal. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 OR test IS DECIMAL\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }
    
    @Test
    @DisplayName("Test < 10.0 OR Test = 9.0")
    public void smallerThanOrEquals() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("=", 9.0);
        CreateOrExpression("<", 10.0, "=", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 or equal to 9.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 OR test = 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 OR Test > 9.0")
    public void smallerThanOrBiggerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">", 9.0);
        CreateOrExpression("<", 10.0, ">", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 or bigger than 9.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 OR test > 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }
    
    @Test
    @DisplayName("Test < 10.0 OR Test >= 9.0")
    public void smallerThanOrBiggerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">=", 9.0);
        CreateOrExpression("<", 10.0, ">=", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 or bigger than or equal to 9.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 OR test >= 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 OR Test < 11.0")
    public void smallerThanOrSmallerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<", 11.0);
        CreateOrExpression("<", 10.0, "<", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 or less than 11.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 OR test < 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test < 10.0 OR Test <= 10.0")
    public void smallerThanOrSmallerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<=", 10.0);
        CreateOrExpression("<", 10.0, "<=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than 10.0 or less than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test < 10.0 OR test <= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test IS DECIMAL")
    public void smallerThanOrEqualOrIs() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        CreateOrExpression("<=", 10.0, "IS", null);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 or Decimal. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 OR test IS DECIMAL\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test = 10.0")
    public void smallerThanOrEqualOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("=", 10.0);
        CreateOrExpression("<=", 10.0, "=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 OR test = 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test > 9.0")
    public void smallerThanOrEqualOrBiggerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">", 9.0);
        CreateOrExpression("<=", 10.0, ">", 9.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 or bigger than 9.0. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 OR test > 9.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test >= 10.0")
    public void smallerThanOrEqualOrBiggerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue(">=", 10.0);
        CreateOrExpression("<=", 10.0, ">=", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 or bigger than or equal to 10.0. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 OR test >= 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test < 10.0")
    public void smallerThanOrEqualOrSmallerThan() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<", 10.0);
        CreateOrExpression("<=", 10.0, "<", 10.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 or less than 10.0. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 OR test < 10.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    @DisplayName("Test <= 10.0 OR Test <= 11.0")
    public void smallerThanOrEqualOrSmallerThanOrEqual() throws Exception {
        DecimalType firstInt = new DecimalType();
        firstInt.SetValue("<=", 10.0);

        String id = "test";
        DecimalType secondInt = new DecimalType();
        secondInt.SetValue("<=", 11.0);
        CreateOrExpression("<=", 10.0, "<=", 11.0);
        
        String expected = "WARNING:\n";
        expected += "REDUNDANT SYNTAX WARNING: \"test\" has been defined as less than or equal to 10.0 or less than or equal to 11.0. This can be simplified\n";
        expected += "At line: rule1: test <= 10.0 OR test <= 11.0\n\n";
        
        Throwable thrown = assertThrows(RedundantSyntaxException.class, () -> firstInt.compareTypesOr( id, secondInt, parentNode));
        assertEquals(expected, thrown.getMessage());
    }

    //#region Exceptions in or expressions
    private void CreateOrExpression(String firstOperator, Double firstValue, String secondOperator, Double secondValue) {
        OR andNode = new OR(1);
        VALEXPR firstExpr = new VALEXPR(2);
        firstExpr.value = firstOperator;
        IDEN firstId = new IDEN(3);
        firstId.value = "test";
        firstExpr.jjtAddChild(firstId, 0);
        if (firstOperator.equals("IS")) {
            CONSTRAINTS firstInt = new CONSTRAINTS(4);
            firstInt.value = "DECIMAL";
            firstExpr.jjtAddChild(firstInt, 1);
        } else {
            FLOATY firstInt = new FLOATY(4);
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
            secondInt.value = "DECIMAL";
            secondExpr.jjtAddChild(secondInt, 1);
        } else {
            FLOATY secondInt = new FLOATY(4);
            secondInt.value = secondValue;
            secondExpr.jjtAddChild(secondInt, 1);
        }
        andNode.jjtAddChild(secondExpr, 1);

        parentNode.jjtAddChild(andNode, 1);
    }
    //#endregion
}
