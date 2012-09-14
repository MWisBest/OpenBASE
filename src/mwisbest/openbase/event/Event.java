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
package mwisbest.openbase.event;

public abstract class Event
{
	private boolean beenCalled = false;
	
	public abstract HandlerList getHandlers();
	
	protected String getEventName()
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean getHasBeenCalled()
	{
		return this.beenCalled;
	}
	
	public void setHasBeenCalled( boolean beenCalled )
	{
		this.beenCalled = beenCalled;
	}
	
	@Override
	public String toString()
	{
		return this.getEventName() + " (" + this.getClass().getName() + ")";
	}
}