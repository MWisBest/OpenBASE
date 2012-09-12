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

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

public class HandlerList
{
	private ListenerRegistration[] handlers = null;
	private final EnumMap<EventPriority, List<ListenerRegistration>> handlerSlots;
	private static final ArrayList<HandlerList> allHandlers = new ArrayList<>();
	
	public HandlerList()
	{
		handlerSlots = new EnumMap<>( EventPriority.class );
		for( EventPriority priority : EventPriority.values() ) handlerSlots.put( priority, new ArrayList<ListenerRegistration>() );
		allHandlers.add( this );
	}
	
	public void registerListeners( Collection<ListenerRegistration> listeners )
	{
		for( ListenerRegistration listener : listeners )
		{
			if( handlerSlots.get( listener.getPriority() ).contains( listener ) ) throw new IllegalStateException( "This listener is already registered to priority " + listener.getPriority().toString() );
			handlers = null;
			handlerSlots.get( listener.getPriority() ).add( listener );
		}
	}
	
	public ListenerRegistration[] getRegisteredListeners()
	{
		ListenerRegistration[] handlers = this.handlers;
		if( handlers != null ) return handlers;
		List<ListenerRegistration> entries = new ArrayList<>();
		for( Entry<EventPriority, List<ListenerRegistration>> entry : handlerSlots.entrySet() ) entries.addAll( entry.getValue() );
		this.handlers = handlers = entries.toArray( new ListenerRegistration[entries.size()]);
		return handlers;
	}
}