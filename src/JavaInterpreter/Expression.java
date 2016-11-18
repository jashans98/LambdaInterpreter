package JavaInterpreter;

/**
 * Created by Jashan Shewakramani
 * Description: Expr class for interpreter
 */
public abstract class Expression {
    public Lambda attachToLambda(String name) {
        return new Lambda(name, this);
    }

    public Expression apply(Expression argument) {
        return new Application(this, argument);
    }

    public String toString() {
        if (this instanceof Lambda) {
            Lambda p = (Lambda) this;
            return "(λ" + p.getArgument() + "." + p.getBody().toString() + ")";
        }
        if (this instanceof Application) {
            Application a = (Application) this;
            return "(" + a.getFunction().toString() + " "
                    + a.getArgument().toString() + ")";
        }

        if (this instanceof Variable) {
            Variable a = (Variable) this;
            return a.getVarName();
        }

        // we'll never reach here
        return null;
    }
}
