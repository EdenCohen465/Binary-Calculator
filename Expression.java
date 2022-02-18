// ID: 318758778
import java.util.List;
import java.util.Map;

/**
 * @author Eden Cohen.
 */
public interface Expression {

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment, an exception is thrown.
     * @param assignment map that saves the variables and his value.
     * @return true or false.
     * @throws Exception if the variables is not in the map.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method. Like the "evaluate(assignment)" method above, but uses an empty assignment.
     * @return true or false.
     * @throws Exception if the expression is not value.
     */
    Boolean evaluate() throws Exception;

    /**
     * @return a list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * @return a nice string representation of the expression.
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided
     * expression (Does not modify the current expression).
     * @param var to replace with the given expression.
     * @param expression to replace the var with.
     * @return expression.
     */
    Expression assign(String var, Expression expression);

    /**
     * @return the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    Expression nandify();

    /**
     * @return the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    Expression norify();

    /**
     * @return a simplified version of the current expression.
     */
    Expression simplify();
}