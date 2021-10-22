package fp_design

import scala.concurrent.{ExecutionContext, Future}

object FlatMapExamples extends App {


  import scala.language.higherKinds
  trait FlatMappable[F[_]] {
    def unit[A](a: A): F[A]
    def flatMap[A, B](m: F[A])(f: A => F[B]): F[B]

    def map[A, B](m: F[A])(f: A => B): F[B] =
      flatMap(m)(f.andThen(unit))

    def flatten[A, B](m: F[A])(implicit asF: A => F[B]): F[B] =
      flatMap(m)(identity[A])
  }

  implicit class FlatMappableOps[A, F[_]: FlatMappable](fA: F[A]) {
    def flatMap[B](f: A => F[B]): F[B] = implicitly[FlatMappable[F]].flatMap(fA)(f)
    def map[B](f: A => B): F[B] = implicitly[FlatMappable[F]].map(fA)(f)
    def flatten[B](implicit asF: A => F[B]): F[B] = implicitly[FlatMappable[F]].flatten(fA)
  }

  implicit val optionFlatMappable: FlatMappable[Option] = new FlatMappable[Option] {
    override def unit[A](a: A): Option[A] = Option(a)
    override def flatMap[A, B](m: Option[A])(f: A => Option[B]): Option[B] = m.flatMap(f)
  }

  implicit def futureFlatMappable(implicit ec: ExecutionContext): FlatMappable[Future] = new FlatMappable[Future] {
    override def unit[A](a: A): Future[A] = Future(a)
    override def flatMap[A, B](m: Future[A])(f: A => Future[B]): Future[B] = m.flatMap(f)
  }

  implicit val listFlatMappable: FlatMappable[List] = new FlatMappable[List] {
    override def unit[A](a: A): List[A] = List(a)
    override def flatMap[A, B](m: List[A])(f: A => List[B]): List[B] = m.flatMap(f)
  }

  def map2[A, B, C, F[_]: FlatMappable](fA: F[A], fB: F[B])(f: (A, B) => C): F[C] = {
    for {
      a <- fA
      b <- fB
    } yield f(a, b)
  }

  import ExecutionContext.Implicits.global

  def pairUp[A, B, F[_]: FlatMappable](fA: F[A], fB: F[B]): F[(A, B)] =
    map2(fA, fB)((_, _))

  val futurePair = pairUp(Future(1), Future(2))
  val optionPair = pairUp(Option(1), Option(2))
  val listPair = pairUp(List(1,2,3), List(1,2,3))

  val optionSum = map2(Option(1), Option(2))(_ + _)
}
