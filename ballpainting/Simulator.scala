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

/*
  Now that we have the probability of transitioning between the states, we can 
  use the probabilities to calculate the average number of turns required to 
  get between the states.

  The shortest path is from high card -> one pair -> three of a kind -> four of a kind
  So the shortest path is 3 turns.  And, in fact, all solutions have to go through
  these four states. 

  The outlier state if two pair, since there are solutions that don't pass through it.
  Once in two pair, the desired path is to go to three of a kind, though once there
  you may bounce back to two pair on your next turn.

  Let's start by calculating the average number of turns to get from 
  two pair to three of a kind.

  In two pair, we have to 2/3 change of going to three of a kind, and 
  a 1/3 chance of staying in two pair, so

  avg turns = 1 + 1/3(1 + 1/3(1 + 1/3 ...

  This is an infinite sequence

  avg turns = 1 + 1/3 + 1/9 + 1/27 + ...
            = 1 + sum(n from 1 to infinity) (1/3)^n 
            = 1 +  (1/3)/(1-(1/3)) = 1 + 1/2 = 3/2

  avg turns(RRBB -> RRRB) = 3/2


  So, it takes an average of 1.5 turns to go from two pair to three of a kind.

  
  Now we need to calculate the average number of steps for the other state transitions.

  The first transition is the easiest.  High card to two pair is the only possible path, so

  avg turns(RGBY -> RRBG) = 1

  The second transition is from two pair to three of a kind.  There are two paths
  to get to three of a kind:  we can go straight there, or we can detour through two pair.
  We can also, of course, spend a turn stuck in two pair

  avg turns = 1 + (1/6)(avg turns from RRBB -> RRRB) + (1/2)[1 + (1/6)(avg turns ...

  Another infinite series, but we already calculated that avg turns(RRBB -> RRRB) = 3/2

  so, 
  avg turns = 1 + (1/6)(3/2) + (1/2)[1 + (1/6)(3/2) ...
            = 1 + (1/4) + (1/2)[1 + (1/4) + (1/2)[...
            = 5/4 + 5/8 + 5/16 + ...
            = (5/2)sum(n from 1 to infinity) (1/2)^n
            = (5/2)(1) = 5/2 

  So, 2.5 turns, on average to get from one pair to three of a kind.

  One we get to three of a kind, we are almost there
  We have 1/4 chance of getting four of a kind, a 1/2 chance of staying at three of a kind,
  and a 1/4 chance of going to two pair.  We know from earlier that it will take us 3/2 turns
  to get back from two pair to three of a kind.  

  so, 
  avg turns = 1 + (1/2)[x] + (1/4)[(3/2) + x]   where x = 1 + (1/2)[x] + (1/4)[(3/2) + x]
            = 1 + (3/8) + (3/4)[1 + (3/8) + (3/4)[...
            = (11/8) + (3/4)[11/8 + (3/4)[(11/8) + ...
            = (11/8) + (11/8)sum(n = 1 to infinity of (3/4)^n) 
            = (11/8) + (11/8)(3/4)/(1-3/4) = (11/8) + (11/8)(3)
            = 44/8 = 11/2

  So, the total expected turns is the sum of the four steps.

  avg turns = 1 + 5/2 + 11/2
            = (2 + 5 + 11)/2 
            = 18/2   = 9 turns

  So 9 turns, on average, to win the game is what we find analytically.
 
  Let's see how this compares to the scala simulation.

*/

object BallSimulator {


  def paintuntildone(bag: BagOfBalls, bagcontents: List[Int], rng: RNG, n: Int): (Int, RNG) = {
    println("paintuntildone "+n)
    val (newcontents, newrng) = bag.pulltwoandpaint(bagcontents, rng)
    if (!bag.checkifallonecolor(newcontents))
      paintuntildone(bag, newcontents, newrng, n+1)
    else (n+1, newrng)
  }

  def simulate(numberofballs: Int, rng: RNG): (Int, RNG) = {
    val bag = new BagOfBalls(numberofballs)
    val contents = bag.fillbagoneofeach
    paintuntildone(bag, contents, rng, 0)
  }

  def simulatemany(numberofballs: Int, rng: RNG, remaining: Int, totalsteps: Int = 0, executed: Int = 0) : Double = {
    if (remaining == 0)
      totalsteps.toDouble / executed.toDouble
    else {
      val (steps, newrng) = simulate(numberofballs, rng)
      simulatemany(numberofballs, newrng, remaining - 1, totalsteps + steps, executed + 1)  
    }        
  }

  def main(args: Array[String]): Unit = {
    
    val n = simulatemany(4, SimpleRNG(61), 100000)
    println(n)

  }

}

/* Let's run it with a few different seeds and see how it works

  simulatemany(4, SimpleRNG(60), 100000)
  8.9874

  simulatemany(4, SimpleRNG(61), 100000)
  9.00927

  Confirms our analysis that it would take an average of 9 turns.

*/


