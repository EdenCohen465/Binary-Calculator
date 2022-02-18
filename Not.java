// ID: 318758778

import java.util.Map;

/**
 * @author Eden Cohen.
 * Create new unary expression that represents the operator Not (~).
 */
public class Not extends UnaryExpression implements Expression {

    /**
     * Constructor.
     *
     * @param x expression.
     */
    public Not(Expression x) {
        super(x);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Recursion evaluation on the expression.
        Boolean xVal = getExpression().evaluate(assignment);
        return (!xVal);
    }

    @Override
    public Boolean evaluate() throws Exception {
        // Negate the val.
        Boolean xVal = getExpression().evaluate();
        return (!xVal);
    }

    @Override
    public String toString() {
        return "~(" + getExpression().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // Recursion on the expression in the fields and return new and expression.
        return new Not(getExpression().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(getExpression(), getExpression()).nandify();
    }

    @Override
    public Expression norify() {

        // A NOR A
        return new Nor(getExpression(), getExpression()).norify();
    }

    @Override
    public Expression simplify() {

        Expression simple = getExpression().simplify();

        // ~T = F
        try {
            if (simple.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // ~F = T
        try {
            if (!simple.evaluate()) {
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        // can't simplify
        return new Not(simple);
    }
}