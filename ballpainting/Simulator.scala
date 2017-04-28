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

  All states are not reachable from each other

  from high card, you can only go to one pair

  RBGY -> RRBG

  from one pair, you can transition to three of a kind, two pair, or stay in one pair

       -> RRRB
  RRBG -> RRBB
       -> RRBG

  from two pair, you can go to three of a kind or stay in two pair

  RRBB -> RRRB
       -> RRBB  

  and from three of a kind you can go to two pair, or you can go to four of a kind and victory

       -> RRBB
  RRRB -> RRRR
       -> RRRB

*/

/* This is a good start.  The next step is we need to calculate the probabilities of 
   transitioning between the different states.

  high card to one pair is easy.  100% you go to one pair.

  RBGY -> RRBG  1

  for one pair, there is a 1/2 chance of drawing R on the first pull
  There is a 1/3 chance of drawing another R, which gives a 1/6 chance of one pair.
  There is a 2/3 chance of drawing a B or G, which gives a 1/3 change of three of a kind.

  There is a 1/2 chance of drawing B or G on the first pull.
  There is a 2/3 chance of then drawing R, wihch gives a 1/3 change of one pair
  There is a 1/3 chance of instead drawing a G or B, which gives a 1/6 change of two pair

  If you combine the 1/6 and 1/3 chances of getting one pair, you get.

       -> RRRB 1/3
  RRBG -> RRBB 1/6
       -> RRBG 1/2

  Similarly for the other combinations

  for two pair

  RRBB -> RRRB 2/3
       -> RRBB 1/3

  for three of a kind

       -> RRBB 1/4 
  RRRB -> RRRR 1/4
       -> RRRB 1/2
 
*/
