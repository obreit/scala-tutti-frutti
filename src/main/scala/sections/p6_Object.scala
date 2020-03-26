package sections

// Object

object p6_Object {

  object MyObject {
    def methodOnObject(i: Int): String = i.toString
  }
  println(MyObject.methodOnObject(1001)) // <- singleton
}
