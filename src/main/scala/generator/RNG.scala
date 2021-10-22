package generator

trait RNG {
  def nextInt: (Int, RNG)
}
object RNG {
  type Rand[+T] = RNG => (T, RNG)

  def unit[A](a: A): Rand[A] = (a, _)

  def map[S, A, B](rand: S => (A, S))(f: A => B): S => (B, S) =
    rng => {
      val (a, nextRng) = rand(rng)
      (f(a), nextRng)
    }

  def mapViaFlatMap[A, B](rand: Rand[A])(f: A => B): Rand[B] =
    flatMap(rand)(a => unit(f(a)))

  def flatMap[A, B](randA: Rand[A])(f: A => Rand[B]): Rand[B] =
    rng => {
      val (a, rng1) = randA(rng)
      f(a)(rng1)
    }

  def map2[A, B, C](randA: Rand[A], randB: Rand[B])(f: (A, B) => C): Rand[C] =
    rng => {
      val (a, rngFromA) = randA(rng)
      val (b, rngFromB) = randB(rngFromA)
      (f(a, b), rngFromB)
    }

  def map2ViaCombinators[A, B, C](randA: Rand[A], randB: Rand[B])(f: (A, B) => C): Rand[C] =
    flatMap(randA)(a => map(randB)(b => f(a, b)))

  def both[A, B](randA: Rand[A], randB: Rand[B]): Rand[(A, B)] =
    map2(randA, randB)((_, _))

  def sequence[A](listRands: List[Rand[A]]): Rand[List[A]] =
    listRands.foldRight(unit(List.empty[A])) {
      case (currentRand, randAcc) =>
        map2(currentRand, randAcc)(_ :: _)
    }

  val int: Rand[Int] = _.nextInt

  val nonNegativeInt: Rand[Int] =
    map(int)(i => if(i == Int.MinValue) Math.abs(i + 1) else Math.abs(i))

  def nonNegativeLessThan(n: Int): Rand[Int] =
    flatMap(nonNegativeInt) { i =>
      val mod = i % n
      if(i + (n -1) - mod >= 0) unit(mod) else nonNegativeLessThan(n)
    }

  val double: Rand[Double] =
    map(nonNegativeInt)(_.toDouble / (Int.MaxValue + 1))

  val intDouble: Rand[(Int, Double)] =
    both(int, double)

  val doubleInt: Rand[(Double, Int)] =
    both(double, int)

  val double3: Rand[(Double, Double, Double)] = {
    val double2 = both(double, double)
    val randDouble3 = both(double2, double)

    map(randDouble3) { case ((d1, d2), d3) => (d1, d2, d3) }
  }

  def ints(count: Int): Rand[List[Int]] =
    sequence(List.fill(count)(int))

  val rollDie: Rand[Int] = map(nonNegativeLessThan(6))(_ + 1)
}