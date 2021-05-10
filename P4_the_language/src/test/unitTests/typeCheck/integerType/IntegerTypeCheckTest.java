package src.test.unitTests.typeCheck.integerType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("Test IS INTEGER")
    public void isInteger() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        
        boolean expected = true;
        
        boolean actual = firstInt.compareTypesAnd( id, secondInt, parentNode);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test = 10")
    public void Equals() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("=", 10);
        
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        List<Integer> actual = firstInt.equalValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Test > 10")
    public void biggerThan() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">", 10);
        
        int expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test >= 10")
    public void biggerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue(">=", 10);
        
        int expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        int actual = firstInt.minValue;
        boolean actualWith = firstInt.withGivenMinValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test < 10")
    public void smallerThan() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<", 10);
        
        int expected = 10;
        boolean expectedWith = false;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }

    @Test
    @DisplayName("Test <= 10")
    public void smallerThanOrEqual() throws Exception {
        IntegerType firstInt = new IntegerType();

        String id = "test";
        IntegerType secondInt = new IntegerType();
        secondInt.SetValue("<=", 10);
        
        int expected = 10;
        boolean expectedWith = true;
        
        firstInt.compareTypesAnd( id, secondInt, parentNode);
        int actual = firstInt.maxValue;
        boolean actualWith = firstInt.withGivenMaxValue;

        assertEquals(expected, actual);
        assertEquals(expectedWith, actualWith);
    }
}
