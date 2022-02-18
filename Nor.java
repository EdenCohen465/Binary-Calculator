// ID: 318758778

import java.util.Map;

/**
 * @author Eden Cohen.
 * Create new binary expression that represent the operator Nor (↓).
 */
public class Nor extends BinaryExpression implements Expression {

    /**
     * Constructor.
     *
     * @param x first expression.
     * @param y second expression.
     */
    public Nor(Expression x, Expression y) {
        super(x, y);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Recursion evaluation on the expressions- check the
        Boolean xVal = getExpression1().evaluate(assignment);
        Boolean yVal = getExpression2().evaluate(assignment);
        return !(xVal || yVal);
    }

    @Override
    public Boolean evaluate() throws Exception {
        // If both expressions true- return true.
        Boolean xVal = getExpression1().evaluate();
        Boolean yVal = getExpression2().evaluate();
        return !(xVal || yVal);
    }

    @Override
    public String toString() {
        return "(" + getExpression1().toString() + " V " + getExpression2().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // Recursion on the expression in the fields and return new and expression.
        return new Nor(getExpression1().assign(var, expression), getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {

        // [ ( A NAND A ) NAND ( B NAND B ) ] NAND [ ( A NAND A ) NAND ( B NAND B ) ]
        return new Nand(new Nand(new Nand(getExpression1(), getExpression1()).nandify(),
                new Nand(getExpression2(), getExpression2()).nandify()).nandify(),
                new Nand(new Nand(getExpression1(), getExpression1()).nandify(),
                        new Nand(getExpression2(), getExpression2()).nandify()).nandify()).nandify();
    }

    @Override
    public Expression norify() {
        return new Nor(getExpression1().norify(), getExpression2().norify());
    }

    @Override
    public Expression simplify() {

        Expression simple1 = getExpression1().simplify();
        Expression simple2 = getExpression2().simplify();

        // x ↓ T = F
        try {
            if (simple2.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // T ↓ x = F
        try {
            if (simple1.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // x ↓ F = ~(x)
        try {
            if (!simple2.evaluate()) {
                return new Not(simple1).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // F ↓ x = ~(x)
        try {
            if (!simple1.evaluate()) {
                return new Not(simple2).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // x ↓ x = ~(x)
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

        // We can't simplify, return original expression- and simplify each argument.
        return new Nor(simple1, simple2);
    }
}