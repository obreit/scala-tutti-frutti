package mylist

object MyListMain extends App {

  def printOp[T, U](opName: String, op: T => U)(t: T): Unit =
    println(s"$t $opName =>>> ${op(t)}")

  println("*" * 100)
  println("TAIL")
  val printTail = printOp("tail", (_: MyList[Int]).getTail) _
  printTail(MyList(1,2,3))
  printTail(MyList(1))
  //printTail(MyList()) --> throws an error

  println("*" * 100)
  println("PREPEND")
  val printPrepend = (i: Int) => printOp(s"prepend $i", i +: (_: MyList[Int])) _
  printPrepend(0)(MyList(1,2,3))
  printPrepend(1)(MyList(2))
  printPrepend(0)(MyList())

  println("*" * 100)
  println("APPEND")
  val printAppend = (i: Int) => printOp(s"append $i", (_: MyList[Int]) :+ i) _
  printAppend(4)(MyList(1,2,3))
  printAppend(2)(MyList(1))
  printAppend(1)(MyList())

  println("*" * 100)
  println("DROP N")
  val printDropN = (n: Int) => printOp(s"drop $n", (_: MyList[Int]).drop(n)) _
  printDropN(2)(MyList(1,2,3))
  printDropN(0)(MyList(1,2,3))
  printDropN(5)(MyList(1,2,3))
  printDropN(3)(Empty)
  printDropN(-5)(MyList(1,2,3))

  println("*" * 100)
  println("TAKE N")
  val printTakeN = (n: Int) => printOp(s"take $n", (_: MyList[Int]).take(n)) _
  printTakeN(2)(MyList(1,2,3))
  printTakeN(2)(MyList(1))
  printTakeN(2)(Empty)
  printTakeN(-2)(MyList(1,2,3))

  println("*" * 100)
  println("INIT")
  val printInit = printOp("init", (_: MyList[Int]).init) _
  printInit(MyList(1,2,3))
  printInit(MyList(1))
  //printInit(Empty) --> throws an error

  println("*" * 100)
  println("LENGTH")
  val printLen = printOp("length", (_: MyList[String]).length) _
  printLen(MyList("a", "b", "c"))
  printLen(MyList("a"))
  printLen(MyList())

  println("*" * 100)
  println("TAKE WHILE")
  val printTakeWhile = printOp("takeWhile", (_: MyList[String]).takeWhile(_ != "")) _
  printTakeWhile(MyList("hello", "how", "are", "", "good", "sir"))
  printTakeWhile(MyList("hello", "how", "are", "you", "good", "sir"))
  printTakeWhile(MyList())

  println("*" * 100)
  println("CONCAT")
  val printConcat = printOp[(MyList[Int], MyList[Int]), MyList[Int]]("concat", ((_: MyList[Int]) ++ (_: MyList[Int])).tupled) _
  printConcat(MyList(1,2,3), MyList(4,5,6))
  printConcat(MyList(1,2,3), MyList())
  printConcat(MyList(), MyList(4,5,6))
}
