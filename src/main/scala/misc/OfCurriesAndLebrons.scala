package misc

object OfCurriesAndLebrons {

  def isSorted[A](ord: (A, A) => Boolean, as: Array[A]): Boolean = {
    def run(i: Int): Boolean = i match {
      case _ if i >= as.length - 1 => true
      case _ if !ord(as(i), as(i + 1)) => false
      case _ => run(i + 1)
    }

    run(0)
  }

  // Partial Application

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C =
    b => f(a, b)

  { // adding the brackets just to be able to reuse names in other examples

    val isSortedIntAsc = partial1(
      (i: Int, j: Int) => i <= j,
      isSorted[Int]
    )
    val isSortedIntDesc = partial1(
      (i: Int, j: Int) => i >= j,
      isSorted[Int]
    )

    isSortedIntAsc(Array(1, 2, 3))
    isSortedIntAsc(Array(3, 2, 1))
    isSortedIntDesc(Array(3, 2, 1))
    isSortedIntDesc(Array(1, 2, 3))
  }

  // Curry

  def curry[A, B, C](f: (A, B) => C): A => B => C =
    a => b => f(a, b)

  {
    val isSortedIntCurried /* : ((Int, Int) => Boolean) => Array[Int] => Boolean */ = curry(isSorted[Int])

    val isSortedIntDesc = isSortedIntCurried((i, j) => i > j)
    val isSortedIntAsc = isSortedIntCurried(_ < _)

    isSortedIntAsc(Array(1, 2, 3))
    isSortedIntDesc(Array(3, 2, 1))

    val isSortedDouble = curry(isSorted[Double])
    val isSortedFloat = curry(isSorted[Float])
    val isSortedChar = curry(isSorted[Char])

    val isSortedStringCurried = curry(isSorted[String])
    val isSortedStringAsc = isSortedStringCurried((a, b) =>
      a.compareTo(b) < 0 || a.compareTo(b) == 0
    )
    val isSortedStringDesc = isSortedStringCurried((a, b) =>
      a.compareTo(b) > 0 || a.compareTo(b) == 0
    )

    isSortedStringAsc(Array("a", "b", "c"))
    isSortedStringDesc(Array("3", "2", "1"))
  }

  {
    def isSortedExplicitCurry[A](ord: (A, A) => Boolean): Array[A] => Boolean =
      as => isSorted(ord, as)

    val isSortedIntAsc = isSortedExplicitCurry[Int](_ <= _)
    val isSortedIntDesc = isSortedExplicitCurry[Int](_ >= _)

    isSortedIntAsc(Array(1, 2, 3))
    isSortedIntDesc(Array(3, 2, 1))
  }

  {
    def isSortedSugarCurry[A](ord: (A, A) => Boolean)(as: Array[A]): Boolean =
      isSorted(ord, as)

    val isSortedIntAsc = isSortedSugarCurry[Int](_ <= _) _
    val isSortedIntDesc = isSortedSugarCurry[Int](_ >= _) _

    isSortedIntAsc(Array(1, 2, 3))
    isSortedIntDesc(Array(3, 2, 1))
  }

  {
    def isSortedCurryWhenCalling[A](ord: (A, A) => Boolean, as: Array[A]): Boolean =
      isSorted(ord, as)

    /* '_' when calling the function means i'm 'lifting' or 'unapplying' this function.
       So you can achieve the same currying effect even if you don't define multiple parameter lists.
       (see 'Uncurry' below for a further example of this.)
    */
    val isSortedIntAsc = isSortedCurryWhenCalling[Int]((i, j) => i <= j, _)
    isSortedIntAsc(Array(1, 2, 3))

    /* And with this style it doesn't matter in which order your arguments are.
       Here i'm first applying the function to a specific array
       and in the second step i provide the ordering function (so the reverse of what we've been doing until now)
    */
    val isSortedOnSpecificArray = isSortedCurryWhenCalling[Int](_, Array(1,2,3))
    isSortedOnSpecificArray((i, j) => i <= j)
  }

  // Uncurry

  // a.k.a lebron
  def uncurry[A, B, C](f: A => B => C): (A, B) => C =
    (a, b) => {
      val bFun = f(a)
      bFun(b)
    }

  def uncurryConcise[A, B, C](f: A => B => C): (A, B) => C =
    (a, b) => f(a)(b)

  def uncurrySoConciseItsAlmostNotUnderstandable[A, B, C](f: A => B => C): (A, B) => C =
    f(_)(_)
}
