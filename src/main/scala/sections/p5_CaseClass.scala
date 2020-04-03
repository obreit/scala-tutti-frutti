package sections

import sections.p4_Class.firstClass

import scala.util.Random

// Case Class
object p5_CaseClass {

  // This is the good stuff. We use case classes extensively for all the domain modeling
  // Case classes are immutable by default.
  // To update a case class, you can use the caseclass.copy() method.
  // The parameters of a case class are public members by default (contrary to regular class parameters)

  // The scala compiler generates a couple of things automatically for case classes
  // - equals method, so case classes can be compared by value, not by reference
  // - toString method, so printed case classes look meaningful instead of some weird symbol set
  // - hashCode method
  // - A companion object (check p7_Apply) with default 'apply' (p7_Apply) and 'unapply' (p8_PatternMatching) methods
       // Note that you can still define your own companion object + apply/unapply for your case class
       // This won't override the stuff that is created by the compiler!
  case class FirstCaseClass(accessible: Int, private val inaccessible: String) {
    val x = Random.nextInt() // Note, when comparing case classes, this 'x' won't be used in the comparison!
  }

  // Note that case classes are created without the 'new' keyword
  // Check p7_Apply how this is done
  val firstCaseClass = FirstCaseClass(42, "hello from the other side")

  println(firstCaseClass.accessible)
  // println(firstCaseClass.inaccessible) --> cannot be accessed error

  // case classes can also be created with 'new' but this is not necessary
  val secondCaseClass = new FirstCaseClass(42, "hello from the other side")
  println(firstCaseClass == secondCaseClass) // --> true, comparing values!
  println(firstCaseClass == secondCaseClass.copy(inaccessible = "new text")) // -> false

  println(firstClass)
  println(firstCaseClass)

  /*
  // Exercise
  case class MyCaseClass(a: Int, b: Int, c: Int)
  val myCaseClass1 = MyCaseClass(1,2,3)
  val myCaseClass2 = MyCaseClass(1,2,3)
  println(myCaseClass1 == myCaseClass2) // what is printed?

  myCaseClass1.a // change the above code to make this fail compilation
   */
}
