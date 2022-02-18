import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Eden Cohen.
 */
public abstract class UnaryExpression extends BaseExpression {

    // Fields
    private Expression expression;

    /**
     * Constructor.
     * @param x expression.
     */
    public UnaryExpression(Expression x) {
        this.expression = x;
    }

    /**
     * @return the Expression.
     */
    protected Expression getExpression() {
        return this.expression;
    }

    /**
     * Set the expression.
     * @param e - the new expression.
     */
    protected void setExpression(Expression e) {
        this.expression = e;
    }
    /**
     * @return the list of the variables.
     */
    public List<String> getVariables() {

        // union the variables of the two expressions to one list.
        List<String> vars = new ArrayList<>();
        vars.addAll(this.expression.getVariables());

        // convert the list to set inorder to ignore duplicates.
        Set<String> set = new LinkedHashSet<>(vars);

        // Clear the original list
        vars.clear();

        // Convert the set back to list.
        vars.addAll(set);

        // Return the list
        return vars;
    }
}
