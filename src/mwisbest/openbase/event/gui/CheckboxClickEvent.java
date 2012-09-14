package mwisbest.openbase.event.gui;

import mwisbest.openbase.event.Event;
import mwisbest.openbase.event.HandlerList;
import mwisbest.openbase.gui.Checkbox;

public class CheckboxClickEvent extends Event
{
	private static HandlerList handlers = new HandlerList();
	
	private Checkbox checkbox;
	
	public CheckboxClickEvent( Checkbox checkbox )
	{
		this.checkbox = checkbox;
	}
	
	public Checkbox getCheckbox()
	{
		return checkbox;
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}