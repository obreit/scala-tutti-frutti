package sections

// Functions
object p3_Functions {
  val add = (x: Int, y: Int) => x + y
  println(add(42, 1))

  val noParams = () => 1
  def noParamsDef(): Int = 1
  val oneParam = (i: Int) => i.toString
  def oneParamDef(i: Int): String = {
    val x = i + 1
    x.toString // no return statement
  }
  println(noParams) // function value
  println(noParamsDef) // compiler warning but function is called
  // println(oneParam) <- doesn't compile
  println(oneParam) // function value
  println(noParams())
}
