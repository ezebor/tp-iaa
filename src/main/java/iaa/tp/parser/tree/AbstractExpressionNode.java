package iaa.tp.parser.tree;

public class AbstractExpressionNode {

    public Boolean isNumber(){
        return false;
    }

    public Boolean isVariable(){
        return false;
    }

    public Boolean hasVariable(){
        return false;
    }

    public Boolean isMinusOne(){
        return false;
    }

    public Boolean isMinusN(){
        return false;
    }

    public Boolean isOne(){
        return false;
    }

    public Boolean isN(){
        return false;
    }

    public Boolean isFractionalNumber(){
        return false;
    }
}