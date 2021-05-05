package src.test.scannerTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.scanner.*;

public class ColRulesTest {
    private final String pathName = "./src/resources/testFiler/prettyPrinter/columnrules/";
    private final String importPrint = "IMPORT users.csv IN users\n";
    
    @Test
    @DisplayName("Model with column rule with sum expression")
    public void WithSumRule() {
        String fileName = "WithSumRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: SUM(height) > 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with column rule with count expression")
    public void WithCountRule() {
        String fileName = "WithCountRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: COUNT(height) > 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with column rule with average expression")
    public void WithAvgRule() {
        String fileName = "WithAvgRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: AVG(height) > 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with column rule with distinct expression")
    public void WithDistinctRule() {
        String fileName = "WithDistinctRule.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: DISTINCT(height) > 10\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Model with column rule with where-clause")
    public void WithWhereClause() {
        String fileName = "WithWhereClause.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: AVG(height) > 10 WHERE age < 5\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with column rule with and expression")
    public void WithAndExpr() {
        String fileName = "WithAndExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: SUM(height) > 10 AND COUNT(height) = 100\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with column rule with or expression")
    public void WithOrExpr() {
        String fileName = "WithOrExpr.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule: SUM(height) > 10 OR COUNT(height) = 100\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Model with column partrules")
    public void WithColumnPartrules() {
        String fileName = "WithColumnPartrules.txt";
        String[] args = {pathName + fileName};
        String expected = importPrint + "MODEL user BEGIN\n";
        expected += "COL rule BEGIN\npart1: SUM(height) > 10\npart2: COUNT(height) = 100\nEND\n";
        expected += "END\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);
    }
    
}
