Project 1 
=========

> "Greetings, Professor Falken. Shall we play a game?
>
> How about a nice game of chess?"
>
> -- WOPR (WarGames)

What is the maximum number of queens that can be placed on an n x n chessboard such that no two queens attack one another? (A queen can move any number of squares vertically, horizontally or diagonally.)

The answer is n queens.

However, the number of *different ways* the n queens can be placed on an n x n chessboard depends on n.

[Algebraic notation](https://en.wikibooks.org/wiki/Chess/Algebraic_notation) notates the standard 8 x 8 board in the following way (*files* are labeled a to h and *ranks* are numbered 1 to 8):

    a8 b8 c8 d8 e8 f8 g8 h8
    a7 b7 c7 d7 e7 f7 g7 h7
    a6 b6 c6 d6 e6 f6 g6 h6
    a5 b5 c5 d5 e5 f5 g5 h5
    a4 b4 c4 d4 e4 f4 g4 h4
    a3 b3 c3 d3 e3 f3 g3 h3
    a2 b2 c2 d2 e2 f2 g2 h2
    a1 b1 c1 d1 e1 f1 g1 h1

...which is very suggestive of a representation for a solution (or partial solution) to the n-queens problem:

    [2 5 7 1 3 8 6 4]
    
In the above example, 2 corresponds to a queen on a2, 5 corresponds to a queen on b5, etc... Note that this vector is a solution to n-queens for n=8. It's also a *partial* solution for n=9; [2 5 7 1 3 8 6 4 9] is a complete solution for n=9.

Requirements
------------

For project 1, write the following functions in Clojure:

    (qextends? partial-sol rank)
    
Given partial solution vector *partial-sol* of length k, and candidate queen placement on file k+1 on row *rank*, return true iff the new placement is valid (i.e. no queens attack one another). For example (qextends? [1] 2) should return false, since a queen on a1 can clearly attack a queen on b2. (qextends? [1 3] 5) should return true.

    (qextend n partial-sol-list)

Given a list *partial-sol-list* of all partial solutions of length k (e.g. [[1 3] [1 4] ...]), return a list of all partial solutions of length k + 1. *n* is the size of the board (i.e. an n x n board).

    (sol-count n)
    
Returns the total number of n-queens solutions on an n x n board.

    (sol-density n)
    
Return the *density* of solutions on an n x n board where density is number of solutions / number of possible placements. Only consider placements that have *exactly* one queen per file (since this is the only type of placement the board representation can represent.)

(We won't test sol-count or sol-density on n < 1, so don't worry about the number of "solutions" for a board of size 0.)

Finally, plot a graph of solution count as a function of board size (n) from n = 1 to 10. (You may use whatever plotting software you like.)

Submission
----------

Name your code lastname_project1.clj, your plot lastname_project1.png and push it to the submit server

Note that we will be grading your projects by script, therefore it is very important to use the *exact* function names and arities described above. (You may change the name of the formal parameters if you wish, as this will not affect the grading scripts.)

We will provide a short set of public tests. We strongly recommend you write your own additional unit tests.

Don't use the ns macro - if you don't know what that is, don't worry about it.

All of the release tests will be suffixed with "-test". Please don't name any of your own functions in lastname_project1.clj this way to avoid naming collisions.
