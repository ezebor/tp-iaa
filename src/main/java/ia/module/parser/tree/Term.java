package ia.module.parser.tree;

import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Operator;

import java.util.ArrayList;
import java.util.List;

public class Term {

    public boolean positive;
    public ExpressionNode expression;

    public Term(boolean positive, ExpressionNode expression) {
        super();
        this.positive = positive;
        this.expression = expression;
    }

    public Boolean hasVariable(){
        return this.expression.hasVariable();
    }

    public Boolean isVariable(){
        return this.expression.isVariable();
    }

    public Boolean isQuadraticX(){
        return this.expression.isQuadraticX();
    }

    public Boolean isNumber(){
        return this.expression.isNumber();
    }

    public Integer getLevel(){
        return expression.getLevel();
    }

    public Boolean isLineal(){
        return this.expression.isLineal();
    }

    public Boolean isZero(){
        return this.expression.isZero();
    }

    public Boolean isFractionalNumber(){
        return this.expression.isFractionalNumber();
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return this.expression.getStructureOf(expressionsWithArgumentStructures);
    }

    public Integer getToken(){
        return this.expression.getToken();
    }

    public ExpressionNode normalize(){
        return this.expression.normalize();
    }

    public Integer getDegree(){
        return this.expression.getDegree();
    }

    public List<Operator> getListOfTokens(){
        List<Operator> tokens = new ArrayList<>();
        tokens.addAll(this.expression.getListOfTokens());
        return tokens;
    }

    public Boolean contains(Integer operator){
        return this.expression.contains(operator);
    }

    public Term simplify(){
        return new Term(this.positive, this.expression.simplify());
    }
}
