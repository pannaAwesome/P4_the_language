package src.test.unitTests.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.types.*;

public class ModelTest {
    
    @Test
    @DisplayName("ToString returnerer \"Model\"")
    public void toStringModel() {
        ModelType model = new ModelType();
        String expected = "Model";

        String actual = model.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Two model objects are equal")
    public void modelEqualsModel() {
        ModelType firstModel = new ModelType();
        ModelType secondModel = new ModelType();
        boolean expected = true;

        boolean actual = firstModel.equals(secondModel);

        assertEquals(expected, actual);
    }
}
