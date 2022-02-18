// ID: 318758778

import java.util.Map;

/**
 * @author Eden Cohen.
 * Creates new binary expression that represent the operator Nand (↑).
 */
public class Nand extends BinaryExpression implements Expression {

    /**
     * Constructor.
     * @param x first expression.
     * @param y second expression.
     */
    public Nand(Expression x, Expression y) {
        super(x, y);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Recursion evaluation on the expressions.
        Boolean xVal = getExpression1().evaluate(assignment);
        Boolean yVal = getExpression2().evaluate(assignment);
        return !(xVal && yVal);
    }

    @Override
    public Boolean evaluate() throws Exception {

        // Check the result of and operator and negate it.
        Boolean xVal = getExpression1().evaluate();
        Boolean yVal = getExpression2().evaluate();
        return !(xVal && yVal);
    }

    @Override
    public String toString() {
        return "(" + getExpression1().toString() + " A " + getExpression2().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // Recursion on the expression in the fields and return new and expression.
        return new Nand(getExpression1().assign(var, expression), getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(getExpression1().nandify(), getExpression2().nandify());
    }

    @Override
    public Expression norify() {

        //[ ( A NOR A ) NOR ( B NOR B ) ] NOR [ ( A NOR A ) NOR ( B NOR B ) ]
        return new Nor(new Nor(new Nor(getExpression1(), getExpression1()).norify(), new Nor(getExpression2(),
                getExpression2()).norify()).norify(), new Nor(new Nor(getExpression1(), getExpression1()).norify(),
                new Nor(getExpression2(), getExpression2()).norify()).norify()).norify();
    }

    @Override
    public Expression simplify() {

        Expression simple1 = getExpression1().simplify();
        Expression simple2 = getExpression2().simplify();

        try {
            // x ↑ T = ~(x)
            if (simple2.evaluate()) {
                return new Not(simple1).simplify();
            }
        } catch (Exception ignored) {
            ;
        }


        // T ↑ x = ~(x)
        try {
            if (simple1.evaluate()) {
                return new Not(simple2).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // x ↑ F = T
        try {
            if (!simple2.evaluate()) {
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        // F ↑ x = T
        try {
            if (!simple1.evaluate()) {
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        // x ↑ x = ~(x)
        try {
            if (getExpression1().toString().equals(getExpression2().toString())) {
                return new Not(simple1).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // Check if the simplified expression are equal.
        try {
            if (simple1.toString().equals(simple2.toString())) {
                return new Not(simple1).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // We can't simplify, return original expression and simplify each argument.
        return new Nand(simple1, simple2);
    }
}
