

class HandofCardsGenerator(seed: Long) {
  //def nextHand: (Int, RNG) = {
  //  val rng = SimpleRNG(seed)
    

  def buildHand(hand: List[Int], rng: RNG, cardsleft: Int): (List[Int], RNG) = {

    if (cardsleft == 0) (hand, rng)
    else {
      (List(1,2,3,4,5), rng)
    }
  }
}


