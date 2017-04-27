package com.cardriddler

package probcalculator

object ProbCalculator {

 /* Let's define a function that gives the probabililty of the max card
   being the card we just turned over */
   
  def pmaxcardshowniterative(decksize: Int, maxcardshown: Int, numbercardsshown: Int, numbercardsfacedown: Int, p: Double): Double = {
    // println(p)
    if (numbercardsfacedown == 0) p
    else {
      pmaxcardshowniterative(decksize, maxcardshown, numbercardsshown + 1, numbercardsfacedown - 1,  p * ((maxcardshown - numbercardsshown).toDouble / (decksize - numbercardsshown).toDouble))
    }
  }
   
  def pmaxcardshown(decksize: Int, numbercardsfacedown: Int, cardsshown: List[Int]): Double = {
    val lastcard = cardsshown(cardsshown.length - 1)
    if (lastcard != cardsshown.max) return 0.0
    val numberunshowncards = decksize - cardsshown.length
    val numberunshowncardslessthanmax = cardsshown.max - 1
    pmaxcardshowniterative(decksize, cardsshown.max, cardsshown.length, numbercardsfacedown, 1.0)                                           
  }


}

