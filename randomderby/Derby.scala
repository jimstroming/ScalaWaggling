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
      // println(wins, newwinners, newwinners.head, newwinners.tail)
      val newwins = wins.updated(newwinners.head, wins(newwinners.head) + 1)
      incrementwinlist(newwins, newwinners.tail)
    }
  }

  def runraces(horses: List[Horse], finishdist: Int, rng: RNG, n: Int, win: List[Int] = List()): (List[Int], RNG) = {
    
    if (n == 0) (win, rng)
    else {
      if (n % 1000 == 0) println(win)
      val winners = fillzeroes(horses, win)
      val (roundwinners, rng2) = race(horses, finishdist, rng)
      val newwinlist = incrementwinlist(winners, roundwinners)
      runraces(horses, finishdist, rng2, n-1, newwinlist)
    }
  }


  def main(args: Array[String]): Unit = {
    
    val horses = List(new Horse(0.52), 
                      new Horse(0.54), 
                      new Horse(0.56),
                      new Horse(0.58),
                      new Horse(0.60), 
                      new Horse(0.62), 
                      new Horse(0.64),
                      new Horse(0.66),
                      new Horse(0.68), 
                      new Horse(0.70),
                      new Horse(0.72),
                      new Horse(0.74), 
                      new Horse(0.76), 
                      new Horse(0.78),
                      new Horse(0.80),
                      new Horse(0.82),
                      new Horse(0.84),
                      new Horse(0.86)) 
                      // new Horse(0.88))
                      // new Horse(0.90))
                     

    // val (winners, newrng) = race(horses, 2000, SimpleRNG(60))
    val (winners, newrng) = runraces(horses, 2000, SimpleRNG(60), 10000000)
    println(winners)

  }

}



/* Let's run some simulations to find the answer.  

List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 536, 25863, 325467)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 539, 25931, 326406)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 539, 26002, 327344)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 543, 26083, 328271)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 548, 26166, 329198)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 549, 26229, 330140)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 551, 26289, 331084)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 551, 26362, 332017)

This is taking a long time.   I have only seen horse 17, 18, 19 and 20 win to this point.
Fortunately, we can speed this up by excluding some of the horses.
If I eliminate horse 20, for instance, I would expect the ratio between wins of horse 18 and 19 to 
stay the same.

Let's try it out

List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 94, 3329, 33911)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 96, 3419, 34824)

List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 66, 1725, 16360)
List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 70, 1835, 17256)


*/



