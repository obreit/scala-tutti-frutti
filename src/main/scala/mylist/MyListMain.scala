package mylist

object MyListMain extends App {

  def printOp[T, U](opName: String, op: T => U)(t: T): Unit =
    println(s"$t $opName =>>> ${op(t)}")

  println("*" * 100)
  println("TAIL")
  val printTail = printOp("tail", MyList.tail[Int]) _
  printTail(MyList(1,2,3))
  printTail(MyList(1))
  //printTail(MyList()) --> throws an error

  println("*" * 100)
  println("SET HEAD")
  val printSetHead = (i: Int) => printOp(s"updateHead with $i", MyList.updateHead[Int](_, i)) _
  printSetHead(2)(MyList(1,2,3))
  printSetHead(42)(MyList(1))
  //printSetHead(MyList()) --> throws an error

  println("*" * 100)
  println("PREPEND")
  val printPrepend = (i: Int) => printOp(s"prepend $i", MyList.prepend[Int](_, i)) _
  printPrepend(0)(MyList(1,2,3))
  printPrepend(1)(MyList(2))
  printPrepend(0)(MyList())

  println("*" * 100)
  println("APPEND")
  val printAppend = (i: Int) => printOp(s"append $i", MyList.append[Int](_, i)) _
  printAppend(4)(MyList(1,2,3))
  printAppend(2)(MyList(1))
  printAppend(1)(MyList())

  println("*" * 100)
  println("DROP N")
  val printDropN = (n: Int) => printOp(s"drop $n", MyList.drop[Int](_, n)) _
  printDropN(2)(MyList(1,2,3))
  printDropN(0)(MyList(1,2,3))
  printDropN(5)(MyList(1,2,3))
  printDropN(3)(Empty)
  printDropN(-5)(MyList(1,2,3))

  println("*" * 100)
  println("TAKE N")
  val printTakeN = (n: Int) => printOp(s"take $n", MyList.take[Int](_, n)) _
  printTakeN(2)(MyList(1,2,3))
  printTakeN(2)(MyList(1))
  printTakeN(2)(Empty)
  printTakeN(-2)(MyList(1,2,3))

  println("*" * 100)
  println("INIT")
  val printInit = printOp("init", MyList.init[Int]) _
  printInit(MyList(1,2,3))
  printInit(MyList(1))
  //printInit(Empty) --> throws an error
}
