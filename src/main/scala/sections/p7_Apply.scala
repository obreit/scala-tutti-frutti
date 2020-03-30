package sections

// Apply

import sections.p3_Functions.{add, oneParam, oneParamDef}

object p7_Apply {

  // Scala has a special rule for any method that is called 'apply'
  // Instead of doing X.apply(...), you can do X(...)
  oneParam(1) // same as
  oneParam.apply(1) // this

  // Everything is an "object" (object meaning an instance of a class -- don't confuse with the 'object' keyword which creates singletons)
  // in Scala. That means, function values are just syntactic sugar for sth like
  val overblownAdd /* : (Int, Int) => Int */ = new Function2[Int, Int, Int] { // read as [Input1,Input2,Output]
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  overblownAdd(2, 1)
  overblownAdd.apply(2, 1)

  // this function works exactly the same as the 'add' from p3_Functions
  add.apply(2, 1)

  // Methods don't quite work like that
  // oneParamDef.apply(1) <-- doesn't compile, method needs to be "lifted" first
  val oneParamDefLifted /*: Int => String*/ = oneParamDef _ // --> lifting, can also be written as oneParamDef(_)
  oneParamDefLifted.apply(1)

  class ApplyClass(i: Int) {
    def apply(s: String): String = s"$s$i" // <-- this is my own apply, the rule also holds for it here
  }
  val applyClass = new ApplyClass(1)
  applyClass("number_")
  applyClass.apply("number_")

  class MyCaseClassApproximation(val i: Int, val s: String) {
    import MyCaseClassApproximation._ // <-- note that import statements can be plastered everywhere not just atop the file
    val y = x + 1
  }
  object MyCaseClassApproximation {
    // This is called a 'companion object'. An object that is in the same file as the class and has the same name
    // as the class is its companion.
    // Scala automatically creates companion objects for case classes, and adds 'apply' and 'unapply' (see p8_PatternMatching) methods

    // note that i have access to 'x' in my case class above, even though it's private (that's a feature of companion object, but
    // we don't use it that much)
    private val x = 1

    // sth like this is created by default for case classes
    def apply(i: Int, s: String): MyCaseClassApproximation =
      new MyCaseClassApproximation(i, s)
  }
  // MyCaseClassApproximation.x --> x is not accessible from here
  val caseclassApprox = MyCaseClassApproximation(1, "bar")
  println(caseclassApprox.s)
}