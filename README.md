# distributed-solver

This implementation is solving a mathematical expression distributed using Dijkstra's Shunting yard algorithm.

See Test.java for a demonstration how to use Parser.java.

First it is parsing an expression and building a stack by Dijkstra's Shunting yard algorithm.

Next steps are working iteratively as all possible sub expressions are extracted,
solved with a dummy implementation and finally substituted by their sub solutions.

This is continuing until only one element is left at the working stack, which is indicating the final solution.

Shunting yard algorithm is implemented with less functionality as described in common literature.
Only numbers and operators like +, - , * , /, ( and ) are recognized. Functions and so one are not working within
this implementation.
