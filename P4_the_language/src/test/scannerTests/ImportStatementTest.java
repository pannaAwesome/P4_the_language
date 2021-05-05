package src.test.scannerTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.scanner.*;

public class ImportStatementTest {
    private final String pathName = "./src/resources/testFiler/prettyPrinter/import/";
    private final String modelPrint = "MODEL user BEGIN\nEND\n";

    @Test
    @DisplayName("Import statement with no arguments")
    public void NoArgumentsImportStatement() {
        String fileName = "NoArgumentsImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users\n" + modelPrint;


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }
    
    @Test
    @DisplayName("Import statement where the only parameter is ID")
    public void OnlyIDImport() {
        String fileName = "OnlyIDImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users BEGIN\nID hejsa\nEND\n" + modelPrint;


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }
    
    @Test
    @DisplayName("Import statement where ID parameter is first, and is given with number")
    public void IDWithNumberOnTopImport() {
        String fileName = "IDWithNumberOnTopImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users BEGIN\nID 1\nNOHEADERS\nEND\n" + modelPrint;


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }
    
    @Test
    @DisplayName("Import statement where ID parameter is first, and is given with columnname")
    public void IDWithNameOnTopImport() {
        String fileName = "IDWithNameOnTopImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users BEGIN\nID hejsa\nNOHEADERS\nEND\n" + modelPrint;


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }
    
    @Test
    @DisplayName("Import statement where the only parameter is NOHEADERS")
    public void OnlyNOHEADERSImport() {
        String fileName = "NoArgumentsImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users BEGIN\nNOHEADERS\nEND\n" + modelPrint;


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }
    
    @Test
    @DisplayName("Import statement where NOHEADERS parameter is first")
    public void NOHEADERSOnTopImport() {
        String fileName = "NOHEADERSOnTopImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users BEGIN\nNOHEADERS\nID hejsa\nEND\n" + modelPrint;


        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }
}
