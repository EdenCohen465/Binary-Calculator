// ID: 318758778

import java.util.Map;

/**
 * @author Eden Cohen.
 * Create new unary expression that represents the operator Xnor (#).
 */
public class Xnor extends BinaryExpression implements Expression {

    /**
     * Constructor.
     *
     * @param x first expression.
     * @param y second expression.
     */
    public Xnor(Expression x, Expression y) {
        super(x, y);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Recursion evaluation on the expressions.
        Boolean xVal = getExpression1().evaluate(assignment);
        Boolean yVal = getExpression2().evaluate(assignment);
        return xVal == yVal;
    }

    @Override
    public Boolean evaluate() throws Exception {

        // Return true if both vals equals.
        Boolean xVal = getExpression1().evaluate();
        Boolean yVal = getExpression2().evaluate();
        return xVal == yVal;
    }

    @Override
    public String toString() {
        return "(" + getExpression1().toString() + " # " + getExpression2().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // Recursion on the expression in the fields and return new and expression.
        return new Xnor(getExpression1().assign(var, expression), getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {

        // [ ( A NAND A ) NAND ( B NAND B ) ] NAND ( A NAND B )
        return new Nand(new Nand(new Nand(getExpression1(), getExpression1()).nandify(), new Nand(getExpression2(),
                getExpression2()).nandify()).nandify(),
                new Nand(getExpression1(), getExpression2()).nandify()).nandify();
    }

    @Override
    public Expression norify() {

        // [ A NOR ( A NOR B ) ] NOR [ B NOR ( A NOR B ) ]
        return new Nor(new Nor(getExpression1(), new Nor(getExpression1(), getExpression2()).norify()).norify(),
                new Nor(getExpression2(), new Nor(getExpression1(), getExpression2()).norify()).norify()).norify();
    }

    @Override
    public Expression simplify() {

        Expression simple1 = getExpression1().simplify();
        Expression simple2 = getExpression2().simplify();

        // F # T  = F
        try {
            if (simple1.evaluate() && !simple2.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // T # F = F
        try {
            if (simple2.evaluate() && !simple1.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // x # x = T
        try {
            if (getExpression1().toString().equals(getExpression2().toString())) {
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        // Check if the simplified argument are equal.
        try {
            if (simple1.toString().equals(simple2.toString())) {
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        // We can't simplify, return original expression - simplify each argument.
        return new Xnor(simple1, simple2);
    }
}
