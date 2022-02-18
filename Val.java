// ID: 318758778
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Eden Cohen.
 */
public class Val implements Expression {

    // Fields:
    private Boolean value;

    /**
     * Constructor.
     * @param val of the expression.
     */
    public Val(Boolean val) {
        this.value = val;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.value;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.value;
    }

    @Override
    public List<String> getVariables() {
        // Return empty list
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        String str;
        if (this.value) {
            str = "T";
            return str;
        }
        str = "F";
        return str;
    }

    @Override
    public Expression assign(String var, Expression expression) {
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
