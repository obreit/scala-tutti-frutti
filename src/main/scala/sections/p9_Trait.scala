package sections

// Trait

object p9_Trait {

  trait MyTrait {
    def abstractBin(x: Int, y: Int): Int

    def abstractBinToString(x: Int, y: Int): String = abstractBin(x, y).toString
  }
  class Adder extends MyTrait {
    override def abstractBin(x: Int, y: Int): Int = x + y
  }
  class Subtractor extends MyTrait {
    override def abstractBin(x: Int, y: Int): Int = x - y
  }
  val adder = new Adder
  val subtractor = new Subtractor
  val multiplier = new MyTrait {
    override def abstractBin(x: Int, y: Int): Int = x * y
  }
  println(adder.abstractBinToString(10, 5))
  println(subtractor.abstractBinToString(10, 5))
  println(multiplier.abstractBinToString(10, 5))
}
