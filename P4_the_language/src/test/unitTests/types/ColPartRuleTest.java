package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class ColPartRuleTest {
    
    @Test
    @DisplayName("ToString returnerer \"Col Part Rule\"")
    public void toStringColPartRule() {
        ColPartRuleType colPartRule = new ColPartRuleType();
        String expected = "Column Part Rule";

        String actual = colPartRule.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two colPartRule objects are equal")
    public void colPartRuleEqualsColPartRule() {
        ColPartRuleType firstColPartRule = new ColPartRuleType();
        ColPartRuleType secondColPartRule = new ColPartRuleType();
        boolean expected = true;

        boolean actual = firstColPartRule.equals(secondColPartRule);

        assertEquals(expected, actual);
    }
}
