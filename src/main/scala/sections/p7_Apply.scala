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

  // this 'overblownAdd' function works exactly the same as the 'add' from p3_Functions
  add.apply(2, 1)

  // Methods don't quite work like that
  // oneParamDef.apply(1) <-- doesn't compile, method needs to be "lifted" first
  val oneParamDefLifted /*: Int => String*/ = oneParamDef _ // --> lifting, can also be written as oneParamDef(_)
  oneParamDefLifted.apply(1)

  // the lifting also works for methods that take multiple parameters
  def twoParam(x: Int, y: Int): Int = x + y
  val twoParamLifted = twoParam _
  twoParamLifted(40, 2)
  val twoParamLiftedAlternative = twoParam(_, _) // alternativ syntax (but becomes cumbersome for more parameters)
  twoParamLiftedAlternative(40, 2)

  // you can also only "partially lift" a method with multiple parameters like this
  val add5 = twoParam(_, 5) // this creates a function that only takes 1 parameter
  add5(10)
  add5(20)

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

  /*
  // Exercise
  val multiply /* add type here */ = (x, y) => x * y // add the type to make this compile

  val multiplyWithoutSugar = ??? // implement an addition function without syntactic sugar
  println(multiplyWithoutSugar(5, 2))

  def defAddition(x: Int, y: Int): Int = x + y
  val defAddLifted = ??? // make this compile
  println(defAddLifted.apply(41, 1))

  class MyClass(a: Int, b: Int)
  val myClass = MyClass(1, 2) // add code to make this compile
   */
}
