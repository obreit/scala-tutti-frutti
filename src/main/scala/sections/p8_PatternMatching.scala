package sections

// Pattern Matching

object p8_PatternMatching {

  // I'd say this is the backbone of fundamentally sound scala code.
  // In addition to being a 'switch on legal steroids' it hase close relationships to
  // case classes, apply, unapply, ADTs, partial functions

  // very basic, like a switch
  val int = 3
  int match {
    case 0 => println("zero")
    case 1 => println("one")
    case _ => println("other") // This is the catch all case (like default in switch), you can use underscore but it is not required.
                               // It just indicates that you are not interested in the variable (you can also do case x => println(s"other: $x"))
  }

  // you can pattern match on types
  val any: Any = 1
  any match {
    case i: Int => println(s"Int: $i!")
    case _: String => println("String!")
    case x => println(s"Other: $x")
  }

  // Pattern matching is super useful when you want to match + decompose case classes
  case class AnyCaseClass(a: Any, suffix: String)
  val anyCaseClass = AnyCaseClass(1, "It's an Int!")
  anyCaseClass match {
    case AnyCaseClass(any, "matches only on this exact string") => println(s"$any")
    case AnyCaseClass(i: Int, suffix) => println(s"$i $suffix")
    case AnyCaseClass(s: String, suffix) => println(s"$s $suffix")
    // 'acc' is a binding ('@' is the special binding operator) that you can use as a regular variable (e.g. acc.suffix + "more suffix")
    case acc @ AnyCaseClass(_: Boolean, suffix) => println(s"${acc.a} $suffix")
    case AnyCaseClass(a, suffix) => println(s"$a $suffix") // --> try to omit this line, the compiler shows a warning because the pattern match isn't complete
  }

  // You can even decompose nested stuff
  case class Wrapper(i: Int, any: AnyCaseClass)
  val wrapper = Wrapper(42, AnyCaseClass(true, "It's a boolean!"))
  wrapper match {
    case Wrapper(i, any) if i == 0 /* this is called a guard and the case only matches if the guard evaluates to true */  =>
      // you can have multiple lines without {} in a case
      val x = i + 3
      println(s"$x ---- ${any.a} ${any.suffix}")
    case Wrapper(i, any) if any.a.isInstanceOf[String] => println(s"$i ---- ${any.a} ${any.suffix}")
    case Wrapper(i, acc @ AnyCaseClass(a: Boolean, suffix)) => println(s"$i ---- ${acc.a} $suffix")
    // --> doesn't show compile warning, probably the compiler can't go that deep (this is different for ADTs, see p9_Trait)
  }

  // The 'unapply' method is the enabler for pattern matching
  // Go over 'case ReadyForPatternMatch' with your cursor and click command+B (it will show you the 'unapply' which is defined above in the companion...mind = blown)
  // That's why case classes can be used in pattern matching out of the box -- Their companion already has the 'unapply'
  class ReadyForPatternMatch(val i: Int, val s: String)
  object ReadyForPatternMatch {
    // see a note on 'Option' in p9_Trait
    def unapply(r: ReadyForPatternMatch): Option[(Int, String)] = Some((r.i, r.s))
  }
  val readyForPatternMatch = new ReadyForPatternMatch(42, "hi")
  readyForPatternMatch match {
    case ReadyForPatternMatch(i, s) => println(s"$i $s")
  }

  // you can also do this crazy thing
  val r @ ReadyForPatternMatch(i, s) = readyForPatternMatch
  println(s"${i + 1}, $s")
  println(r)

  /*
  // Exercise
  case class MyCaseClass(i: Int, s: String)
  val myCaseClass = ??? // create an instance and update the pattern match so we print the statements below
  myCaseClass match {
    case MyCaseClass(i, s) => println(s"$s $i")
    case _ => println("other")
  }
  // 'int is: 42'
  // 'int is greater 10: 11'
  // 'string is empty and int is 42'

  case class Wrapped(i: Int, cc: MyCaseClass)
  Wrapped(1, MyCaseClass(2, "hello")) match {
    case Wrapped(x, MyCaseClass(y, s)) => println(s"x=$x, y=$y, s=$s, w=$w, cc=$cc") // make this compile
  }

  case class AnywayCaseClass(any: Any)
  AnywayCaseClass(1) match {
    case AnywayCaseClass(s) => println("It's a String!")
    case AnywayCaseClass(i) => println("It's an Int!") // change the pattern match so it prints this line
  }

  class UseInPatternMatch(i: Int, s: String)
  val useInPatternMatch = new UseInPatternMatch(1, "hi")
  useInPatternMatch match {
    case UseInPatternMatch(i, s) => println(s"$i $s") // make this compile and print '1 hi'
  }
   */


  /*
    A note on 'apply' and 'unapply'.
    Apply is used to compose sth bigger out of smaller parts (e.g. an instance from it's arguments)
    Unapply is used to decompose sth into its parts
   */
}
