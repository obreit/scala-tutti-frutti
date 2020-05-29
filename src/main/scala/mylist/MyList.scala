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
}
