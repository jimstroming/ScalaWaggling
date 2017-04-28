package com.cardriddler

package simulator

import RNG.RNG
import simpleRNG.SimpleRNG
import dealer.Dealer
import percentrule.PercentRule

/* 
  The April 21, 2017 FiveThirtyEight Riddler
  https://fivethirtyeight.com/features/pick-a-card-any-card/

  From a shuffled deck of 100 cards that are numbered 1 to 100, you are dealt 10
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

   The problem is, we have incomplete information.   
   The key is correctly identifying that the card you have is the max card.
   It is very easy to screw up.  For example
   (56, 30, 76,  40,  12, 60, 45, 58, 72, 14)
   The max came early, in the third slot.  At that point, the odds
   of the max being 76 was only 13%.   So we kept going and never
   hit a new local max
  
   We can also miss the other way.  What about the following case
   (56, 30, 70, 40, 12, 74, 29, 80, 12, 85)
   Where do we stop?
   When we drew 70 with card 3, that only had a 7% chance of being the high, so we kept going
   On card 6, we drew 74.  This had a 27% chance of being high.  Probably keep going
   On card 8, we drew 80.  This had a 61% chance of being high.  We would probably stop there.
   But unfortunately, on card 10, we drew an 85. 
  
   What this shows is that, if there is a 50% chance of the current card being
   the maximum, and you chose to draw another, your chance of winning becomes < 50%,
   because even if the low card is in the face down cards, you may not identify it when it comes.
  
   Rule 2:  If the chance of your current card being max is 50% or more, stop.
  
   There is still room for improvement.  But I don't know how to quantify it right now.
   Instead, I think we will need to simulate it, and try tuning that 50% lower to account for 
   the fact we can not perfectly identify the max.

*/

object CardSimulator {



  
  /* returns true if we stopped on the maximum card we were dealt */

  def simulate(decksize: Int, cardsfaceup: List[Int], cardsfacedown: List[Int], f: (Int, List[Int], List[Int], Double) => Boolean):
    Boolean = {
      if (cardsfacedown.length != 0 && f(decksize, cardsfaceup, cardsfacedown, 0.47)) {
        //println("Turning over another card")
        val newcardsfaceup =   cardsfaceup ::: List(cardsfacedown(0))
        val newcardsfacedown = cardsfacedown.drop(1)
        simulate(decksize, newcardsfaceup,  newcardsfacedown, f(_,_,_,_))
      }
      else {
        val lastcard = cardsfaceup(cardsfaceup.length - 1)
        val allcards = cardsfaceup:::cardsfacedown
        //println(allcards)
        //println(lastcard)
        if (lastcard == allcards.max) {
          //println("Win")
          true
        } 
        else {
          //println("Lose") 
          false
        }
      }
    } 

   // simulate(100, List(50), List(1,2,3,4,5,60,7,8,9), drawanotherpercentrule(_,_,_,_))  

   def runsimulations(decksize: Int, handsize: Int, numberofhands: Int, f: (Int, List[Int], List[Int], Double) => Boolean, 
                                      rng: RNG, wins: Int, losses: Int): (Int, Int, RNG) = {
     if (numberofhands == 0) (wins, losses, rng)
     else {
       val dealer = new Dealer
       val (hand, newrng) = dealer.getHand(handsize, decksize, rng)
       val faceup = List(hand(0))
       val facedown = hand.drop(1)
       if (simulate(decksize, faceup, facedown, f))
         runsimulations(decksize, handsize, numberofhands - 1, f, newrng, wins + 1, losses)
       else {
         runsimulations(decksize, handsize, numberofhands - 1, f, newrng, wins, losses + 1)
       }
       

     }    
  }

  def main(args: Array[String]): Unit = {

   val pr = new PercentRule()
   val sim = CardSimulator
   val (wins, losses, rng) = sim.runsimulations(100, 10, 10000, pr.drawanotherpercentrule(_,_,_,_), SimpleRNG(32), 0, 0)
   println("Total Wins = " + wins)
   println("Total Loss = " + losses)
   
  }

}

/*

  command line:
 
  val sim = CardSimulator
  sim.runsimulations(100, 10, 5, drawanotherpercentrule(_,_,_,_), SimpleRNG(23), 0, 0)


  package:

  scala com.cardriddler.simulator.CardSimulator
  

*/ 

/* Results:

With cutoff = 50%, seed = 26
Total Wins = 62099
Total Loss = 37901

With cutoff = 45%, seed = 26
Total Wins = 62224
Total Loss = 37776

With cutoff = 30%, seed = 26
Total Wins = 59356
Total Loss = 40644

With cutoff = 55%, seed = 26
Total Wins = 61338
Total Loss = 38662

With cutoff = 40%, seed = 26
Total Wins = 61698
Total Loss = 38302

-------------

With cutoff = 30%, seed = 27
Total Wins = 5821
Total Loss = 4179

With cutoff = 40%, seed = 27
Total Wins = 6054
Total Loss = 3946

With cutoff = 60%, seed = 27
Total Wins = 5967
Total Loss = 4033

With cutoff = 10%, seed = 27
Total Wins = 4512
Total Loss = 5488

--------------------

With cutoff = 50%, seed = 27
Total Wins = 62066
Total Loss = 37934

With cutoff = 45%, seed = 27
Total Wins = 62211
Total Loss = 37789

With cutoff = 43%, seed = 27
Total Wins = 62119
Total Loss = 37881

With cutoff = 47%, seed = 27
Total Wins = 62270
Total Loss = 37730

With cutoff = 46%, seed = 27

-------------------

With cutoff = 47%, seed = 27
Total Wins = 621206
Total Loss = 378794

With cutoff = 47%, seed = 28
Total Wins = 621206
Total Loss = 378794

With cutoff = 50%, seed = 27
Total Wins = 619604
Total Loss = 380396

With cutoff = 50%, seed = 28
Total Wins = 620164
Total Loss = 379836

With cutoff = 45%, seed = 27
Total Wins = 620963
Total Loss = 379037

With cutoff = 48%, seed = 27
Total Wins = 620863
Total Loss = 379137

With cutoff = 46%, seed = 27
Total Wins = 621144
Total Loss = 378856

---------------

With cutoff = 46%, seed = 30
Total Wins = 6217189
Total Loss = 3782811

With cutoff = 47%, seed = 30
Total Wins = 6217400
Total Loss = 3782600

With cutoff = 48%, seed = 30
Total Wins = 6214532
Total Loss = 3785468

---------

With cutoff = 46%, seed = 31
Total Wins = 62189918
Total Loss = 37810082

With cutoff = 47%, seed = 31
Total Wins = 62190761
Total Loss = 37809239

With cutoff = 48%, seed = 31
Total Wins = 62167159
Total Loss = 37832841

-----------

With cutoff = 47%, seed = 32
Total Wins = 62182610
Total Loss = 37817390

-----------

So this is my solution:  Keep drawing cards until you get to a card that has a 47% or greater chance of being the high card.
This gives you a 62% chance of victory.

*/

