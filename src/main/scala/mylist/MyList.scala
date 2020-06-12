package mylist

import scala.annotation.tailrec

// +T wtf --> https://docs.scala-lang.org/tour/variances.html explanation of variance
// The coursera scala course has a good explanation and background on variance
// https://www.coursera.org/learn/progfun1?specialization=scala#syllabus
// 'Lecture 4.4 - Variance (Optional)' and maybe also in 'Lecture 4.7 - Lists'
sealed trait MyList[+T] {

  /*
    2 things happening here
    1:
      We define the 'prepend' operation as '+:'. The scala compiler has a rule that
      methods ending with ':' associate to the right. This allows us to use the prepend operation like this

      1 +: MyList(2,3) == MyList(2,3).+:(1) == MyList(1,2,3)

    2:
      Ideally the signature should look like 'def +:(elem: T): MyList[T]', so why do we make it complicated?
      See what happens if you use the "normal" signature 'def +:(elem: T): MyList[T]'.

      The 'U >: T' trick is due to variance. I don't have a very intuitive way to explain this, so I can only refer
      to the video linked above which explains it very nicely.
      It boils down to the fact that function parameters are contravariant (the video explains it!).
      E.g. the function taking 3 parameters is defined as 'Function3[-T1, -T2, -T3, +R]'.
      The function taking 1 parameter is defined as 'Function1[-T, +R]', and equivalently for all other functions.
      --> This means
      function input types are CONTRAvariant
      function return types are COvariant

      We define our data type as MyList[+T], so with a COvariant type T. So we can't use it as an input type
      in 'def +:(elem: T): MyList[T]', because we established that input types are CONTRAvariant. To overcome this,
      we create this CONTRAvariant fix 'U >: T'. Since 'T >: T', the usage of such a function stays the same luckily.

      (remember contravariant means:
       if you have types A,B and A >: B (A is a supertype of B) then for a container
       defined as Container[-T] it holds that Container[A] >: Container[B] (i.e. container of A is a supertype of a container of B))
   */
  def +:[U >: T](elem: U): MyList[U] = mylist.+:(elem, this)

  /*
    Append operation
    Notice this method doesn't end with ':' so it's NOT right associative --> 1 :+ MyList(2,3) doesn't compile because
    ':+' is not defined on Int.
    As a general memory trick (mnemonic, or "Eselsbrücke" in German) use
    COlon is pointing to the COllection
    MyList(1,2,3) :+ 4 == MyList(1,2,3,4)
    0 +: MyList(1,2,3) == MyList(0,1,2,3)
   */
  def :+[U >: T](elem: U): MyList[U] = this match {
    case Empty => elem +: Empty
    case head +: tail => head +: (tail :+ elem) // not so efficient
  }

  def tail: MyList[T] = this match {
    case Empty => throw new RuntimeException("tail on empty list")
    case _ +: tail => tail
  }

  def updateHead[U >: T](elem: U): MyList[U] = this match {
    case Empty => throw new RuntimeException("trying to update head on empty list")
    case _ +: tail => elem +: tail
  }

  // drop the first n elements from the list
  def drop(n: Int): MyList[T] = this match {
    case _ +: tail if n > 0 => tail.drop(n - 1)
    case _ => this
  }

  // take the first n elements from the list
  def take(n: Int): MyList[T] = this match {
    case head +: tail if n > 0 => head +: tail.take(n - 1)
    case _ => Empty
  }

  // get all elements except the last element
  def init: MyList[T] = this match {
    case Empty => throw new RuntimeException("init on empty list")
    case _ +: Empty => Empty
    case head +: tail => head +: tail.init
  }

  def length: Int = this match {
    case Empty => 0
    case _ +: tail => 1 + tail.length
  }

  // 2 parameter groups for better type inference
  def takeWhile(p: T => Boolean): MyList[T] = this match {
    case head +: tail if p(head) => head +: tail.takeWhile(p)
    case _ => Empty
  }

