-module(real_producer_consumer).
-export([start_consumer/0]).

start_consumer() ->
    spawn(fun() -> consumer_loop(queue:new()) end).

consumer_loop(Queue) ->
    receive
	{add, Product} -> consumer_loop(queue:in(Product, Queue));
	{get, From} -> 
	    case queue:out(Queue) of 
		{{value, Product}, NewQueue} ->
		    From ! {ok, Product},
		    consumer_loop(NewQueue);
		{empty, NewQueue} -> From ! {error, empty},
				     consumer_loop(NewQueue)
	    end;
	_Other -> consumer_loop(Queue)
    end.


