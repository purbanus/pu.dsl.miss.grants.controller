package pu.dsl.miss.grants.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * The state class keeps track of the commands that it will send and its outbound transitions.
 */

public class State
{
private final String name;
private List<Command> commands = new ArrayList<>();

private Map<String, Transition> transitions = new HashMap<>();

public State( String aName )
{
	super();
	name = aName;
}
public String getName()
{
	return name;
}
public List<Command> getCommands()
{
	return commands;
}
public void addCommand( Command aCommand )
{
	commands.add( aCommand );
}
public Map<String, Transition> getTransitions()
{
	return transitions;
}
public void addTransition( Event aEvent, State aTargetState )
{
	assert null != aTargetState;
	transitions.put( aEvent.getCode(), new Transition( this, aEvent, aTargetState ) );
}
public Collection<State> getAllTargets()
{
	List<State> result = new ArrayList<>();
	for ( Transition t : transitions.values() )
	{
		result.add( t.getTarget() );
	}
	return result;
}
public boolean hasTransition( String eventCode )
{
	return transitions.containsKey( eventCode );
}
public State targetState( String eventCode )
{
	return transitions.get( eventCode )
	    .getTarget();
}
public void executeCommands( CommandChannel commandsChannel )
{
	for ( Command c : commands )
	{
		System.out.println( "State: executing command " + c.getName() );
		commandsChannel.send( c.getCode() );
	}
}
@Override
public String toString()
{
	return "State " + getName() + " commands=" + getCommands() + " transitions=" + getTransitions();
}
}