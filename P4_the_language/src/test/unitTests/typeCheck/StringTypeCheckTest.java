package src.test.unitTests.typeCheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import src.classes.exceptions.ConstraintException;
import src.classes.exceptions.DuplicationException;
import src.classes.exceptions.RedundantSyntaxException;
import src.classes.scanner.SimpleNode;
import src.classes.types.*;

public class StringTypeCheckTest {

    @Test
    @DisplayName("Adding equal value to string type")
    public void addingEqualValue() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "hejsa");
        SimpleNode node = new SimpleNode(1);

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        
        firstString.compareTypesAnd( id, secondString, node);
        List<String> actual = firstString.exactValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding contains value to string type")
    public void addingContainsValue() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "hejsa");
        SimpleNode node = new SimpleNode(1);

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        
        firstString.compareTypesAnd( id, secondString, node);
        List<String> actual = firstString.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding contain value to contain value in and expression")
    public void addingAnotherContainValueAndExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        SimpleNode node = new SimpleNode(1);

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        expected.add("goddag");
        
        firstString.compareTypesAnd( id, secondString, node);
        List<String> actual = firstString.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding equal value to equal value in or expression")
    public void addingAnotherEqualValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "goddag");
        SimpleNode node = new SimpleNode(1);

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        expected.add("goddag");
        
        firstString.compareTypesOr( id, secondString, node);
        List<String> actual = firstString.exactValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding contain value to equal value in or expression")
    public void addingContainToEqualValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("=", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        SimpleNode node = new SimpleNode(1);

        List<String> expectedExact = new ArrayList<String>();
        expectedExact.add("hejsa");
        List<String> expectedContain = new ArrayList<String>();
        expectedContain.add("goddag");
        
        firstString.compareTypesOr( id, secondString, node);
        List<String> actualExact = firstString.exactValue;
        List<String> actualContain = firstString.containValue;

        assertIterableEquals(expectedExact, actualExact);
        assertIterableEquals(expectedContain, actualContain);
    }

    @Test
    @DisplayName("Adding contain value to contain value in or expression")
    public void addingAnotherContainValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("CONTAINS", "goddag");
        SimpleNode node = new SimpleNode(1);

        List<String> expected = new ArrayList<String>();
        expected.add("hejsa");
        expected.add("goddag");
        
        firstString.compareTypesOr( id, secondString, node);
        List<String> actual = firstString.containValue;

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding equal value to contain value in or expression")
    public void addingEqualToContainValueOrExpr() throws DuplicationException, ConstraintException, RedundantSyntaxException {
        StringType firstString = new StringType();
        firstString.setStringValues("CONTAINS", "hejsa");

        String id = "test";
        StringType secondString = new StringType();
        secondString.setStringValues("=", "goddag");
        SimpleNode node = new SimpleNode(1);

        List<String> expectedContain = new ArrayList<String>();
        expectedContain.add("hejsa");
        List<String> expectedExact = new ArrayList<String>();
        expectedExact.add("goddag");
        
        firstString.compareTypesOr( id, secondString, node);
        List<String> actualContain = firstString.containValue;
        List<String> actualExact = firstString.exactValue;
        
        assertIterableEquals(expectedContain, actualContain);
        assertIterableEquals(expectedExact, actualExact);
    }

    
}
