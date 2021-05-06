package src.test.scannerTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.scanner.*;

public class AnalyzeStatementTest {
    private final String pathName = "./src/resources/testFiler/prettyPrinter/analyze/";
    private final String importmodelPrint = "IMPORT users.csv IN users\nMODEL user BEGIN\nEND\n";

    @Test
    @DisplayName("Analyze statement with no arguments")
    public void NoArgumentsAnalyze(){
        String fileName = "NoArgumentsAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where one rule argument is given")
    public void OnlyOneRuleAnalyze(){
        String fileName = "OnlyOneRuleAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nrule1\nEND\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where two rule arguments are given")
    public void OnlyTwoOrMoreRulesAnalyze(){
        String fileName = "OnlyTwoOrMoreRulesAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nrule1\nrule2\nEND\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where only columns are given as numbers")
    public void OnlyColWithNumbersAnalyze(){
        String fileName = "OnlyColWithNumbersAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nCOL 1, 4\nEND\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where only columns are given as column names")
    public void OnlyColWithNamesAnalyze(){
        String fileName = "OnlyColWithNamesAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nCOL firstname, lastname\nEND\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    
    @Test
    @DisplayName("Analyze statement where only columns are given as column names and numbers")
    public void OnlyColumnsWithNameAndNumbersAnalyze(){
        String fileName = "OnlyColumnsWithNameAndNumbersAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nCOL 1, lastname\nEND\n";

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where only row parameter with rows as range")
    public void OnlyRowWithRangeAnalyze(){
        String fileName = "OnlyRowWithRangeAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nROW 1 TO 20\nEND\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where only row parameter with one row")
    public void OnlyRowsWithOneRowAnalyze(){
        String fileName = "OnlyRowsWithOneRowAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nROW 1, 20\nEND\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where only row parameter with rows as range and index")
    public void OnlyRowWithRangeAndRowAnalyze(){
        String fileName = "OnlyRowWithRangeAndRowAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nROW 1 TO 20, 50\nEND\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }

    @Test
    @DisplayName("Analyze statement where all parameters are given")
    public void AllArgumentsAnalyze(){
        String fileName = "AllArgumentsAnalyze.txt";
        String[] args = {pathName + fileName};
        String expected = importmodelPrint + "ANALYZE users WITH user BEGIN\nrule1\nCOL firstname\nROW 1\nEND\n";


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);   
    }
}
