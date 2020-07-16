package mylist

object ForComprehension extends App {

  val eleanorRigby = MyList(
    "Ah, look at all the lonely people",
    "Ah, look at all the lonely people",
    "Eleanor Rigby",
    "Picks up the rice in the church where a wedding has been",
    "Lives in a dream",
    "Waits at the window",
    "Wearing the face that she keeps in a jar by the door",
    "Who is it for?",
    "All the lonely people",
    "Where do they all come from?",
    "All the lonely people",
    "Where do they all belong?"
  )

  def tokenize(sentence: String): MyList[String] =
    MyList(sentence.split("\\s+"):_*)

  /*
     The for comprehension in Scala is just syntactic sugar for a combination of
     mainly flatMap and map calls (but also withFilter and foreach -- you can think of as withFilter the same as filter).
     Any class that defines a flatMap and map function can be used in for comprehensions!
     If the class additionally defines withFilter and foreach functions, you can have some more features in the
     for comprehension.
   */
  def createBagOfWords(sentences: MyList[String]): MyList[String] = {
    for {
      sentence <- sentences
      word <- tokenize(sentence)
    } yield word // 'yield' translates to a 'map' call, see below to see what happens if you leave out 'yield'

    // sentences.flatMap(sentence => tokenize(sentence).map(word => word)) <-- this expression is exactly the same as
    // the for-comprehension above (in fact, the compiler rewrites the for-comprehension to the flatMap/map calls).
  }

  /*
    This shows what happens if you leave out the 'yield' keyword. The last part of the for comprehension is then
    equivalent to a 'foreach' call, not a 'map' call
   */
  def printBagOfWords(sentences: MyList[String]): Unit = {
    for {
      sentence <- sentences
      word <- tokenize(sentence)
    } println(word) // no 'yield' translates to a 'foreach' call

    // == sentences.flatMap(sentence => tokenize(sentence).foreach(word => println(word)))
  }

  // see the different output
  println(createBagOfWords(eleanorRigby))
  printBagOfWords(eleanorRigby)


  val aList = MyList(1,2,3)
  val bList = MyList(1,2,3)
  val cList = MyList(1,2,3)
  val dList = MyList(1,2,3)

  val quadruples /*: MyList[(Int, Int, Int, Int)] */ =
    for {
      a <- aList
      b <- bList
      c <- cList
      d <- dList
    } yield (a,b,c,d)
  /*
     == aList.flatMap(a => bList.flatMap(b => cList.flatMap(c => dList.map(d => (a, b, c, d))))
     it becomes clear that the for comprehension improves readability a lot if you have to call flatMap many times.
   */

  // example of using 'if' inside the for comprehension (this is enabled by defining withFilter on the class; you can
  // try to comment out withFilter from the class and see check the error message when compiling
  val onlySameElements =
    for {
      a <- aList
      b <- bList
      c <- cList
      d <- dList
      if a == b && b == c && c == d
    } yield (a, b, c, d)

  println(onlySameElements)

  // if conditions can be placed on any line (as long as all the necessary variables have already been defined before)
  val onlySameElements2 =
    for {
      a <- aList
      b <- bList
      if a == b
      c <- cList
      if b == c
      d <- dList
      if c == d
    } yield (a, b, c, d)

  println(onlySameElements2)
  println(onlySameElements == onlySameElements2)
}
