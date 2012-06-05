-module(functions).

-compile(export_all).

numbers(1) -> one;
numbers(2) -> two;
numbers(X) when X > 2 ->
    many.


numbers_case(X) ->
    case X of 
	1 -> one;
	2 -> two;
	_ -> many
    end.


deconstruction([_,[_,{_,What}]]) ->
	What. 

(emacs@stella)11> functions:deconstruction([{1, banan}, [liste, {1, tupel}]]).
tupel

	




