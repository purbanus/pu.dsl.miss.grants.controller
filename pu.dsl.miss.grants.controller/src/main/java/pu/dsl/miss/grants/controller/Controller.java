package pu.dsl.miss.grants.controller;

/*
 * With the structure out of the way, let’s move on to the behavior. As it turns out, it’s really quite simple. The
 * controller has a handle method that takes the event code it receives from the device.
 */
public class Controller
{
private CommandChannel commandsChannel;
private State currentState;
private StateMachine machine;

public Controller( CommandChannel aCommandsChannel, State aCurrentState, StateMachine aMachine )
{
	super();
	commandsChannel = aCommandsChannel;
	currentState = aCurrentState;
	machine = aMachine;
}

public CommandChannel getCommandChannel()
{
	return commandsChannel;
}

public void handle( String eventCode )
{
	report( "handling " + eventCode );
	if ( currentState.hasTransition( eventCode ) )
	{
		transitionTo( currentState.targetState( eventCode ) );
	}
	else if ( machine.isResetEvent( eventCode ) )
	{
		transitionTo( machine.getStart() );
	}
	else
	{
		// ignore unknown events
		report( "no valid transitions found" );
	}

}
private void transitionTo( State target )
{
	report( "transitioning to " + target.getName() );
	currentState = target;
	report( "executing commands " + currentState.getCommands() );
	currentState.executeCommands( commandsChannel );
	reportState();
}
public static void report( Class aClass, String aMessage )
{
	System.out.println( aClass.getSimpleName() + ": " + aMessage );
}
public void reportState()
{
	report( "current state is " + currentState.getName() );
}
public void report( String report )
{
	report( getClass(), report );
}
}
