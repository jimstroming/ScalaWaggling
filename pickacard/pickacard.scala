/* From a shuffled deck of 100 cards that are numbered 1 to 100, you are dealt 10
  cards face down. You turn the cards over one by one. After each card, you must
  decide whether to end the game. If you end the game on the highest card in the
  hand you were dealt, you win; otherwise, you lose.

What is the strategy that optimizes your chances of winning? 
How does the strategy change as the sizes of the deck and the hand are changed? 

*/


/* Some things are obvious.  If your current card is not the maxium card
   turned over so far, then you need to turn over the next card.
   
   The question then becomes, what do you do if the current card is the 
   highest card turned over so far. 
*/


object CardSimulator {


 /* Let's define a function that gives the probabililty of the max card
   being in the face down cards */
   
  def pmaxcardshowniterative(decksize: Int, maxcardshown: Int, numbercardsshown: Int, numbercardsfacedown: Int, p: Double): Double = {
    println(p)
    if (numbercardsfacedown == 0) p
    else {
      pmaxcardshowniterative(decksize, maxcardshown, numbercardsshown + 1, numbercardsfacedown - 1,  p * ((maxcardshown - numbercardsshown).toDouble / (decksize - numbercardsshown).toDouble))
    }
  }
   
  def pmaxcardshown(decksize: Int, numbercardsfacedown: Int, cardsshown: List[Int]): Double = {
    val numberunshowncards = decksize - cardsshown.length
    val numberunshowncardslessthanmax = cardsshown.max - 1
    pmaxcardshowniterative(decksize, cardsshown.max, cardsshown.length, numbercardsfacedown, 1.0)                                           
  }
  
  // The problem is, we have incomplete information.   
  // The key is correctly identifying that the card you have is the max card.
  // It is very easy to screw up.  For example
  // (56, 30, 76,  40,  12, 60, 45, 58, 75, 14)
  // The max came early, in the third slot.  At that point, the odds
  // of the max .  
  
  
  
}

