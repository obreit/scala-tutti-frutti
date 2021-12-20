
object Main extends App {


  // importing everything from one object
  import sections.p1_Val._ // <- _ = wildcard import

  // importing step by step
  import sections._
  import p2_Expressions._

  // importing selected stuff from a package
  import sections.p3_Functions.{add, noParams, noParamsDef}

  // imports can be used to rename stuff for the current file (e.g. to handle name clashes)
  import sections.p4_Class.{ FirstClass => User }
  val user = new User(1, "text", 1.0)
}
