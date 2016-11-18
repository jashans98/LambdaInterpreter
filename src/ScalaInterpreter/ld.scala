package ScalaInterpreter

/**
  * Created by jashan on 15/11/16.
  */
object ld {
  case class Var(name: Symbol) extends Expr
  case class Lambda(name: Symbol, body: Expr) extends Expr
  case class Apply(fun: Expr, arg: Expr) extends Expr

  abstract class Expr {
    def ::(name: Symbol): Expr = Lambda(name, this)
    def @@(arg: Expr): Expr = Apply(this, arg)
    override def toString = prettyPrint(this)
  }

  implicit def toVar(name: Symbol): Expr = Var(name)

  def prettyPrint(expr: Expr, followedByStuff: Boolean = false): String = expr match {
    case Lambda(name, e2) if followedByStuff => "λ" + name.toString.substring(1) + "." + e2
    case Lambda(name, e2) => "λ" + name.toString().substring(1) + "." + e2
    case Apply(e1, e2) => "(" + e1 + " " + e2 + ")"
    case Var(name) => name.toString.substring(1)
  }
}
