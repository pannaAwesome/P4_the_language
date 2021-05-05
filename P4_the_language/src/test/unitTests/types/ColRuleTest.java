package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class ColRuleTest {
    
    @Test
    @DisplayName("ToString returnerer \"Column Rule\"")
    public void toStringColRule() {
        ColRuleType colRule = new ColRuleType();
        String expected = "Column Rule";

        String actual = colRule.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two colRule objects are equal")
    public void colRuleEqualsColRule() {
        ColRuleType firstColRule = new ColRuleType();
        ColRuleType secondColRule = new ColRuleType();
        boolean expected = true;

        boolean actual = firstColRule.equals(secondColRule);

        assertEquals(expected, actual);
    }
}
