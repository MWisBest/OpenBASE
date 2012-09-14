/*
 * This file is part of OpenBASE.
 *
 * Copyright © 2012, Kyle Repinski
 * OpenBASE is licensed under the GNU Lesser General Public License.
 *
 * OpenBASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenBASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package mwisbest.openbase.event.input;

import mwisbest.openbase.event.Event;
import mwisbest.openbase.event.HandlerList;
import mwisbest.openbase.input.KeyboardKey;
import mwisbest.openbase.input.KeyboardKeyState;

public class KeyboardEvent extends Event
{
	private static HandlerList handlers = new HandlerList();
	private KeyboardKey key;
	private KeyboardKeyState state;
	private boolean repeatEvent;
	
	public KeyboardEvent( KeyboardKey key, KeyboardKeyState state, boolean repeatEvent )
	{
		this.key = key;
		this.state = state;
		this.repeatEvent = repeatEvent;
	}
	
	public KeyboardKey getKey()
	{
		return this.key;
	}
	
	public KeyboardKeyState getState()
	{
		return this.state;
	}
	
	public boolean getRepeatEvent()
	{
		return this.repeatEvent;
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