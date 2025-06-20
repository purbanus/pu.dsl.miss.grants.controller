package pu.dsl.miss.grants.controller;

public class Event extends AbstractEvent
{

public Event( String aName, String aCode )
{
	super( aName, aCode );
}
@Override
public String toString()
{
	return "Event " + getName();
}
}