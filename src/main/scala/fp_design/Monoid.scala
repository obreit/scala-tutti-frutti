package fp_design

trait Monoid[A] {
  def zero: A
  def op(a1: A, a2: A): A
}

object Monoid {

  def instance[A](z: A)(f: (A, A) => A): Monoid[A] = new Monoid[A] {
    override def zero: A = z
    override def op(a1: A, a2: A): A = f(a1, a2)
  }

  def dual[A](m: Monoid[A]): Monoid[A] = instance(m.zero)((x, y) => m.op(y, x))

  val stringMonoid: Monoid[String] = instance("")((s1, s2) => s"$s1$s2")
  val intAddMonoid: Monoid[Int] = instance(0)(_ + _)
  val intMultMonoid: Monoid[Int] = instance(1)(_ * _)
  val boolOrMonoid: Monoid[Boolean] = instance(false)(_ || _)
  val boolAndMonoid: Monoid[Boolean] = instance(true)(_ && _)

  def listMonoid[A]: Monoid[List[A]] = instance(List.empty[A])(_ ++ _)
  def optionMonoid[A]: Monoid[Option[A]] = instance(Option.empty[A])(_ orElse _)

  def endoMonoid[A]: Monoid[A => A] = instance(identity[A] _)(_ compose _)

  def concatenate[A](ls: List[A], m: Monoid[A]): A =
    ls.foldLeft(m.zero)(m.op)

  def foldMap[A, B](ls: List[A], m: Monoid[B])(f: A => B): B =
    ls.foldLeft(m.zero)((b, a) => m.op(b, f(a)))

  def foldRightViaFoldMap[A, B](ls: List[A])(z: B)(f: (A, B) => B): B =
    foldMap(ls, endoMonoid[B])(a => b => f(a, b))(z)

  def foldLeftViaFoldMap[A, B](ls: List[A])(z: B)(f: (B, A) => B): B =
    foldMap(ls, dual(endoMonoid[B]))(a => b => f(b, a))(z)

  def foldMapV[A, B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = {
    if(v.isEmpty) m.zero
    else if(v.length == 1) f(v(0))
    else {
      val middle = v.length / 2
      val (firstHalf, secondHalf) = v.splitAt(middle)
      m.op(foldMapV(firstHalf, m)(f), foldMapV(secondHalf, m)(f))
    }
  }
}
