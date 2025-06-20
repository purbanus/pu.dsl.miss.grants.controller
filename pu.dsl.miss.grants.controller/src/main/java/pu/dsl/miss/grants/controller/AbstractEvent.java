package pu.dsl.miss.grants.controller;

public class AbstractEvent
{
private String name, code;

@SuppressWarnings( "hiding" )
public AbstractEvent( String name, String code )
{
	this.name = name;
	this.code = code;
}
public String getCode()
{
	return code;
}
public String getName()
{
	return name;
}

}