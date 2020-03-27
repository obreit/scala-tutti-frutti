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
  var badStuff = 42 // never do this (the last var we have is in a certain service that shall not be named...you know how that turned out)
  badStuff = 32 // :scream:
}
