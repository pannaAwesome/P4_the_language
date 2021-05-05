package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class RuleTest {
    
    @Test
    @DisplayName("ToString returnerer \"Rule\"")
    public void toStringRule() {
        RuleType rule = new RuleType();
        String expected = "Rule";

        String actual = rule.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two rule objects are equal")
    public void ruleEqualsRule() {
        RuleType firstRule = new RuleType();
        RuleType secondRule = new RuleType();
        boolean expected = true;

        boolean actual = firstRule.equals(secondRule);

        assertEquals(expected, actual);
    }
}
