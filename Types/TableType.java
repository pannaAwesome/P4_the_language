package Types;
import Types.*;
import Exceptions.*;
import Visitors.*;
import jjtNode.*;
import Scanner.*;

public class TableType extends BaseType{
    public TableType() {
        super(6);
    }

    @Override
    public String toString(){
        return "Table";
    }
}
