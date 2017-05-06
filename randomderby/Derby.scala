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


  def fillzeroes(horses: List[Horse], dist: List[Int]): List[Int] = {
    if (dist.length != 0) dist    // in case no currdist passed in, need to initialize to zero
    else List.fill(horses.length)(0)
  }


  def race(horses: List[Horse], finishdist: Int, rng: RNG, currdist: List[Int] = List()): (List[Int], RNG) = {

    val distances = fillzeroes(horses, currdist) // in case no currdist passed in, need to initialize to zero
    val (newdistances, rng2) = racestep(horses, rng, distances) 
    val winnerlist =  checkforwinner(newdistances, finishdist) 
    if (winnerlist.length != 0) (winnerlist, rng2)
    else race(horses, finishdist, rng2, newdistances)

  }

  def incrementwinlist(wins: List[Int], newwinners: List[Int]): List[Int] = {
    if (newwinners.length == 0) wins
    else {
      val newwins = wins.updated(newwinners.head, wins(newwinners.head + 1))
      incrementwinlist(newwins, newwinners.tail)
    }
  }

  def runraces(horses: List[Horse], finishdist: Int, rng: RNG, n: Int, win: List[Int]): List[Int] = {
    
    if (n == 0) win
    else {
      val winners = fillzeroes(horses, win)
      val (roundwinners, rng2) = race(horses, finishdist, rng)
      val newwinlist = incrementwinlist(winners, roundwinners)
      runraces(horses, finishdist, rng2, n-1, newwinlist)
    }
  }


  def main(args: Array[String]): Unit = {
    
    val horses = List(new Horse(0.2), new Horse(0.9))
    val (winners, newrng) = race(horses, 2000, SimpleRNG(60))
    println(winners)

  }



}

