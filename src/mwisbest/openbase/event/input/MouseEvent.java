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
import mwisbest.openbase.input.MouseButton;
import mwisbest.openbase.input.MouseButtonState;

public class MouseEvent extends Event
{
	private static HandlerList handlers = new HandlerList();
	private MouseButton button;
	private MouseButtonState state;
	private int x, y, z;
	
	public MouseEvent( MouseButton button, MouseButtonState state, int x, int y, int z )
	{
		this.button = button;
		this.state = state;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public MouseButton getButton()
	{
		return button;
	}
	
	public MouseButtonState getState()
	{
		return state;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
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