package com.randomderby

package derby

import RNG.RNG
import simpleRNG.SimpleRNG
import horse.Horse

object Derby {
  
  def racestep(horses: List[Horse], rng: RNG, olddist: List[Int], newdist: List[Int] = List()): (List[Int], RNG) = {

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


  def convertdistance(horses: List[Horse], dist: List[Int]): List[Int] = {
    if (dist.length != 0) dist    // in case no currdist passed in, need to initialize to zero
    else List.fill(horses.length)(0)
  }


  def race(horses: List[Horse], finishdist: Int, rng: RNG, currdist: List[Int] = List()): (List[Int], RNG) = {

    val distances = convertdistance(horses, currdist) // in case no currdist passed in, need to initialize to zero
    val (newdistances, rng2) = racestep(horses, rng, distances) 
    val winnerlist =  checkforwinner(newdistances, finishdist) 
    if (winnerlist.length != 0) (winnerlist, rng2)
    else race(horses, finishdist, rng2, newdistances)

  }

}

