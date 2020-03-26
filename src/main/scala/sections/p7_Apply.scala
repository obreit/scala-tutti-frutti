package sections

// Apply

import sections.p3_Functions.{add, oneParam, oneParamDef}

object p7_Apply {


  oneParam(1)
  oneParam.apply(1)
  add.apply(2, 1)

  // oneParamDef.apply(1) <-- method needs to be unapplied first
  val oneParamDefUnapplied = oneParamDef _
  //val oneParamDefUnapplied: Int => String = oneParamDef _ <-- alternative
  oneParamDefUnapplied(1)

  class ApplyClass(i: Int) {
    def apply(s: String): String = s"$s$i"
  }
  val applyClass = new ApplyClass(1)
  applyClass("number_")
  applyClass.apply("number_")

  class MyCaseClassApproximation(val i: Int, val s: String)
  object MyCaseClassApproximation {
    def apply(i: Int, s: String): MyCaseClassApproximation =
      new MyCaseClassApproximation(i, s)
  }
  val caseclassApprox = MyCaseClassApproximation(1, "bar")
  println(caseclassApprox.s)
}
