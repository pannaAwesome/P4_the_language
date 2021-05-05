package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class TableTest {
    
    @Test
    @DisplayName("ToString returnerer \"Table\"")
    public void toStringTable() {
        TableType table = new TableType();
        String expected = "Table";

        String actual = table.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two table objects are equal")
    public void tableEqualsTable() {
        TableType firstTable = new TableType();
        TableType secondTable = new TableType();
        boolean expected = true;

        boolean actual = firstTable.equals(secondTable);

        assertEquals(expected, actual);
    }
}