  /*
    Concat operation
    Often collection operators that take another collection as the input will
    look similar to related operators that take only one element but the operator is doubled.
    E.g.
    append is MyList(1,2,3) :+ 4
    concat is MyList(1,2,3) ++ MyList(4,5,6)
   */
  def ++[U >: T](ls2: MyList[U]): MyList[U] = this match {
    case Empty => ls2
    case head +: tail => head +: (tail ++ ls2)
  }

  def reverse: MyList[T] = {
    def run(remaining: MyList[T], reversedList: MyList[T]): MyList[T] = remaining match {
      case Empty => reversedList
      case next +: rest => run(rest, next +: reversedList)
    }
    run(this, Empty)
    /*
      // recursive solution --> notice that complexity moves towards O(n^2)
      this match {
        case Empty => Empty
        case head +: tail => tail.reverse :+ head
      }
     */
  }

  override def toString: String = {
    def stringifyElements(remaining: MyList[T], strElements: String): String = remaining match {
      case Empty => strElements
      case next +: rest => stringifyElements(rest, s"$strElements,$next")
    }
    val strElements = this match {
      case Empty => ""
      case head +: tail => stringifyElements(tail, head.toString)
    }

    s"MyList($strElements)"
    /*
     recursive version
     def toString: String = {
        def run(remaining: MyList[T]): String = remaining match {
        case Empty => ""
        case head +: Empty => head.toString
        case head +: tail => s"$head,${run(tail)}"
      }
      val stringifiedElements = run(this)
      s"MyList($stringifiedElements)"
     }
    */
  }
}
case object Empty extends MyList[Nothing]
case class +:[T](head: T, override val tail: MyList[T]) extends MyList[T]

object MyList {
  def empty[T]: MyList[T] = Empty

  def apply[T](ts: T*): MyList[T] =
    if (ts.isEmpty) Empty else +:(ts.head, apply(ts.tail: _*))

  def sum(list: MyList[Int]): Int = list match {
    case Empty => 0
    case head +: tail => head + sum(tail)
  }
  /*
     If we follow the execution of a small example, we see that a tail builds up (1 + 2 + ...) that cannot be evaluated
     until we reach the empty list which allows us to collapse the whole expression (1 + 2 + 3 + 0 -> 6) to get the
     final result. For large lists the recursive implementation will lead to stackoverflow issues.

     sum(MyList(1,2,3))
     -->
     Cons(1,Cons(2,Cons(3,Empty)))) match { ... }
     -->
     1 + sum(Cons(2,Cons(3,Empty)))
     -->
     1 + Cons(2,Cons(3,Empty)) match { ... }
     -->
     1 + 2 + sum(Cons(3,Empty))
     --> -->
     1 + 2 + 3 + sum(Empty)
     -->
     1 + 2 + 3 + Empty match { ... }
     -->
     1 + 2 + 3 + 0
     -->
     6
  */

  def sumTailRec(ls: MyList[Int]): Int = {
    @tailrec // <- telling the compiler it can optimize this function to a more efficient 'while' loop
    def loop(remaining: MyList[Int], sum: Int): Int = remaining match {
      case Empty => sum
      case head +: Empty => sum + head // optimization (this case can be left out without changing the correctness of the function)
      case head +: tail =>
        // no recursive calls are building up here that need to be evaluated before 'loop' can be called again
        loop(tail, sum + head)
    }

    loop(ls, 0)
  }

  def product(list: MyList[Double]): Double = list match {
    case Empty => 1
    case 0 +: _ => 0 // optimization
    case head +: tail => head * product(tail)
  }

  def productTailRec(ls: MyList[Double]): Double = {
    @tailrec
    def loop(remaining: MyList[Double], productResult: Double): Double = remaining match {
      case Empty => productResult
      case 0 +: _ => 0 // optimization
      case head +: tail => loop(tail, head * productResult)
    }

    loop(ls, 1)
  }
}
