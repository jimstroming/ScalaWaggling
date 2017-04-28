package com.cardriddler

package percentrule

import probcalculator.ProbCalculator

class PercentRule {

  val calc = new ProbCalculator()

  def drawanotherpercentrule(decksize: Int, cardsfaceup: List[Int], cardsfacedown: List[Int], pcutoff: Double): Boolean = {
    val p: Double = calc.pmaxcardshown(decksize, cardsfacedown.length: Int, cardsfaceup)
    if (p > pcutoff) false else true
  }

}

