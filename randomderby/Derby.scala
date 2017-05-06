package com.randomderby

package derby

import RNG.RNG
import simpleRNG.SimpleRNG
import horse.Horse

object Derby {
  
  def racestep(horses: List[Horse], rng: RNG, olddist: List[Int], newdist: List[Int]): (List[Int], RNG) = {

    if (horses.length == 0) return (newdist, rng)
    else {
      val currenthorse = horses.head
      val remaininghorses = horses.tail
      val (distance, newrng) = currenthorse.move(olddist.head, rng)
      racestep(remaininghorses, newrng, olddist.tail, newdist:::List(distance))
    }
  }

  def checkforwinner(dist: List[Int], finishline: Int): List[Int] = {

    dist.zipWithIndex.filter(_._1 >= finishline).map(_._2)

  }


  def race(horses: List[Horse], finishdist: Int, rng: RNG, currdist: List[Int] ): (List[Int], RNG) = {

    (List(1,2,3), rng)

  }


}

