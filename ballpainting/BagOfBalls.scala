package com.ballpainter

package bagofballs

import RNG.RNG
import simpleRNG.SimpleRNG


class BagOfBalls(colors: Int) {

  val numcolors = colors

  def fillbagoneofeach(): List[Int] = List.range(0,numcolors)

  def pullball(bag: List[Int], rng: RNG): (Int, List[Int], RNG) = {
  
    if (bag.length == 0) (-1, List(), rng)
    else {
      val (newnumber, rngnext) = rng.nextInt         // generate a new number
      val sizeofbag = bag.length
      val position =  ((newnumber.abs) % sizeofbag)  // convert it to a position from 0 to numballs-1
      val (l1, l2) = bag.splitAt(position)
      (bag(position), l1 ::: (l2.drop(1)), rngnext)
    }
  }

  def pushball(bag: List[Int], ball: Int): List[Int] = bag ::: List(ball)
  
  def checkifallonecolor(bag: List[Int]): Boolean = bag.forall(_ == bag.head) 

  def pulltwoandpaint(bagin: List[Int], rng: RNG): (List[Int], RNG) = {
    println("bag contains "+bagin)
    val (firstball,  bag2, rng2)  = pullball(bagin, rng) // pull the first ball
    println("first ball is "+firstball)
    val (secondball, bag3, rng3)  = pullball(bag2, rng2) // pull the second ball
    println("second ball is "+secondball)
    // return the first ball twice to simulate paining
    val bag4 = pushball(bag3, firstball)
    val bag5 = pushball(bag4, firstball)
    println("new bag contins"+bag5)
    (bag5, rng3)

  }

}


