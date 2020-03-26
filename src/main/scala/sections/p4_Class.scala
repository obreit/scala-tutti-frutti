package sections

// Class
object p4_Class {

  class FirstClass(constructorParam: Int, val accessible: String, private val inaccessible: Double) {
    // def method(i: Int): String <-- doesn't compile if method is not implemented

    val publicMember: Char = 'c'
    private val privateMember: String = this.constructorParam.toString

    def getPrivateMember(): String = privateMember
    def printPrivateMember(): Unit = println(privateMember)
  }
  val firstClass = new FirstClass(1, "hello", 42.1)
  // println(firstClass.constructorParam) --> not a member error
  // println(firstClass.inaccessible) --> cannot be accessed error

  println(firstClass.accessible)
  println(firstClass.getPrivateMember())
  firstClass.printPrivateMember()

  val secondClass = new FirstClass(1, "hello", 42.1)
  println(firstClass == secondClass) // --> false, because comparing reference
}
