package ScalaInterpreter


object ScalaApp {
  object λ {
    case class Var(name: Symbol) extends Expr
    case class Lambda(name: Symbol, body: Expr) extends Expr
    case class Apply(fun: Expr, arg: Expr) extends Expr

    abstract class Expr {
      def :: (name: Symbol): Expr = Lambda(name, this)
      def @@(arg: Expr): Expr = Apply(this, arg)
      override def toString = prettyPrint(this)
    }

    implicit def toVar(name: Symbol): Expr = Var(name)

    def prettyPrint(expr: Expr, followedByStuff: Boolean = false): String = expr match {
      case Lambda(name, e2) if followedByStuff => "λ" + name.toString.substring(1) + "." + e2
      case Lambda(name, e2) => "λ" + name.toString.substring(1) + "." + e2
      case Apply(e1, e2) => "(" + e1 + " " + e2 + ")"
      case Var(name) => name.toString.substring(1)
    }
  }

  import λ._

  val step: PartialFunction[Expr, Expr] = {
    case Apply(Lambda(name, body), arg) => {
      subst(body, name, arg)
    }
    case Lambda(name, body) if step.isDefinedAt(body) => {
      Lambda(name, step(body))
    }
    case Apply(e1, e2) if step.isDefinedAt(e1) => {
      Apply(step(e1), e2)
    }
    case Apply(e1, e2) if step.isDefinedAt(e2) => {
      Apply(e1, step(e2))
    }
  }

  def subst(expr: Expr, name: Symbol, replacement: Expr): Expr = expr match {
    case Var(name2) => {
      if (name == name2) {
        replacement
      } else {
        expr
      }
    }
    case Apply(fun, arg) => Apply(subst(fun, name, replacement), subst(arg, name, replacement))
    case Lambda(name2, body) => {
      val newName2 = uniqueName(name2, freeVariables(replacement) ++ Set(name))
      val newBody = subst(body, name2, newName2)
      Lambda(newName2, subst(newBody, name, replacement))
    }
  }

  def reduce(expr: Expr): Expr = {
    if (step.isDefinedAt(expr)) {
      reduce(step(expr))
    }
    else {
      expr
    }
  }

  def uniqueName(name: Symbol, badNames: Set[Symbol]): Symbol = {
    if (badNames.contains(name)) {
      uniqueName(Symbol(name.toString.substring(1) + "'"), badNames)
    } else {
      name
    }
  }

  def freeVariables(expr: Expr): Set[Symbol] = expr match {
    case Var(name) => Set(name)
    case Apply(fun, arg) => freeVariables(fun) ++ freeVariables(arg)
    case Lambda(name, body) => freeVariables(body) -- Set(name)
  }


  def main(args: Array[String]) = {
    val I = 'x :: 'x
    val ZERO = 'f :: 'x :: 'x
    val SUCC = 'n :: 'f :: 'x :: ('f @@ ('n @@ 'f @@ 'x))
    val ONE = reduce(SUCC @@ ZERO)
    def NUM(i : Int): Expr = if (i == 0) ZERO else reduce(SUCC @@ NUM(i - 1))
    def PLUS = 'm :: 'n :: 'f :: 'x :: ('m @@ 'f @@ ('n @@ 'f @@ 'x))

    val TRUE = 'x :: 'y :: 'x
    val FALSE = 'x :: 'y :: 'y

    val IF = 'cond :: 'then :: 'else :: 'cond @@ 'then @@ 'else

    val PAIR = 'car :: 'cdr
    print(ZERO)
    print(SUCC)
  }
}