package pu.dsl.miss.grants.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITController
{
Configuration configuration = new Configuration(); 
Controller controller = configuration.controller;
StateMachine machine = configuration.machine;
@BeforeEach
public void setup()
{
	//controller = configuration.controller;
}

@Test
public void idleToActive()
{
	controller.getCommandChannel().send( configuration.doorClosed.getCode() );
	assertEquals( configuration.activeState, controller.getCurrentState() );
}
@Test
public void activeToWaitingForLight()
{
	controller = new Controller( configuration.activeState, machine );
	controller.getCommandChannel().send( configuration.drawerOpened.getCode() );
	assertEquals( configuration.waitingForLightState, controller.getCurrentState() );
}
@Test
public void waitingForLightToUnlockPanel()
{
	controller = new Controller( configuration.waitingForLightState, machine );
	controller.getCommandChannel().send( configuration.lightOn.getCode() );
	assertEquals( configuration.unlockedPanelState, controller.getCurrentState() );
}
}
