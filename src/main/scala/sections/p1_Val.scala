package sections

// Val + Var
object p1_Val {

  val x = 1
  val y: Int = 1
  val z: Double = 1.23
  val s: String = "asd"
  val c: Char = 'c'
  val b: Byte = 0x1
  val short: Short = 10000
  val bool: Boolean = true

  println(x)
  println(s"x+y = ${x + y}, z = $z")

  // Var
  // x = 2 --> compile error can't reassign val
  var badStuff = 42
  badStuff = 32 // :scream:
}
