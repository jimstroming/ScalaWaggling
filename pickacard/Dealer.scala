package com.cardriddler

package dealer

import RNG.RNG
import simpleRNG.SimpleRNG


class Dealer {

  def getHand(handsize: Int, decksize: Int, rng: RNG): (List[Int], RNG) = {
    val (hand, simplerng) = buildHand(List(), rng, handsize, decksize)
    (hand, simplerng)

  }
    

  def buildHand(hand: List[Int], rng: RNG, cardsleft: Int, decksize: Int): (List[Int], RNG) = {

    if (cardsleft == 0) (hand, rng)
    else {
      
      val (newnumber, rngnext) = rng.nextInt  // generate a new number

      val newcard =  ((newnumber.abs) % decksize) + 1         // convert it to a card from 1 to decksize

      if (hand.contains(newcard)) // check if it is already in the hand

        buildHand(hand, rngnext, cardsleft, decksize) // if it is, call buildhand again

      else // if it is not, add it to the hand and call buildhand again with one fewer card left 

        buildHand(hand:::List(newcard), rngnext, cardsleft - 1, decksize)
    }


  }



}



/* 

val dealer = new Dealer
val rng = SimpleRNG(42)
val (hand1, rng2) = dealer.getHand(10, 100, rng)
val (hand2, rng3) = dealer.getHand(10, 100, rng2) 

*/

