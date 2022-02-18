// ID: 318758778
import java.util.Map;

/**
 * @author Eden Cohen.
 */
public class Xor extends BinaryExpression implements Expression {

    /**
     * Constructor.
     * @param x first expression.
     * @param y second expression.
     * Create new binary expression that represents the operator Xor (^).
     */
    public Xor(Expression x, Expression y) {
        super(x, y);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Recursion evaluation on the expressions.
        Boolean xVal = getExpression1().evaluate(assignment);
        Boolean yVal = getExpression2().evaluate(assignment);
        return (xVal ^ yVal);
    }

    @Override
    public Boolean evaluate() throws Exception {

        // If only one true - return true.
        Boolean xVal = getExpression1().evaluate();
        Boolean yVal = getExpression2().evaluate();
        return (xVal ^ yVal);
    }

    @Override
    public String toString() {
        return "(" + getExpression1().toString() + " ^ " + getExpression2().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // Recursion on the expression in the fields and return new and expression.
        return new Xor(getExpression1().assign(var, expression), getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {

        //[ A NAND ( A NAND B ) ] NAND [ B NAND ( A NAND B ) ]
        return new Nand(new Nand(getExpression1(), new Nand(getExpression1(), getExpression2()).nandify()).nandify(),
                new Nand(getExpression2(), new Nand(getExpression1(), getExpression2()).nandify()).nandify()).nandify();
    }

    @Override
    public Expression norify() {

        //[ ( A NOR A ) NOR ( B NOR B ) ] NOR ( A NOR B )
        return new Nor(new Nor(new Nor(getExpression1(), getExpression1()).norify(), new Nor(getExpression2(),
                getExpression2()).norify()).norify(),
                new Nor(getExpression1(), getExpression2()).norify()).norify();
    }

    @Override
    public Expression simplify() {

        Expression simple1 = getExpression1().simplify();
        Expression simple2 = getExpression2().simplify();

        // X ^ T = ~(X)
        try {
            if (simple2.evaluate()) {
                return new Not(simple1).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // T ^ X = ~(X)
        try {
            if (simple1.evaluate()) {
                return new Not(simple2).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        // X ^ F = X
        try {
            if (!simple2.evaluate()) {
                return simple1;
            }
        } catch (Exception ignored) {
            ;
        }

        // F ^ X = X
        try {
            if (!simple1.evaluate()) {
                return simple2;
            }
        } catch (Exception ignored) {
            ;
        }

        // X ^ X = 0
        try {
            if (getExpression1().toString().equals(getExpression2().toString())) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // Check if the simplify expressions are equal.
        try {
            if (simple1.toString().equals(simple2.toString())) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        // We can't simplify, return original expression and simplify each argument.
        return new Xor(simple1, simple2);
    }
}