package com.ballpainter

package RNG




// Let's use the random number generator from Functional Programming in Scala 
// by by Paul Chiusano and Runar Bjarnason

trait RNG {
  def nextInt: (Int, RNG)
}

