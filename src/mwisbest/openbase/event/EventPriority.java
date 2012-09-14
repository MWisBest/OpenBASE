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

public enum EventPriority
{
	/**
	 * Event is of lowest priority, and will let all other events modify the event as they see fit.
	 */
	LOWEST( 0 ),
	/**
	 * Event is of low priority and can be freely modified by most other events.
	 */
	LOW( 1 ),
	/**
	 * Event is of normal priority, and is not important or unimportant.
	 */
	NORMAL( 2 ),
	/**
	 * Event is of high priority, but still allowing events with HIGHEST priority to affect the outcome of the event.
	 */
	HIGH( 3 ),
	/**
	 * Event is of highest priority, needing to have the final say in the outcome of the event.
	 */
	HIGHEST( 4 ),
	/**
	 * Event is only used to monitor the outcome of an event.
	 * 
	 * Event modifications should not be made under this priority.
	 */
	MONITOR( 5 );
	
	private final int id;
	
	private EventPriority( int id )
	{
		this.id = id;
	}
	
	public int getID()
	{
		return this.id;
	}
}