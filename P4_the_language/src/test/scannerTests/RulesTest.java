package src.test.scannerTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.scanner.*;

public class RulesTest {
    private final String pathName = "./src/resources/testFiler/prettyPrinter/rules/";
    private final String importPrint = "IMPORT users.csv IN users\n";
    
    @Test
    @DisplayName("Model with rule with is expression")
    public void WithIsRule() {
        String fileName = "WithIsRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height IS INTEGER\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with equal to string expression")
    public void WithEqualStringRule() {
        String fileName = "WithEqualStringRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height = \"hejsa\"\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with contains string expression")
    public void WithContainsRule() {
        String fileName = "WithContainsRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height CONTAINS \"hejsa\"\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with equal to number expression")
    public void WithEqualNumberRule() {
        String fileName = "WithEqualNumberRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height = 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with less than expression")
    public void WithLessThanRule() {
        String fileName = "WithLessThanRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height < 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with less than or equal expression")
    public void WithLessThanOrEqualRules() {
        String fileName = "WithLessThanOrEqualRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height <= 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with bigger than expression")
    public void WithBiggerThanRules() {
        String fileName = "WithBiggerThanRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height > 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with bigger than or equal expression")
    public void WithBiggerThanOrEqualRules() {
        String fileName = "WithBiggerThanOrEqualRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height >= 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with addition and subtraction expression")
    public void WithAddExpr() {
        String fileName = "WithAddExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height = 1 + age - 15\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with multiplication, division and modulus expression")
    public void WithMultExpr() {
        String fileName = "WithMultExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height = age * 5 / 4 % 15\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with and expression")
    public void WithAndExpr() {
        String fileName = "WithAndExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height IS INTEGER AND height < 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with and that contains or expression")
    public void WithAndThatContainsOrExpr() {
        String fileName = "WithAndThatContainsOrExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: (height IS INTEGER OR height > 10) AND (height IS DECIMAL OR height > 10.0)\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with or expression")
    public void WithOrExpr() {
        String fileName = "WithOrExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height IS INTEGER OR height IS DECIMAL\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with rule with or that contains and expression")
    public void WithOrThatContainsAndExpr() {
        String fileName = "WithOrThatContainsAndExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule: height IS INTEGER AND height > 10 OR height IS DECIMAL AND height > 10.0\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with partrules")
    public void WithPartrules() {
        String fileName = "WithPartrules.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "rule BEGIN\npart1: height IS INTEGER\npart2: height IS DECIMAL\nEND\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

}
