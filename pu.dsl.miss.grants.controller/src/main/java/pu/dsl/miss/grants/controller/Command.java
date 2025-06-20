package pu.dsl.miss.grants.controller;

public class Command extends AbstractEvent
{
public Command( String aName, String aCode )
{
	super( aName, aCode );
}
@Override
public String toString()
{
	return "Command " + getName();
}

}