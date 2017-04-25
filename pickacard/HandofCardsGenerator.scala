

class HandofCardsGenerator(seed: Long) {
  //def nextHand: (Int, RNG) = {
  //  val rng = SimpleRNG(seed)
    

  def buildHand(hand: List[Int], rng: RNG, cardsleft: Int): (List[Int], RNG) = {

    if (cardsleft == 0) (hand, rng)
    else {
      
      val (newcard, rngnext) = rng.nextInt  // generate a new card

      if (hand.contains(newcard)) // check if it is already in the hand

        buildHand(hand, rngnext, cardsleft) // if it is, call buildhand again

      else // if it is not, add it to the hand and call buildhand again with one fewer card left 

        buildHand(hand:::List(newcard), rngnext, cardsleft - 1)

    }
  }
}


