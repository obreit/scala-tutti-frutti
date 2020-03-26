package sections

import sections.p4_Class.firstClass

import scala.util.Random

// Case Class
object p5_CaseClass {

  case class FirstCaseClass(accessible: Int, private val inaccessible: String) {
    val x = Random.nextInt()
  }

  val firstCaseClass = FirstCaseClass(42, "hello from the other side")
  println(firstCaseClass.accessible)
  // println(firstCaseClass.inaccessible) --> cannot be accessed error

  val secondCaseClass = new FirstCaseClass(42, "hello from the other side")
  println(firstCaseClass == secondCaseClass) // --> true, comparing values!
  println(firstCaseClass == secondCaseClass.copy(inaccessible = "new text")) // -> false

  println(firstClass)
  println(firstCaseClass)
}
