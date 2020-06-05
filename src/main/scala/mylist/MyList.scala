package mylist

import scala.annotation.tailrec

// +T wtf --> https://docs.scala-lang.org/tour/variances.html explanation of variance
// The coursera scala course has a good explanation and background on variance
// https://www.coursera.org/learn/progfun1?specialization=scala#syllabus
// 'Lecture 4.4 - Variance (Optional)' and maybe also in 'Lecture 4.7 - Lists'
sealed trait MyList[+T]
case object Empty extends MyList[Nothing]
case class Cons[T](head: T, tail: MyList[T]) extends MyList[T]

object MyList {
  def empty[T]: MyList[T] = Empty

  def apply[T](ts: T*): MyList[T] =
    if (ts.isEmpty) Empty else Cons(ts.head, apply(ts.tail: _*))

  def tail[T](ls: MyList[T]): MyList[T] = ls match {
    case Empty => throw new RuntimeException("tail on empty list")
    case Cons(_, tail) => tail
  }

  def updateHead[T](ls: MyList[T], elem: T): MyList[T] = ls match {
    case Empty => throw new RuntimeException("trying to update head on empty list")
    case Cons(_, tail) => Cons(elem, tail)
  }

  def prepend[T](ls: MyList[T], elem: T): MyList[T] = Cons(elem, ls) // efficient
  def append[T](ls: MyList[T], elem: T): MyList[T] = ls match {
    case Empty => Cons(elem, Empty)
    case Cons(head, tail) => Cons(head, append(tail, elem)) // not so efficient
  }

  // drop the first n elements from the list
  @tailrec
  def drop[T](ls: MyList[T], n: Int): MyList[T] = ls match {
    case Cons(_, tail) if n > 0 => drop(tail, n - 1)
    case _ => ls
  }

  // take the first n elements from the list
  def take[T](ls: MyList[T], n: Int): MyList[T] = ls match {
    case Cons(head, tail) if n > 0 => Cons(head, take(tail, n - 1))
    case _ => Empty
  }

  // get all elements except the last element
  def init[T](ls: MyList[T]): MyList[T] = ls match {
    case Empty => throw new RuntimeException("init on empty list")
    case Cons(_, Empty) => Empty
    case Cons(head, tail) => Cons(head, init(tail))
  }

  def length[T](ls: MyList[T]): Int = ls match {
    case Empty => 0
    case Cons(_, tail) => 1 + length(tail)
  }

  // 2 parameter groups for better type inference
  def takeWhile[T](list: MyList[T])(p: T => Boolean): MyList[T] = list match {
    case Cons(head,tail) if p(head) => Cons(head, takeWhile(tail)(p))
    case _ => Empty
  }
  def takeWhileTailRec[T](ls: MyList[T])(p: T => Boolean): MyList[T] = {
    @tailrec
    def run(remaining: MyList[T], takenElements: MyList[T]): MyList[T] = remaining match {
      case Cons(next, rest) if p(next) => run(rest, append(takenElements, next))
      case _ => takenElements
    }
    run(ls, Empty)
  }

  def concat[T](ls1: MyList[T], ls2: MyList[T]): MyList[T] = ls1 match {
    case Empty => ls2
    case Cons(head, tail) => Cons(head, concat(tail, ls2))
  }






  def sum(list: MyList[Int]): Int = list match {
    case Empty => 0
    case Cons(head, tail) => head + sum(tail)
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
      case Cons(head, Empty) => sum + head // optimization (this case can be left out without changing the correctness of the function)
      case Cons(head, tail) =>
        // no recursive calls are building up here that need to be evaluated before 'loop' can be called again
        loop(tail, sum + head)
    }

    loop(ls, 0)
  }

  def product(list: MyList[Double]): Double = list match {
    case Empty => 1
    case Cons(0, _) => 0 // optimization
    case Cons(head, tail) => head * product(tail)
  }

  def productTailRec(ls: MyList[Double]): Double = {
    @tailrec
    def loop(remaining: MyList[Double], productResult: Double): Double = remaining match {
      case Empty => productResult
      case Cons(0, _) => 0 // optimization
      case Cons(head, tail) => loop(tail, head * productResult)
    }

    loop(ls, 1)
  }
}
