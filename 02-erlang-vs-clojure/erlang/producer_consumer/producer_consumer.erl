-module(producer_consumer).
-export([start_consumer/0,produce/2]).

start_consumer() ->
    spawn(fun() -> consumer_loop() end).

consumer_loop() ->
    receive
	{Pid, meat} -> Pid ! {yummy, that, tasted, good},
			  consumer_loop();
	{Pid, _} -> Pid ! {i, do, 'not', want, to, consume, that, discarded},
		    consumer_loop()
    end.


produce(Pid, Product) ->
    Pid ! {self(), Product},
    receive 
	Response -> io:format("consumer said: ~p~n", [Response])
    end.





