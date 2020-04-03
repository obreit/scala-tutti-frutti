package sections

// Expressions
object p2_Expressions {

  val x = 10
  val ifelse: String = if(x >= 0) "greater eq 0" else "smaller 0"
  val block: Int = {
    val x = 42 // <- different scope for x!
    x + 50
  }

  // parantheses of methods can be used as blocks, the result of the block will be used as the parameter to the function
  println {
    val y = 1
    y + 10
  }

  // careful with unit return type
  val unitReturn: Unit = {
    val x = 1
    x // x won't be returned --> compiler issues warning
    // compiler adds '()' as last statement here
  }

  // same for methods
  def unitFun(i: Int): Unit = {
    val x = 1
    x // compiler issues warning
  }

  // you don't have to define a return type of a method, but usually it's a good idea
  def unitImplied(i: Int) = {
    val x = i
  }

  /*
  // Exercise
  val blockResult: Unit = {
    val x = 1
    val y = 41
    x + y
  }
  println(blockResult == 42) // change the code above to make this print 'true'
  */
}
