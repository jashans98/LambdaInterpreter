package JavaInterpreter;

/**
 * Created by Jashan Shewakramani
 * Description: Lambda Application base class
 */
public class Application extends Expression {
    private Expression function;
    private Expression argument;

    public Application(Expression function, Expression argument) {
        this.function = function;
        this.argument = argument;
    }

    public Expression getFunction() {
        return function;
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public Expression getArgument() {
        return argument;
    }

    public void setArgument(Expression argument) {
        this.argument = argument;
    }
}
