package sections

// Class
object p4_Class {

  // Note the concise syntax of classes compared to Java
  // Important notes
  // 1. 'constructorParam' is not a member of the class, it is only a parameter to the class constructor.
  //    The class constructor i actually everything that you see below this line until the second '}'.
  //    This code is run when a class is instantiated
  // 2. 'accessible' is a public member of the class
  // 3. 'inaccessible' is a private member of the class
  class FirstClass(constructorParam: Int, val accessible: String, private val inaccessible: Double) {
    // def method(i: Int): String <-- doesn't compile if method is not implemented

    // alternativ class constructor (we use a more scala way of constructor overloading with 'apply')
    def this(i: Int) = this(i, "default", 1.0) // now, you can also create an instance like new FirstClass(1)

    println("constructor") // <- gets printed when you do new FirstClass(...)
    val publicMember: Char = 'c' // alternative of defining a public member
    private val privateMember: String = this.constructorParam.toString // alternative of defining a public member

    def getPrivateMember(): String = privateMember // java style get method
    def printPrivateMember(): Unit = println(privateMember)
  }
  val firstClass = new FirstClass(1, "hello", 42.1)
  // println(firstClass.constructorParam) --> not a member error
  // println(firstClass.inaccessible) --> cannot be accessed error

  println(firstClass.accessible)
  println(firstClass.getPrivateMember())
  firstClass.printPrivateMember()

  val secondClass = new FirstClass(1, "hello", 42.1)
  println(firstClass == secondClass) // --> false, because classes are compared by reference

  /*
  // Exercise
  class MyClass(a: Int, val b: Int, private val c: Int) // explain the difference between a,b,c
  val myClass = new MyClass(1,2,3)
  println(myClass.a) // change the code above to make this print "1"

  val myClass2 = new MyClass(4)
  println(s"${myClass2.a} ${myClass2.b} ${myClass2.c}") // change the code above to make this compile and print "4 5 6"

  val sameOld = new MyClass(1,2,3)
  val sameOld2 = new MyClass(1,2,3)
  println(sameOld == sameOld2) // <- what is printed?
   */
}
