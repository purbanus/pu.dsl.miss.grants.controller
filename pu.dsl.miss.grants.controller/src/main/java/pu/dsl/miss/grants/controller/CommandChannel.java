package pu.dsl.miss.grants.controller;

public class CommandChannel
{
private Controller controller;

public void setController( Controller aController )
{
	controller = aController;
}

public void send( String aCode )
{
	controller.handle( aCode );
}

}
