package pu.dsl.miss.grants.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateMachine
{
/*
 * The state machine holds on to its start state.
 */

private State start;

@SuppressWarnings( "hiding" )
public StateMachine( State start )
{
	this.start = start;
}
public State getStart()
{
	return start;
}

/*
 * Then, any other states in the machine are those reachable from this state.
 */

public Collection<State> getStates()
{
	List<State> result = new ArrayList<>();
	collectStates( result, start );
	return result;
}

private void collectStates( Collection<State> result, State s )
{
	if ( result.contains( s ) )
	{
		return;
	}
	result.add( s );
	for ( State next : s.getAllTargets() )
	{
		collectStates( result, next );
	}
}

/*
 * To handle reset events, I keep a list of them on the state machine.
 */

private List<Event> resetEvents = new ArrayList<>();

public List<Event> getResetEvents()
{
	return resetEvents;
}
public void addResetEvents( Event... events )
{
	for ( Event e : events )
	{
		resetEvents.add( e );
	}
}

public boolean isResetEvent( String aEventCode )
{
	for ( Event event : resetEvents )
	{
		if ( event.getCode().equals( aEventCode ) )
		{
			return true;
		}
	}
	return false;
}
/*
 * I don’t need to have a separate structure for reset events like this. I could handle this by simply declaring extra transitions on the state machine like this:

 private void addResetEvent_byAddingTransitions(Event e) {
   for (State s : getStates())
     if (!s.hasTransition(e.getCode())) s.addTransition(e, start);
 } 

I prefer explicit reset events on the machine because that better expresses my intent. While it does complicate the machine a bit, it makes it clear 
how a general machine is supposed to work, as well as the intention of defining a particular machine. 
 */
}
