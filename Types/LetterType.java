package Types;
import Types.*;
import Exceptions.*;
import Visitors.*;
import jjtNode.*;
import Scanner.*;

public class LetterType extends BaseType {
    public LetterType() {
        super(4);
    }

    @Override
    public String toString() {
        return "Letters";
    }
}
