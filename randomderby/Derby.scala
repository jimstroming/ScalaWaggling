package com.randomderby

package derby

import RNG.RNG
import simpleRNG.SimpleRNG
import horse.Horse

object Derby {
  
  def racestep(horses: List[Horse], newdist: List[Int], olddist: List[Int], rng: RNG): (List[Int], RNG) = {

    if (horses.length == 0) return (newdist, rng)
    else {
      val currenthorse = horses.head
      val remaininghorses = horses.tail
      val (distance, newrng) = currenthorse.move(olddist.head, rng)
      racestep(remaininghorses, newdist:::List(distance), olddist.tail, newrng)
    }
  }

}

