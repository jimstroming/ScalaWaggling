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
      val position =  ((newnumber.abs) % numcolors)  // convert it to a position from 0 to numballs-1
      val (l1, l2) = bag.splitAt(position)
      (bag(position), l1 ::: (l2.drop(1)), rngnext)
    }
  }

  def pushball(bag: List[Int], ball: Int): List[Int] = bag ::: List(ball)
  
  def checkifallonecolor(bag: List[Int]): Boolean = bag.forall(_ == bag.head) 
}


