package JavaInterpreter;

/**
 * Created by Jashan Shewakramani
 * Description: Lambda Interpreter
 */
public class Interpreter {
    public static void main(String[] args) {
        Lambda IDENTITY = new Lambda("x", new Variable("x"));

        Lambda TRUE = new Variable("x").attachToLambda("y").attachToLambda("x");

        Lambda FALSE = new Variable("y").attachToLambda("y").attachToLambda("x");

        Application applyIden = new Application(IDENTITY, new Variable("y"));

        Lambda xy = new Lambda("x", new Application(new Variable("x"), new Variable("y")));

        Application applyXy = new Application(xy, new Variable("y"));

        Lambda ZERO = new Variable("x").attachToLambda("x").attachToLambda("f");

        System.out.println(ZERO);


    }

     static Expression substitute(Expression e, String target, Expression replacement) {
        if (e instanceof Variable) {
            Variable v = (Variable) e;
            if (v.getVarName().equals(target))
                return replacement;
            else if ((replacement instanceof Variable) && ((Variable) replacement).getVarName().equals(v.getVarName()))
                return new Variable(v.getVarName() + "'");
            else
                return e;

        }

        if (e instanceof Lambda) {
            Lambda l = (Lambda) e;
            if (l.getArgument().equals(target))
                // just return the expression of the lambda, nicely substituted
                return substitute(l.getBody(), target, replacement);
            else
                // this is basically a higher order lambda, return a new lambda but change the body
                return new Lambda(l.getArgument(), substitute(l.getBody(), target, replacement));
        }

        if (e instanceof Application) {
            Application a = (Application) e;
            // just run substitutions on both the function call and argument
            return new Application(substitute(a.getFunction(), target, replacement),
                    substitute(a.getArgument(), target, replacement));
        }

        // we'll never hit this line
        return null;
    }

    // takes a lambda expression and computes the output on it
    static Expression reduceExpression(Expression e) {
        if (e instanceof Application) {
            Application a = (Application) e;
            if (a.getFunction() instanceof Lambda) {
                // reduce by subbing into the lambda
                Lambda l = (Lambda) a.getFunction();
                return reduceExpression(substitute(l, l.getArgument(), a.getArgument()));
            }
            else
                return e;
        } else {
            return e;
        }
    }

}
