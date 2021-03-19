package ContextualAnalysis;

import java.util.*;
import SyntaxAnalysis.TreeFiles.*;

public class SymbolTableBuilder {
    public static Map<String, Integer> dictionary = Collections.synchronizedMap(new TreeMap<String, Integer>());
    // Node root;

    public SymbolTableBuilder(Node root) {
        // this.root = root;
    }

    // public void buildSymbolTable(){
    // processNode(this.root);
    // }

    // private void processNode(Node node){
    // if (node instanceof ASTIDEN) {
    // ASTIDEN castedNode = (ASTIDEN)node;
    // if (dictionary.get(castedNode.toString())==null &&
    // castedNode.type=="coloumnName"){
    // dictionary.put(castedNode.toString(), castedNode.type);
    // }
    // // If refrence, lookup
    // }

    // for (int i = 0; i<node.jjtGetNumChildren(); i++){
    // processNode(node.jjtGetChild(i));
    // }
    // }

    public static void insertNode(String identifierName, int type) {
        dictionary.put(identifierName, type);
    }

    public static int getNodeType(String identifierName) throws Exception {
        Integer result = dictionary.get(identifierName);
        if (result != null) {
            return result.intValue();
        } else {
            throw new Exception("Cannot use " + identifierName + " before it is declared");
        }
    }

    public static boolean hasNodeBeenInserted(String identifierName){
        return dictionary.get(identifierName) != null;
    }
}