import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Eden Cohen.
 * Abstract class that generates to the classes of binarry expressions.
 */
public abstract class BinaryExpression extends BaseExpression {

    // Fields
    private Expression expression1;
    private Expression expression2;

    /**
     * Constructor.
     * @param x expression.
     * @param y expression
     */
    public BinaryExpression(Expression x, Expression y) {
        this.expression1 = x;
        this.expression2 = y;
    }

    /**
     * @return a the first expression.
     */
    protected Expression getExpression1() {
        return this.expression1;
    }

    /**
     * @return a the second expression.
     */
    protected Expression getExpression2() {
        return this.expression2;
    }

    /**
     * Set the expression.
     * @param e - the new expression.
     */
    protected void setExpression1(Expression e) {
        this.expression1 = e;
    }

    /**
     * Set the expression.
     * @param e - the new expression.
     */
    protected void setExpression2(Expression e) {
        this.expression2 = e;
    }

    /**
     * @return list of variables.
     */
    public List<String> getVariables() {

        // Union the variables of the two expressions to one list.
        List<String> vars = new ArrayList<>();
        vars.addAll(this.expression1.getVariables());
        vars.addAll(this.expression2.getVariables());

        // Convert the list to set inorder to ignore duplicates.
        Set<String> set = new LinkedHashSet<>(vars);

        // Clear the original list
        vars.clear();

        // Convert the set back to list.
        vars.addAll(set);

        // Return the list
        return vars;
    }
}
