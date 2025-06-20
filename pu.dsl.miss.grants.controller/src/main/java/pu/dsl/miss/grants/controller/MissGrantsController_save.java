package pu.dsl.miss.grants.controller;

/*
 * Now that I’ve implemented the state machine model, I can program Miss Grant’s controller like this:
 */

public class MissGrantsController_save
{
public static void main( String [] argv )
{
	new MissGrantsController_save().run();
}
public void run()
{
	Event doorClosed = new Event( "doorClosed", "D1CL" );
	Event drawerOpened = new Event( "drawerOpened", "D2OP" );
	Event lightOn = new Event( "lightOn", "L1ON" );
	Event doorOpened = new Event( "doorOpened", "D1OP" );
	Event panelClosed = new Event( "panelClosed", "PNCL" );

	Command unlockPanelCmd = new Command( "unlockPanel", "PNUL" );
	Command lockPanelCmd = new Command( "lockPanel", "PNLK" );
	Command lockDoorCmd = new Command( "lockDoor", "D1LK" );
	Command unlockDoorCmd = new Command( "unlockDoor", "D1UL" );

	State idle = new State( "idle" );
	State activeState = new State( "active" );
	State waitingForLightState = new State( "waitingForLight" );
	State waitingForDrawerState = new State( "waitingForDrawer" );
	State unlockedPanelState = new State( "unlockedPanel" );

	StateMachine machine = new StateMachine( idle );

	idle.addTransition( doorClosed, activeState );
	idle.addCommand( unlockDoorCmd );
	idle.addCommand( lockPanelCmd );

	activeState.addTransition( drawerOpened, waitingForLightState );
	activeState.addTransition( lightOn, waitingForDrawerState );

	waitingForLightState.addTransition( lightOn, unlockedPanelState );

	waitingForDrawerState.addTransition( drawerOpened, unlockedPanelState );

	unlockedPanelState.addCommand( unlockPanelCmd );
	unlockedPanelState.addCommand( lockDoorCmd );
	unlockedPanelState.addTransition( panelClosed, idle );

	machine.addResetEvents( doorOpened );
	
	CommandChannel commandChannel = new CommandChannel();
	Controller controller = new Controller( commandChannel, idle, machine );
	commandChannel.setController( controller );
	
	controller.reportState();
	commandChannel.send( doorClosed.getCode() );
	System.out.println();
	commandChannel.send( drawerOpened.getCode() );
	System.out.println();
	commandChannel.send( lightOn.getCode() );
}
}
