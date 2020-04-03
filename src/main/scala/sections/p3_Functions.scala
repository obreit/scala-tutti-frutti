package sections

// Functions
object p3_Functions {

  // 'add' is a so called a function value
  val add = (x: Int, y: Int) => x + y

  println(add(42, 1))

  val noParams = () => 1
  println(noParams) // function value, prints sth like a reference to the function ...Function...Lambda...
  println(noParams()) // prints the result of the function

  // methods can be used in a similar way but have some differences
  def noParamsDef(): Int = 1
  println(noParamsDef) // compiler warning but function is actually called and the result is printed!
  println(noParamsDef()) // exactly the same

  val oneParam = (i: Int) => i.toString
  def oneParamDef(i: Int): String = {
    val x = i + 1
    x.toString // no return statement
  }

  // println(oneParamDef) //<- doesn't compile (also check the error message in sbt, it's useful)!
  println(oneParam) // function value

  // big difference between function and method when using generics
  def genericDef[T](t: T): String = t.toString
  // val genericFunction[T] = (t: T) => t.toString <-- not possible

  /*
  // Exercise
  val multiply = ???
  println(multiply(5, 2)) // implement multiply

  val funStuff = () => multiply(5, 2)
  println(funStuff) // -> what is printed?

  def curios(): Int = multiply(5, 2)
  println(curios) // -> what is printed?

  def show(i: Int): String = i.toString
  // println(show) // <- what is printed?
   */
}
