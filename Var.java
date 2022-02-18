import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Eden Cohen.
 */
public class Var implements Expression {

    private final String variable;

    /**
     * Constructor.
     * @param variable of the new Var.
     */
    public Var(String variable) {
        this.variable = variable;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        // Check if the var is in the map, if yes, return it. if not - throw error.
        if (assignment.get(this.variable) != null) {
            return assignment.get(this.variable);
        }

        throw new Exception("Var is not in the assignment");
    }

    @Override
    public Boolean evaluate() throws Exception {
        // Can't evaluate without a map
        throw new Exception();
    }

    @Override
    public List<String> getVariables() {
        List<String> var = new ArrayList<>();
        var.add(this.variable);
        return var;
    }

    @Override
    public String toString() {
        return this.variable;
    }

    @Override
    public Expression assign(String var, Expression expression) {

        // If the given variable is equals to the this var, return the given expression.
        if (this.variable.equals(var)) {
            return expression;
        }
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
