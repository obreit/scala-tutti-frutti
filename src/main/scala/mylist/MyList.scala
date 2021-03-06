package mylist

/**
 * Rules for functions on MyList
 * 1. If generics are too alien: Put the function outside of the trait and use concrete types (e.g. Int, String) instead of generics. After you're done with the implementation, replace the concrete types with generic paramerters.
 * 2. Let Intellij fill out the pattern match
 * 3. Handle the trivial Empty case first
 * 4. Recursively call the function on the tail element (Any function/algorithm on a recursive data structure has to be recursive)
 * 5. Apply some logic on the head element and combine it with the result of the recursive call
 */
sealed trait MyList[+T] {

  def zipWith[U, V](ls2: MyList[U])(f: (T, U) => V): MyList[V] = ???
  def zip[U](ls2: MyList[U]): MyList[(T, U)] = ???

  def flatMap[U](f: T => MyList[U]): MyList[U] = this match {
    case Empty => Empty
    case head +: tail => f(head) ++ tail.flatMap(f)
  }

  def map[U](f: T => U): MyList[U] =
    flatMap(x => MyList(f(x)))

  def filter(p: T => Boolean): MyList[T] =
    flatMap(x => if(p(x)) MyList(x) else Empty)

  // this function allows us to use if-guards in a for-comprehension
  def withFilter(p: T => Boolean): MyList[T] = filter(p)

  def foreach(f: T => Unit): Unit = this match {
    case Empty => ()
    case head +: tail =>
      f(head)
      tail.foreach(f)
  }

  def forall(p: T => Boolean): Boolean = this match {
    case Empty => true
    case head +: tail => p(head) && tail.forall(p)
  }

  def exists(p: T => Boolean): Boolean = this match {
    case Empty => false
    case head +: tail => p(head) || tail.exists(p)
  }

  def partition(pred: T => Boolean): (MyList[T], MyList[T]) =
    (filter(pred), filter(x => !pred(x)))

  // prepend an element
  def +:[U >: T](elem: U): MyList[U] = mylist.+:(elem, this)

  // append an element
  def :+[U >: T](elem: U): MyList[U] = this match {
    case Empty => elem +: Empty
    case head +: tail => head +: (tail :+ elem) // not so efficient
  }

  def getTail: MyList[T] = this match {
    case Empty => throw new RuntimeException("tail on empty list")
    case _ +: tail => tail
  }

  // concatenate a list to this list
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

  def takeWhile(p: T => Boolean): MyList[T] = this match {
    case head +: tail if p(head) => head +: tail.takeWhile(p)
    case _ => Empty
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
  }
}
case object Empty extends MyList[Nothing]
case class +:[T](head: T, tail: MyList[T]) extends MyList[T]

object MyList {

  def flatten[T](ls: MyList[MyList[T]]): MyList[T] =
    ls.flatMap(identity)

  def empty[T]: MyList[T] = Empty

  def apply[T](ts: T*): MyList[T] =
    if (ts.isEmpty) Empty else +:(ts.head, apply(ts.tail: _*))

  def sum(list: MyList[Int]): Int = list match {
    case Empty => 0
    case head +: tail => head + sum(tail)
  }

  def product(list: MyList[Double]): Double = list match {
    case Empty => 1
    case 0 +: _ => 0 // optimization
    case head +: tail => head * product(tail)
  }
}
