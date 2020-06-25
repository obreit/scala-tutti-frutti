package mylist

sealed trait MyList[+T] {

  def map[U](f: T => U): MyList[U] = this match {
    case Empty => Empty
    case head +: tail => f(head) +: tail.map(f)
  }

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
