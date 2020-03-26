package sections

// Pattern Matching

object p8_PatternMatching {

  val int = 3
  int match {
    case 0 => println("zero")
    case 1 => println("one")
    case _ => println("other")
  }

  val any: Any = 1
  any match {
    case i: Int => println(s"Int: $i!")
    case _: String => println("String!")
    case x => println(s"Other: $x")
  }

  case class AnyCaseClass(a: Any, suffix: String)
  val anyCaseClass = AnyCaseClass(1, "It's an Int!")
  anyCaseClass match {
    case AnyCaseClass(i: Int, suffix) => println(s"$i $suffix")
    case AnyCaseClass(s: String, suffix) => println(s"$s $suffix")
    case acc @ AnyCaseClass(_: Boolean, suffix) => println(s"${acc.a} $suffix")
    case AnyCaseClass(a, suffix) => println(s"$a $suffix") // --> compiler shows warning if we omit this line
  }

  case class Wrapper(i: Int, any: AnyCaseClass)
  val wrapper = Wrapper(42, AnyCaseClass(true, "It's a boolean!"))
  wrapper match {
    case Wrapper(i, any) if any.a.isInstanceOf[String] => println(s"$i ---- ${any.a} ${any.suffix}")
    case Wrapper(i, any) if i == 0 => println(s"$i ---- ${any.a} ${any.suffix}")
    case Wrapper(i, AnyCaseClass(a: Boolean, suffix)) => println(s"$i ---- $a $suffix")
    // --> doesn't show compile warning
  }

  class ReadyForPatternMatch(val i: Int, val s: String)
  object ReadyForPatternMatch {
    def unapply(r: ReadyForPatternMatch): Option[(Int, String)] = Some((r.i, r.s))
  }
  val readyForPatternMatch = new ReadyForPatternMatch(42, "hi")
  readyForPatternMatch match {
    case ReadyForPatternMatch(i, s) => println(s"$i $s")
  }
}
