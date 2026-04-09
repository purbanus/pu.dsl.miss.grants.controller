package pu.dsl.miss.grants.controller;

/*
 * Now that I’ve implemented the state machine model, I can program Miss Grant’s controller like this:
 */

public class Configuration
{
public final Event doorClosed = new Event( "doorClosed", "D1CL" );
public final Event drawerOpened = new Event( "drawerOpened", "D2OP" );
public final Event lightOn = new Event( "lightOn", "L1ON" );
public final Event doorOpened = new Event( "doorOpened", "D1OP" );
public final Event panelClosed = new Event( "panelClosed", "PNCL" );

public final Command unlockPanelCmd = new Command( "unlockPanel", "PNUL" );
public final Command lockPanelCmd = new Command( "lockPanel", "PNLK" );
public final Command lockDoorCmd = new Command( "lockDoor", "D1LK" );
public final Command unlockDoorCmd = new Command( "unlockDoor", "D1UL" );

public final State idle = new State( "idle" );
public final State activeState = new State( "active" );
public final State waitingForLightState = new State( "waitingForLight" );
public final State waitingForDrawerState = new State( "waitingForDrawer" );
public final State unlockedPanelState = new State( "unlockedPanel" );

public final StateMachine machine = new StateMachine( idle );

public final Controller controller = new Controller( idle, machine );

public Configuration()
{
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
}
}
