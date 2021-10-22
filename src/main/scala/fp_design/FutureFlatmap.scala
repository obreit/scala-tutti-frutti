package fp_design

import scala.util.{Failure, Success}

object FutureFlatmap extends App {


  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.{Future, Promise}

  def flatMap[T, U](future: Future[T])(f: T => Future[U]): Future[U] = {
    val promise = Promise[U]() // define the write part (i.e. Promise) of the async computation
    future.onComplete { // this callback is run after the 1st future is done, and the result is available
      case Failure(firstException) => promise.complete(Failure(firstException)) // "complete" the async computation as failed
      case Success(value) =>
        // we only run the 'f' function if the current future 'future' is successful!
        f(value).onComplete { // this runs after the 2nd future is done (2nd future == f(value))
          case Failure(secondException) => promise.complete(Failure(secondException))
          case Success(mappedValue) => promise.complete(Success(mappedValue)) // both async computations are successful, "complete" the async computation with the resulting value
        }
    }
    promise.future // getting the "Future" side of the async computation
  }

  // with a bit more helper methods (but it's doing the same as above, i wrote it more explicitly above to make clear what's happening)
  def flatMapNicer[T, U](future: Future[T])(f: T => Future[U]): Future[U] = {
    val promise = Promise[U]()
    future.onComplete {
      case Failure(exception) => promise.failure(exception)
      case Success(value) => promise.completeWith(f(value)) // this helper function essentially does what i wrote above explicitly
    }
    promise.future
  }
}
