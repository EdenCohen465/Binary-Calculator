// ID: 318758778

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Eden Cohen.
 * Check the correctness and the possibilities of the project.
 */
public class ExpressionsTest {

    /**
     * Main method to show the possibilities of the project.
     * @param args from command line.
     */
    public static void main(String[] args) {

        // Create new Expression.
        Var x = new Var("x");
        Var y = new Var("y");
        Var z = new Var("z");

        Expression e = new And(new Or(x, y), new Or(z, new Val(false)));

        // Print the expression:
        System.out.println(e.toString());

        // Print the value of the expression with an assignment to every variable:
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);

        // Print the new expression with the assignment and the evaluation.
        try {
            Val v = new Val(e.evaluate(assignment));
            System.out.println(v.toString());
        } catch (Exception ignored) {
            ;
        }

        // Print the nandified version of the expression.
        System.out.println(e.nandify().toString());

        // Print the norified version of the expression.
        System.out.println(e.norify().toString());

        // Print the simplified version of the string.
        System.out.println(e.simplify().toString());
    }
}
