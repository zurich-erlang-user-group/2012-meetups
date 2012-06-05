-module(lazy_eval).
-compile(export_all).


even(X) ->
    2*X.

inc(X) ->
    X + 1.

range(F, Num) ->
    fun() -> [F(Num)|range(F, Num+1)] end.

take(NumItems, Range) ->
    generate(Range, NumItems, []).

generate(_Fun, 0, Acc) -> lists:reverse(Acc);
generate(Fun, ToGo, Acc)  ->
    [Val|Func] = Fun(),
    generate(Func, ToGo - 1, [Val | Acc]).
