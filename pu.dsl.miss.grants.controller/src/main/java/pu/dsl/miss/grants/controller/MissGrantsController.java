package pu.dsl.miss.grants.controller;

/*
 * Now that I’ve implemented the state machine model, I can program Miss Grant’s controller like this:
 */

public class MissGrantsController
{
public static void main( String [] argv )
{
	new MissGrantsController().run();
}
public void run()
{
	Configuration configuration = new Configuration(); 
	configuration.controller.reportState();
	configuration.commandChannel.send( configuration.doorClosed.getCode() );
	System.out.println();
	configuration.commandChannel.send( configuration.drawerOpened.getCode() );
	System.out.println();
	configuration.commandChannel.send( configuration.lightOn.getCode() );
}
}
