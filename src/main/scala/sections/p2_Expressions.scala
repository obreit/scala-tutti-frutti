package sections

// Expressions
object p2_Expressions {

  val x = 10
  val ifelse: String = if(x >= 0) "greater eq 0" else "smaller 0"
  val block: Int = {
    val x = 42 // <- different scope for x!
    x + 50
  }
  println {
    val y = 1
    y + 10
  }
}
