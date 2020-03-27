package sections

// Generic

object p10_Generic {

  // Scala implements generics the right way (lot's of stuff can be done with generics -- this just shows some basics)

  class Show[T](t: T) {
    def show(): String = t.toString

    // you can also have generics on methods
    def showBoth[U](u: U): String = s"$t --- $u"
  }
  val intShow = new Show[Int](1)
  val strShow = new Show[String]("sad")
}
