package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class PartRuleTest {
    
    @Test
    @DisplayName("ToString returnerer \"Part Rule\"")
    public void toStringPartRule() {
        PartRuleType PartRule = new PartRuleType();
        String expected = "Part Rule";

        String actual = PartRule.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two PartRule objects are equal")
    public void PartRuleEqualsPartRule() {
        PartRuleType firstPartRule = new PartRuleType();
        PartRuleType secondPartRule = new PartRuleType();
        boolean expected = true;

        boolean actual = firstPartRule.equals(secondPartRule);

        assertEquals(expected, actual);
    }
}
