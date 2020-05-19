package sections

import scala.Predef

object p11_Pitfall {

  Array(1) == Array(1) // always false -- the only scala collection that behaves this way (intellij gives hint), fixed in 2.13
  Array(1).sameElements(Array(1))

  // inferred type is 'Some' != 'Option' - always add types to your methods!
  def asOption[A](a: A) = Some(a)

  // never use 'Enumeration'! start using 'enum' in scala 3 --> before sealed hierarchies instead!
  object Status extends Enumeration {
    val Ok, NotOk = Value
  }
  def printStatus(s: Status.Value): Unit = s match {
    case Status.Ok => println("OK") // compiles without warning! non exhaustive pattern match
  }

  // Type inference can be confusing sometimes, since scala looks for the closest common supertype!
  sealed trait Homo
  object Homo {
    case object Sapiens extends Homo
    case object Neanderthalensis extends Homo
  }

  // confusing type inference! 'case' types extend 'Product' + 'Serializable' by default
  val humans /* inferred type == Homo with Product with Serializable */ = List(Homo.Sapiens, Homo.Neanderthalensis)


  // mark case classes as final, or by convention never extend case classes!
  case class Foo(i: Int)
  class Bar(i: Int, s: String) extends Foo(i)
  new Bar(1, "asd") == new Bar(1, "notthesame") // this will yield true!!! because Bar extends the equals method from Foo

  // never use 'return'

  // never make fun of javascript
  // def toSet[B >: A], .apply(b: B): Boolean
  // List("1").toSet() == List("1").toSet[inferred Any!!!].apply(() <-- unit parameter is inferred)
  // List("1").toSet() == List("1").toSet[Any].apply(())
  // luckily the compiler at least warns now
  /*
      Adaptation of argument list by inserting () is deprecated: this is unlikely to be what you want.
            signature: GenSetLike.apply(elem: A): Boolean
      given arguments: <none>
      after adaptation: GenSetLike((): Unit)
   */
  val notWhatAnyoneWouldExpect = List("1", "2").toSet() + "3"
  println(notWhatAnyoneWouldExpect == "false3")

  // prints '()abc'; {} is block of code returning unit and the nice 'any2stringadd' implicit from 'Predef' adds the empty string to unit
  println({} + "abc")

  // true! another fun implicit conversion
  println(Option(1).zip(Option(1)) == List((1,1)))
}
