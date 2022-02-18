// ID: 318758778

import java.util.Map;

/**
 * @author Eden Cohen.
 * Create new binary expression that represent the operator And (&).
 */
public class And extends BinaryExpression implements Expression {

    /**
     * Constructor.
     *
     * @param x first expression.
     * @param y second expression.
     */
    public And(Expression x, Expression y) {
        super(x, y);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Recursion evaluation on the expressions.
        Boolean xVal = getExpression1().evaluate(assignment);
        Boolean yVal = getExpression2().evaluate(assignment);
        return (xVal && yVal);
    }

    @Override
    public Boolean evaluate() throws Exception {

        // If both expressions true- return true.
        Boolean xVal = getExpression1().evaluate();
        Boolean yVal = getExpression2().evaluate();
        return (xVal && yVal);
    }

    @Override
    public String toString() {
        return "(" + getExpression1().toString() + " & " + getExpression2().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // Recursion on the expression in the fields and return new and expression.
        return new And(getExpression1().assign(var, expression), getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {

        //( A NAND B ) NAND ( A NAND B )
        return new
                Nand(new Nand(getExpression1(), getExpression2()).nandify(),
                new Nand(getExpression1(), getExpression2()).nandify()).nandify();
    }

    @Override
    public Expression norify() {

        // ( A NOR A ) NOR ( B NOR B )
        return new Nor(new Nor(getExpression1(), getExpression1()).norify(), new Nor(getExpression2(),
                getExpression2()).norify()).norify();
    }

    @Override
    public Expression simplify() {
        Expression exp1 = getExpression1().simplify();
        Expression exp2 = getExpression2().simplify();
        // F & X = F
        try {
            if (!exp1.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // X & F = F
        try {
            if (!exp2.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // T & X = X
        try {
            if (exp1.evaluate()) {
                return exp2;
            }
        } catch (Exception ignored) {
            ;
        }

        // X & T = X
        try {
            if (exp2.evaluate()) {
                return exp1;
            }
        } catch (Exception ignored) {
            ;
        }

        // X & X = X
        try {
            if (getExpression1().toString().equals(getExpression2().toString())) {
                return exp1;
            }
        } catch (Exception ignored) {
            ;
        }

        // Check if the simplified expression are equal.
        try {
            if (exp1.toString().equals(exp2.toString())) {
                return exp1;
            }
        } catch (Exception ignored) {
            ;
        }

        // We can't simplify, return original expression- and simplify each argument.
        return new And(exp1, exp2);
    }
}
