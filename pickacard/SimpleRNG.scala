package com.cardriddler

package simpleRNG

import RNG.RNG

// Let's use the random number generator from Functional Programming in Scala 
// by by Paul Chiusano and Runar Bjarnason

case class SimpleRNG(seed: Long) extends RNG {
  def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = SimpleRNG(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRNG)
  }
}


/* Usage

scala> val rng = SimpleRNG(42)
rng: SimpleRNG = SimpleRNG(42)

scala> val (n1, rng2) = rng.nextInt
n1: Int = 16159453
rng2: RNG = SimpleRNG(1059025964525)

scala> val (n2, rng3) = rng2.nextInt
n1: Int = -1281479697
rng3: RNG = SimpleRNG(197491923327988)

*/
