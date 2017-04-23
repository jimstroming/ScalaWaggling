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

/*
   Let's define a function that gives the probabililty of the max card
   in the face down cards
*/

object CardSimulator {
  def pmaxcarddown(decksize: Int, numbercardsfacedown: Int, cardsshown: List[Int]): Double = {
     val numberunshowncards = decksize - cardsshown.length
     val numberunshowncardsgreaterthanmax = decksize - cardsshown.max
     numberunshowncardsgreaterthanmax.toDouble / numberunshowncards.toDouble                                            
  }
}

