-module(higher_order_funs).

-export([map/2]).

map(F, L) ->
    map(F, L, []).

map(_F, [], Acc) ->
    lists:reverse(Acc);
map(F,[H|T],Acc) ->
    map(F, T, [F(H) | Acc]).
    
%% Example: 
%% 3> higher_order_funs:map(fun(X) -> X + 1 end, lists:seq(1,10)).
%% [2,3,4,5,6,7,8,9,10,11]
