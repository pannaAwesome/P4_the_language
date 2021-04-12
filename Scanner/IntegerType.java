import java.util.ArrayList;
import java.util.List;

public class IntegerType extends BaseType {
    private Integer minValue = null;
    private boolean withGivenMinValue = false;
    private Integer maxValue = null;
    private boolean withGivenMaxValue = false;
    private List<Integer> equalValue = new ArrayList<Integer>();

    public IntegerType() {
        super(3);
    }

    private void SetMinValue(int newMinValue, boolean withValue) {
        minValue = newMinValue;
        withGivenMinValue = withValue;
    }

    private void SetMaxValue(int newMaxValue, boolean withValue) {
        maxValue = newMaxValue;
        withGivenMaxValue = withValue;
    }

    private void SetEqualValue(int newEqualValue) {
        equalValue.add(newEqualValue);
    }

    public void SetValue(String operator, int value){
        switch (operator) {
            case ">=":
                SetMinValue(value, true);
                break;
            case ">":
                SetMinValue(value, false);
                break;
            case "<=":
                SetMaxValue(value, true);
                break;
            case "<":
                SetMaxValue(value, false);
                break;
            default:
                SetEqualValue(value);
                break;
        }
    }

    
    public boolean compareTypes(BaseType type) {
        IntegerType t = (IntegerType) type;
        if (t.minValue == null && t.maxValue == null && t.equalValue == null
            && this.minValue == null && this.maxValue == null && this.equalValue == null) {
            return true;
        } else if (this.equalValue.equals(t.equalValue) 
                && this.minValue == null && this.maxValue == null
                && t.minValue == null && t.maxValue == null) {
            return true;
        } else if (this.minValue.equals(t.minValue) && this.withGivenMinValue == t.withGivenMinValue
                && this.maxValue.equals(t.maxValue) && this.withGivenMaxValue == t.withGivenMaxValue) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Integer";
    }

    public String toString(String minOrMaxOrEqual){
        switch (minOrMaxOrEqual){
            case "min": 
                return minValue.toString();
            case "max": 
                return maxValue.toString();
            case "equal": 
                return equalValue.toString();
            default: 
                return "";
        }
    }
    //hej >= 5 AND hej = 5  → constraint error
    @Override
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode) {
        
        return true;
    }
    private void CompareMinValue(String id, IntegerType t, SimpleNode parentNode) throws RedundantSyntaxException, DuplicationException, ConstraintException{
        if(this.minValue != null && this.withGivenMinValue){
           if(this.minValue.equals(t.maxValue) && t.withGivenMaxValue){
               throw new RedundantSyntaxException(id, parentNode, this.minValue);
           } else if(t.equalValue != null){
               throw new ConstraintException(id, parentNode, "less than or equal", minValue);
           }else if (t.minValue != null){
               if(this.minValue.equals(t.minValue)&& t.withGivenMinValue){
                   throw new DuplicationException(id, parentNode, "less than",this.minValue);
               }
               else {
                   throw new ConstraintException(id, parentNode, "less than or equal", minValue); //hvad skal der stå i type
               }
            
           }
           else if (this.minValue != null && !this.withGivenMinValue){
               if(t.equalValue.size() != 0){
                   throw new ConstraintException(id, parentNode, "less than", minValue);
               } else if(t.minValue != null){
                   if(this.minValue.equals(t.minValue) && !t.withGivenMinValue){
                        throw new DuplicationException(id, parentNode, this.minValue);
                   } else {
                       throw new ConstraintException(id, parentNode, "", minValue);
                   }
               }
           }
        }
    }
    private void CompareMaxValue(String id, IntegerType t, SimpleNode parentNode) throws ConstraintException, DuplicationException, RedundantSyntaxException{
        if(this.maxValue != null && this.withGivenMaxValue){
            if(t.equalValue.size() != 0){
                throw new ConstraintException(id, parentNode, "bigger than or equal", maxValue);
            } else if (t.maxValue != null){
                if(this.maxValue.equals(t.maxValue) && t.withGivenMaxValue) {
                    throw new DuplicationException(id, parentNode, "bigger than or equal", this.maxValue);
                } else {
                    throw new ConstraintException(id, parentNode, "bigger than or equal", maxValue);
                }
            }
        } else if(this.maxValue != null && !this.withGivenMaxValue){
            if(t.equalValue.size() != 0){
                throw new ConstraintException(id, parentNode, "bigger than or equal", maxValue);
            } else if (t.maxValue != null){
                if(this.maxValue.equals(t.maxValue) && !t.withGivenMaxValue){
                    throw new DuplicationException(id, parentNode, "bigger than", this.maxValue); 
                } else {
                    throw new ConstraintException(id, parentNode, "bigger than", maxValue);
                }
            }
        } else {
            this.Set
        }
    }
    /** hej >= 5 OR hej <= 5 → reduntant syntax warning
    hej < 5 OR hej = 5 → reduntant syntax warning
    hej >= 5 OR hej = 5 → reduntant syntax warning */
    @Override
    public boolean compareTypesOr(BaseType type){
        return true;
    }
}
