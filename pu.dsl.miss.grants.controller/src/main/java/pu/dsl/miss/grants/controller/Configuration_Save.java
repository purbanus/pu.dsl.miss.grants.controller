package pu.dsl.miss.grants.controller;

/*
 * Now that I’ve implemented the state machine model, I can program Miss Grant’s controller like this:
 */

public class Configuration_Save
{
public enum Events
{
	DOOR_CLOSED( new Event( "doorClosed", "D1CL" ) ),
	DRAWER_OPENED( new Event( "drawerOpened", "D2OP" ) ),
	LIGHT_ON( new Event( "lightOn", "L1ON" ) ),
	DOOR_OPENED( new Event( "doorOpened", "D1OP" ) ),
	PANEL_CLOSED( new Event( "panelClosed", "PNCL" ) );
	private Event event;
	Events( Event aEvent )
	{
		event = aEvent;
	}
	public Event getEvent()
	{
		return event;
	}

}
public enum Commands
{
	UNLOCK_PANEL_CMD( new Command( "unlockPanel", "PNUL" ) ),
	LOCK_PANEL_CMD( new Command( "lockPanel", "PNLK" ) ),
	LOCK_DOOR_CMD( new Command( "lockDoor", "D1LK" ) ),
	UNLOCK_DOOR_CMD( new Command( "unlockDoor", "D1UL" ) );
	private Command command;
	Commands( Command aCommand )
	{
		command = aCommand;
	}
	public Command getCommand()
	{
		return command;
	}
}
public enum States
{
	IDLE_STATE( new State( "idle" ) ),
	ACTIVE_STATE( new State( "active" ) ),
	WAITING_FOR_LIGHT_STATE( new State( "waitingForLight" ) ),
	WAITING_FOR_DRAWER_STATE( new State( "waitingForDrawer" ) ),
	UNLOCKED_PANEL_STATE( new State( "unlockedPanel" ) );
	private State state;
	States( State aState )
	{
		state = aState;
	}
	public State getState()
	{
		return state;
	}
	
}

public Controller configure()
{
	StateMachine machine = new StateMachine( States.IDLE_STATE.getState() );

	States.IDLE_STATE.getState().addTransition( Events.DOOR_CLOSED, States.ACTIVE_STATE );
	States.IDLE_STATE.getState().addCommand( Commands.UNLOCK_DOOR_CMD );
	States.IDLE_STATE.getState().addCommand( Commands.LOCK_PANEL_CMD );

	States.ACTIVE_STATE.getState().addTransition( Events.DRAWER_OPENED, States.WAITING_FOR_LIGHT_STATE );
	States.ACTIVE_STATE.getState().addTransition( Events.LIGHT_ON, States.WAITING_FOR_DRAWER_STATE );

	States.WAITING_FOR_LIGHT_STATE.getState().addTransition( lightOn, unlockedPanelState );

	waitingForDrawerState.addTransition( drawerOpened, unlockedPanelState );

	unlockedPanelState.addCommand( unlockPanelCmd );
	unlockedPanelState.addCommand( lockDoorCmd );
	unlockedPanelState.addTransition( panelClosed, idle );

	machine.addResetEvents( doorOpened );
	
	CommandChannel commandChannel = new CommandChannel();
	Controller controller = new Controller( commandChannel, idle, machine );
	commandChannel.setController( controller );
	return controller;
}
}
