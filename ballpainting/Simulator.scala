package com.ballpainter

package simulator

import RNG.RNG
import simpleRNG.SimpleRNG
import bagofballs.BagOfBalls

/*

  The April 28, 2017 FiveThirtyEight Riddler
  https://fivethirtyeight.com/features/can-you-solve-these-colorful-puzzles/

  You play a game with four balls: One ball is red, one is blue, one is green and one 
  is yellow. They are placed in a box. You draw a ball out of the box at random and note 
  its color. Without replacing the first ball, you draw a second ball and then paint 
  it to match the color of the first. Replace both balls, and repeat the process. 

  The game ends when all four balls have become the same color. 
  What is the expected number of turns to finish the game?

*/

/* 

  There are only a fixed number of states.  One good way to think of the problem is in 
  terms of poker hands.  

  The initial state, RBGY, is high card
  After the first turn, one of the colors will go away,
  so you transition to a pair,   RRBG.
  
  There are a total of five states.

  RBGY - high card
  RRBG - one pair
  RRBB - two pari 
  RRRB - three of a kind
  RRRR - four of a kind

  The object, then, is to get from high card to four of a kind.




*/
