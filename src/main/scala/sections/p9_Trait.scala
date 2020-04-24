package sections

// Trait

object p9_Trait {

  // Traits are like interfaces in java but better
  // You can also use 'abstract class' in Scala which is very similar, but traits are more common and also recommended to be used

  trait MyTrait {
    def someMember: Double

    def binaryOp(x: Int, y: Int): Int

    // Note that I use the abstract method here!
    // This is useful if you implement some larger "algorithm" which should always behave the same but some parts of it
    // are unique to its subclasses (e.g. a kafka hash partitioner trait could implement everything except the hash function)
    // This style is called templating
    def binaryOpToString(x: Int, y: Int): String = binaryOp(x, y).toString
  }
  class Adder extends MyTrait {
    override def someMember: Double = 1.0
    override def binaryOp(x: Int, y: Int): Int = x + y
  }
  class Subtractor extends MyTrait {
    // note that you can make a 'val' out of a 'def' in a subclass of a trait
    // so you can be more flexible on top of the hierarchy (maybe a subclass implementation of the member needs to behave like a method,
    // whereas another should behave like a value)
    override val someMember: Double = -1.0
    override def binaryOp(x: Int, y: Int): Int = x - y
  }
  val adder = new Adder
  val subtractor = new Subtractor

  // this is an anonymous instance of the member trait
  val multiplier = new MyTrait {
    override def someMember: Double = 1.0
    override def binaryOp(x: Int, y: Int): Int = x * y
  }

  println(adder.binaryOpToString(10, 5))
  println(subtractor.binaryOpToString(10, 5))
  println(multiplier.binaryOpToString(10, 5))

  // Traits are very useful to implement so called ADTs (abstract data type)
  // Let's create an "AlertState" ADT

  // Note the 'sealed' keyword!
  // This means that subclasses of this trait are only allowed to be defined in this file! (see how this comes in handy below)
  sealed trait AlertState {
    def rawValue: String
  }
  case object AlertClear extends AlertState { // case objects are like case classes in the sense that they can be used in pattern matches
    override val rawValue: String = "clear"
  }
  case object AlertSet extends AlertState {
    override val rawValue: String = "set"
  }

  // now let's say we have an alert case class
  case class Alert(name: String, state: AlertState)
  val alert = Alert("name", AlertSet)
  alert match {
    case Alert(_, AlertSet) => println("Alert is set!")
    case Alert(_, AlertClear) => println("Alert is cleared!")
      // we don't need a catch all here!!!
      // our trait is 'sealed' so the compiler knows all possible subclasses, so this pattern match is complete
      // remove the sealed keyword and compile again, then the compiler should show a warning
  }

  // now for some more mind bending, let's define a companion to our trait
  object AlertState {
    // What we're doing here is create an alert state from a raw string value
    // See how that works on pattern matching
    // This kind of parsing is useful if you want to plug it into json parsers
    def unapply(s: String): Option[AlertState] = s match {
      case AlertSet.rawValue => Some(AlertSet)
      case AlertClear.rawValue => Some(AlertClear)
      case _ => None
    }
  }

  def parseAlertState: AlertState = "set" match {
    case AlertState(state) => state // wow
    case otherString => throw new Exception(s"Not a valid alert state $otherString")
  }

  /*
  Exercise
    complete the code, also with more cases if you like

    trait Pet
    object Pet {
      def unapply(s: String): Option[Pet] = ???
    }

    case class Owner(name: String, rawPet: String)
    val tintin = Owner("Tintin", "dog")
    val hermione = Owner("Hermione", "cat")
      ...

    tintin match {
      case ??? => println(s"$name is a dog owner")
      case ??? => println(s"$name is a cat owner")
        ...
    }

   */

  /*
    Note on Option
    Option is Scala's way of removing null values (Scala still allows null, because it runs on the jvm).
    It is implemented as an ADT in the standard lib,
    with subclasses 'Some' and 'None'
    Some represents an existing value
    None a nonexisting (e.g. null)
   */
}
