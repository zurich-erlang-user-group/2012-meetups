-module(many_processes).

-export([max/1]).

%% max(N)
%%   Create N processes then destroy them
%%   See how much time this takes

max(N) ->
    Max = erlang:system_info(process_limit),
    io:format("Maximum allowed processes: ~p~n", [Max]),
    statistics(runtime),
    statistics(wall_clock),
    L = [spawn(fun wait/0) || _ <- lists:seq(1, N)],
    [Pid ! die || Pid <- L],
    {_, Time1} = statistics(runtime),
    {_, Time2} = statistics(wall_clock),
    U1 = Time1 * 1000 / N,
    U2 = Time2 * 1000 / N,
    io:format("Process spawn time = ~p (~p) microseconds~n", [U1, U2]).

wait() ->
    receive
	die ->
	    void
    end.
