package misc

import scala.annotation.tailrec

object OfProsAndCons {

  // +T wtf --> https://docs.scala-lang.org/tour/variances.html explanation of variance
  // The coursera scala course has a good explanation and background on variance
  // https://www.coursera.org/learn/progfun1?specialization=scala#syllabus
  // 'Lecture 4.4 - Variance (Optional)' and maybe also in 'Lecture 4.7 - Lists'
  sealed trait MyList[+T]
  case object Empty extends MyList[Nothing]
  case class Cons[T](head: T, tail: MyList[T]) extends MyList[T]

  object MyList {
    def empty[T]: MyList[T] = Empty

    // summing bird

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

    println(sum(Empty))
    println(sum(MyList.empty[Int]))
    println(sum(Cons(1, Empty)))
    println(sum(Cons(1, Cons(2, Cons(3, Empty)))))

    println(sumTailRec(Empty))
    println(sumTailRec(MyList.empty[Int]))
    println(sumTailRec(Cons(1, Empty)))
    println(sumTailRec(Cons(1, Cons(2, Cons(3, Empty)))))

    // multiplication

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

    println(product(Empty))
    println(product(MyList.empty[Double]))
    println(product(Cons(3, Empty)))
    println(product(Cons(1, Cons(2, Cons(4, Empty)))))

    println(productTailRec(Empty))
    println(productTailRec(MyList.empty[Double]))
    println(productTailRec(Cons(3, Empty)))
    println(productTailRec(Cons(1, Cons(2, Cons(4, Empty)))))
  }
}
