This is an AI solving the 8-puzzle game based on the A* search algorithm.

8-puzzle game:

The 8-puzzle problem is a puzzle invented and popularized by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8 and a blank square. Your goal is to rearrange the tiles so that they are in order, using as few moves as possible. You are permitted to slide tiles horizontally or vertically into the blank square. The following shows a sequence of legal moves from an initial board (left) to the goal board (right).(0 indicates a blank)


0  1  3        1  0  3        1  2  3        1  2  3        1  2  3
4  2  5   =>   4  2  5   =>   4  0  5   =>   4  5  0   =>   4  5  6
7  8  6        7  8  6        7  8  6        7  8  6        7  8  0

initial        1 left          2 up          5 left          goal


To run Solver.java, you will have to first compile it with command "javac -cp .\puzzle\algs4.jar; .\puzzle\Solver.java"

Then, run the Solver class with "java -cp .\puzzle\algs4.jar; puzzle.Solver .\puzzle\1.txt" with a 1.txt in this file folder. Then the solver will read in the content of the text file to perform a puzzle solving operation.

Note: the contents in 1.txt should be:

5
1 2 3 4 5
6 7 8 9 10
11 12 0 13 14 
16 17 18 19 15
21 22 23 24 20


where the first number 5 is the size of the puzzle and the next array is the original board.