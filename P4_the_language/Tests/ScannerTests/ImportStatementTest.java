package Tests.ScannerTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import src.*;

public class ImportStatementTest {
    private final String pathName = "PrettyPrinterTestFiler/";
    private final String modelPrint = "MODEL user BEGIN\nEND";

    @Test
    public void NoArgumentsImportStatement() {
        String fileName = "NoArgumentsImport.txt";
        String[] args = {pathName + fileName};
        String expected = "IMPORT users.csv IN users\n" + modelPrint;

        Scanner.main(args);
        String actual = Scanner.prettyPrint;

        assertEquals(expected, actual);        
    }

}
