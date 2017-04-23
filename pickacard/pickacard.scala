/* From a shuffled deck of 100 cards that are numbered 1 to 100, you are dealt 10
  cards face down. You turn the cards over one by one. After each card, you must
  decide whether to end the game. If you end the game on the highest card in the
  hand you were dealt, you win; otherwise, you lose.

What is the strategy that optimizes your chances of winning? 
How does the strategy change as the sizes of the deck and the hand are changed? 

*/


/* Some things are obvious.  

   Rule 1: If your current card is not the maximum card turned over so far, then you need to turn over the next card.
   
   The question then becomes, what do you do if the current card is the 
   highest card turned over so far. 
*/


object CardSimulator {


 /* Let's define a function that gives the probabililty of the max card
   being the card we just turned over */
   
  def pmaxcardshowniterative(decksize: Int, maxcardshown: Int, numbercardsshown: Int, numbercardsfacedown: Int, p: Double): Double = {
    println(p)
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
  
  // The problem is, we have incomplete information.   
  // The key is correctly identifying that the card you have is the max card.
  // It is very easy to screw up.  For example
  // (56, 30, 76,  40,  12, 60, 45, 58, 72, 14)
  // The max came early, in the third slot.  At that point, the odds
  // of the max being 76 was only 13%.   So we kept going and never
  // hit a new local max
  
  // We can also miss the other way.  What about the following case
  // (56, 30, 70, 40, 12, 74, 29, 80, 12, 85)
  // Where do we stop?
  // When we drew 70 with card 3, that only had a 7% chance of being the high, so we kept going
  // On card 6, we drew 74.  This had a 27% chance of being high.  Probably keep going
  // On card 8, we drew 80.  This had a 61% chance of being high.  We would probably stop there.
  // But unfortunately, on card 10, we drew an 85. 
  
  // What this shows is that, if there is a 50% chance of the current card being
  // the maximum, and you chose to draw another, your chance of winning becomes < 50%,
  // because even if the low card is in the face down cards, you may not identify it when it comes.
  
  // Rule 2:  If the chance of your current card being max is 50% or more, stop.
  
  // There is still room for improvement.  But I don't know how to quantify it right now.
  // Instead, I think we will need to simulate it, and try tuning that 50% lower 
  
  /* returns true if we are going to draw another.  false if we are going to stop */
  def drawanotherpercentrule(decksize: Int, cardsfaceup: List[Int], cardsfacedown: List[Int], pcutoff: Double): Boolean = {
    val p: Double = pmaxcardshown(decksize, cardsfacedown.length: Int, cardsfaceup)
    if (p > pcutoff) false else true
  }
  
  /* returns true if we stopped on the maximum card we were dealt */
  def simulate(decksize: Int, cardsfaceup: List[Int], cardsfacedown: List[Int], f: (Int, List[Int], List[Int], Double) => Boolean): Boolean = {
    if (cardsfacedown.length != 0 && f(decksize, cardsfaceup, cardsfacedown, 0.5)) {
      println("Turning over another card")
      val newcardsfaceup =   cardsfaceup ::: List(cardsfacedown(0))
      val newcardsfacedown = cardsfacedown.drop(1)
      simulate(decksize, newcardsfaceup,  newcardsfacedown, f(_,_,_,_))
    }
    else {
      val lastcard = cardsfaceup(cardsfaceup.length - 1)
      val allcards = cardsfaceup:::cardsfacedown
      if (lastcard == allcards.max) true else false
    }
  } 

// simulate(100, List(50), List(1,2,3,4,5,60,7,8,9), drawanotherpercentrule(_,_,_,_))  
   
}

