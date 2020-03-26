package sections

import sections.p4_Class.firstClass

import scala.util.Random

object p5_CaseClass {

  // Case Class
  case class FirstCaseClass(accessible: Int, private val inaccessible: String) {
    val x = Random.nextInt()
  }

  val firstCaseClass = FirstCaseClass(42, "hello from the other side")
  println(firstCaseClass.accessible)
  // println(firstCaseClass.inaccessible) --> cannot be accessed error

  val secondCaseClass = new FirstCaseClass(42, "hello from the other side")
  println(firstCaseClass == secondCaseClass) // comparing values!
  println(firstCaseClass == secondCaseClass.copy(inaccessible = "new text"))

  println(firstClass)
  println(firstCaseClass)
}
