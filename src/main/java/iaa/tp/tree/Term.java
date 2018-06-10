package iaa.tp.tree;

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

    public Integer getLevel(){
        return expression.getLevel();
    }
}
