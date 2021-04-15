package Types;
import Types.*;
import Exceptions.*;
import Visitors.*;
import jjtNode.*;
import Scanner.*;

public class ModelType extends BaseType {
    public ModelType() {
        super(7);
    }

    @Override
    public String toString(){
        return "Model";
    }
}
