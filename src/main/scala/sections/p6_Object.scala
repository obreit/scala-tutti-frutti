package sections

// Object

object p6_Object {

  // Singleton objects like this are similar to static classes in java
  // We mostly use them to define some general function/methods/values and as companions to case classes (see p7_Apply)
  // Don't confuse the 'object' keyword which is specific to scala with instances of classes (which is the more common usage of the word object)
  object MyObject {
    def methodOnObject(i: Int): String = i.toString
  }
  println(MyObject.methodOnObject(1001))
}
