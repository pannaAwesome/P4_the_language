import java.util.*;
import SyntaxAnalysis.TreeFiles.*;

public class SymbolTableBuilder implements ISymbolTableBuilder {
    public static Map<String,String> dictionary = Collections.synchronizedMap(new TreeMap<String,String>());
    Node root;

    public SymbolTableBuilder(Node root){
        this.root = root;
    }

    public void buildSymbolTable(){
        processNode(this.root);
    }

    private void processNode(Node node){
        if (node instanceof ASTIDEN) {
            ASTIDEN castedNode = (ASTIDEN)node;
            if (dictionary.get(castedNode.toString())==null && castedNode.type=="coloumnName"){
                dictionary.put(castedNode.toString(), castedNode.type);
            }
            // If refrence, lookup
        }

        for (int i = 0; i<node.jjtGetNumChildren(); i++){
            processNode(node.jjtGetChild(i));
        }
    }
}