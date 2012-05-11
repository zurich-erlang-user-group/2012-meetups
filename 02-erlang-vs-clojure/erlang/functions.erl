-module(functions).

-export([numbers/1]).

numbers(1) -> one;
numbers(2) -> two;
numbers(X) when X > 2 ->
    many.





