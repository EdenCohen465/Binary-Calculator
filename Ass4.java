import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class Ass4 {
    private List<Expression> expressions;
    private List<Map<String, Boolean>> assignments;
    private Map<String, String> results;
    private Map<String, String> answers;

    public Ass4() {
        this.expressions = new ArrayList<>();
        this.assignments = new ArrayList<>();
        this.results = new TreeMap<>();
        this.answers = read_answers("answers");
    }

    public static Map<String, String> read_answers(String filename) {
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(filename));
            return (Map<String, String>) o.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public void write_answers(String filename) {
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filename));
            o.writeObject(this.results);
        } catch (Exception e) {
            System.out.println("Writing Error");
        }
    }

    public void add_exp() {
        this.expressions.add(new Not(new Var("X")));
        this.expressions.add(new And(new Var("X"), new Var("X")));
        this.expressions.add(new Or(new Var("X"), new Var("X")));
        this.expressions.add(new Xor(new Var("X"), new Var("X")));
        this.expressions.add(new Nand(new Var("X"), new Var("X")));
        this.expressions.add(new Nor(new Var("X"), new Var("X")));
        this.expressions.add(new Xnor(new Var("X"), new Var("X")));

        this.expressions.add(new And(new Var("X"), new Var("Y")));
        this.expressions.add(new Or(new Var("X"), new Var("Y")));
        this.expressions.add(new Xor(new Var("X"), new Var("Y")));
        this.expressions.add(new Nand(new Var("X"), new Var("Y")));
        this.expressions.add(new Nor(new Var("X"), new Var("Y")));
        this.expressions.add(new Xnor(new Var("X"), new Var("Y")));

        this.expressions.add(new And(new Var("X"), new Or(new Var("Y"), new Var("Z"))));
        this.expressions.add(new And(new Var("X"), new Xor(new Var("Y"), new Var("Z"))));
        this.expressions.add(new Or(new Var("X"), new Nand(new Var("Y"), new Var("Z"))));
        this.expressions.add(new Or(new Var("X"), new Nor(new Var("Y"), new Var("Z"))));
        this.expressions.add(new Not(new Xnor(new Var("X"), new Nor(new Var("Y"), new Var("Z")))));
        this.expressions.add(new Not(new Xor(new And(new Val(true), new Or(new Var("X"), new Var("Y"))), new Var("Z"))));
    }

    public void add_exp_comp() {
        Expression a = new And(new Var("X"), new Var("Y"));
        Expression b = new Xor(new Var("Y"), new Var("Z"));
        Expression exp = a;
        exp = exp.assign("X", b);
        exp = exp.assign("Z", a);
        exp = exp.assign("Y", b);
        this.expressions.add(a);
        this.expressions.add(b);
        this.expressions.add(exp);
    }

    public void add_ass() {
        Map<String, Boolean> ass31 = new TreeMap<>();
        ass31.put("X", true);
        ass31.put("Y", true);
        ass31.put("Z", true);
        this.assignments.add(ass31);
        Map<String, Boolean> ass32 = new TreeMap<>();
        ass32.put("X", true);
        ass32.put("Y", true);
        ass32.put("Z", false);
        this.assignments.add(ass32);
        Map<String, Boolean> ass33 = new TreeMap<>();
        ass33.put("X", true);
        ass33.put("Y", false);
        ass33.put("Z", true);
        this.assignments.add(ass33);
        Map<String, Boolean> ass34 = new TreeMap<>();
        ass34.put("X", false);
        ass34.put("Y", true);
        ass34.put("Z", true);
        this.assignments.add(ass34);
        Map<String, Boolean> ass35 = new TreeMap<>();
        ass35.put("X", true);
        ass35.put("Y", false);
        ass35.put("Z", false);
        this.assignments.add(ass35);
        Map<String, Boolean> ass36 = new TreeMap<>();
        ass36.put("X", false);
        ass36.put("Y", true);
        ass36.put("Z", false);
        this.assignments.add(ass36);
        Map<String, Boolean> ass37 = new TreeMap<>();
        ass37.put("X", false);
        ass37.put("Y", false);
        ass37.put("Z", true);
        this.assignments.add(ass37);
        Map<String, Boolean> ass38 = new TreeMap<>();
        ass38.put("X", false);
        ass38.put("Y", false);
        ass38.put("Z", false);
        this.assignments.add(ass38);
    }

    public void generate_results() {
        for (int i = 0; i < this.expressions.size(); i++) {
            for (int j = 0; j < this.assignments.size(); j++) {
                Expression exp = this.expressions.get(i);
                Map<String, Boolean> ass = this.assignments.get(j);
                String key = String.format("%02d.%02d.", i + 1, j + 1);
                String value;

                try {
                    value = exp.evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "1", value);

                try {
                    value = exp.simplify().evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "2", value);

                try {
                    value = exp.nandify().evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "3", value);

                try {
                    value = exp.nandify().simplify().evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "4", value);

                try {
                    value = exp.norify().evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "5", value);

                try {
                    value = exp.norify().simplify().evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "6", value);

                try {
                    value = exp.simplify().simplify().evaluate(ass).toString();
                } catch (Exception e) {
                    value = "Exp";
                }
                results.put(key + "7", value);
            }
        }
    }

    public static float test_nandify() {
        Expression e = new And(new Var("X"), new Or(new Var("X"), new Xor(new Var("X"), new Nand(new Var("X"), new Nor(new Var("X"), new Xnor(new Var("X"), new Not(new Var("X"))))))));
        String s = (e.nandify()).toString().replaceAll("[X ()A]", "");
        if (s.equals("")) {
            System.out.println("PASS: Nandify");
            return 0;
        } else {
            System.out.println("FAIL: Nandify: " + s);
            return 5;
        }
    }

    public static Double test_norify() {
        Expression e = new And(new Var("X"), new Or(new Var("X"), new Xor(new Var("X"), new Nand(new Var("X"), new Nor(new Var("X"), new Xnor(new Var("X"), new Not(new Var("X"))))))));
        String s = (e.norify()).toString().replaceAll("[X ()V]", "");
        if (s.equals("")) {
            System.out.println("PASS: Norify");
            return 0.0;
        } else {
            System.out.println("FAIL: Norify: " + s);
            return 5.0;
        }
    }

    public void run() {
        if (this.answers == null) {
            System.out.println("write mode");
            this.write_answers("answers");
            return;
        }
        Double grade = 100.0;
        for (Map.Entry<String, String> entry : this.results.entrySet()) {
            String answer = this.answers.get(entry.getKey());
            if (answer.equals(entry.getValue())) {
                System.out.println("PASS: " + entry.getKey() + " The answer is: " + answer);
            } else {
                System.out.println("FAIL: " + entry.getKey() + " Expected: \"" + answer + "\" instead of \"" + entry.getValue() + "\"");
                grade -= 0.08;
            }
        }
        grade -= test_nandify();
        grade -= test_norify();
        System.out.println(this.results);
        System.out.println(Math.round(Double.max(grade, 0)));
    }

    public static void main(String[] args) {
        Ass4 ass4 = new Ass4();
        ass4.add_exp();
        ass4.add_exp_comp();
        ass4.add_ass();
        ass4.generate_results();
        ass4.run();
    }
}
