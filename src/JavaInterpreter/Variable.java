package JavaInterpreter;

/**
 * Created by Jashan Shewakramani
 * Description: Base Variable class for Lambda Interpreter
 */
public class Variable extends Expression {
    private String varName;

    public Variable(String name) {
        this.varName = name;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
