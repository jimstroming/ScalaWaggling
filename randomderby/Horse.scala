package com.randomderby

package horse

import RNG.RNG
import simpleRNG.SimpleRNG

class Horse(prob: Double) {

  val p = prob  // probability of going forward
  val step = 1  // horse moves either forward or backward 1

  def move(pos: Int, rng: RNG): (Int, RNG) = {
    val (newnumber, rngnext) = rng.nextInt
    val modnumber = newnumber % 1000
    val cutoff = 1000 * p 
    if (modnumber < cutoff) (step, rngnext)
    else (-step, rngnext)
  }

} 
