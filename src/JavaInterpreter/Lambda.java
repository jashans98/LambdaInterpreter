package JavaInterpreter;

/**
 * Created by Jashan Shewakramani
 * Base class for lambda
 */
public class Lambda extends Expression{
    private String argument;
    private Expression body;

    protected Lambda(String arg, Expression body) {
        this.argument = arg;
        this.body  = body;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Expression getBody() {
        return body;
    }

    public void setBody(Expression body) {
        this.body = body;
    }
}
