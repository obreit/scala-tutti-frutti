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

  /*
    Scala also allows you to constrain types in certain situations:
    An 'upper type bound' is defined as 'A <: B' which means A is a subtype of B
    A  'lower type bound' is defined as 'A >: B' which means A is a supertype of B
   */
  trait Wizard
  case class Harry() extends Wizard {
    override def toString: String = "Expecto Patronum"
  }
  case class Hermione() extends Wizard {
    override def toString: String = "Wingardium Leviosa" // (not LeviosAAA)
  }
  case class Dudley() {
    override def toString: String = "36?!!! But last year I had 37!"
  }

  def castCharm[W](w: W): Unit = println(w)

  castCharm(Harry())
  castCharm(Hermione())
  castCharm(Dudley())

  trait Magic
  trait Castle extends Magic
  trait Wiz extends Magic

  trait Hogwarts extends Magic with Castle

  class Gryffindor extends Hogwarts
  class Slytherin extends Hogwarts

  class Ron extends Wiz

  class School[S >: Hogwarts <: Magic]

  new School[Hogwarts]
}
