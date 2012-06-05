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

generate(Range, 0, Acc) -> {Range, lists:reverse(Acc)};
generate(Range, ToGo, Acc)  ->
    [Val|Next] = Range(),
    generate(Next, ToGo - 1, [Val | Acc]).
