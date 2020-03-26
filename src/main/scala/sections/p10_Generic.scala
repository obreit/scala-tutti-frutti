package sections

// Generic

object p10_Generic {

  // Generic
  class Show[T](t: T) {
    def show(): String = t.toString
  }
  val intShow = new Show[Int](1)
  val strShow = new Show[String]("sad")
}
