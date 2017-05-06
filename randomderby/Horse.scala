package randomderby

package horse

import RNG.RNG
import simpleRNG.SimpleRNG

class Horse(prob: Double) {

  val p = prob  // probability of going forward

  def move(pos: Int, rng: RNG): (Int, RNG) = {
    val (newnumber, rngnext) = rng.nextInt

  }


} 
