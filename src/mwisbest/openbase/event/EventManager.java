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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mwisbest.openbase.exception.AccessException;
import mwisbest.openbase.exception.EventException;
import mwisbest.openbase.util.LoggerHelper;

public class EventManager
{
	public static <EventClass extends Event> EventClass callEvent( EventClass event )
	{
		HandlerList handlers = event.getHandlers();
		ListenerRegistration[] listeners = handlers.getRegisteredListeners();
		
		if( listeners != null )
		{
			for( ListenerRegistration listener : listeners )
			{
				try
				{
					listener.getExecutor().execute( event );
				}
				catch( Throwable t )
				{
					LoggerHelper.getLogger().severe( "Couldn't pass event " + event.getEventName() + " to " + listener.getOwner().getClass().getName() + "." );
				}
			}
			event.setHasBeenCalled( true );
		}
		return event;
	}
	
	public static void registerEvents( Listener listener, Object owner )
	{
		for( Map.Entry<Class<? extends Event>, Set<ListenerRegistration>> entry : createRegisteredListeners( listener, owner ).entrySet() )
		{
			Class<? extends Event> eventClass = getRegistrationClass( entry.getKey() );
			if( !entry.getKey().equals( eventClass ) )
			{
				LoggerHelper.getLogger().severe( "Program attempted to register delegated event class " + entry.getKey() + ". It should be using " + eventClass + "!" );
				continue;
			}
			getEventListeners( eventClass ).registerListeners( entry.getValue() );
		}
	}
	
	private static HandlerList getEventListeners( Class<? extends Event> eventClass )
	{
		try
		{
			Method method = getRegistrationClass( eventClass ).getDeclaredMethod( "getHandlerList" );
			method.setAccessible( true );
			return (HandlerList)method.invoke( null );
		}
		catch( Exception e )
		{
			throw new AccessException( e.toString() );
		}
	}
	
	private static Class<? extends Event> getRegistrationClass( Class<? extends Event> eventClass )
	{
		try
		{
			eventClass.getDeclaredMethod( "getHandlerList" );
			return eventClass;
		}
		catch( NoSuchMethodException e )
		{
			if( eventClass.getSuperclass() != null && !eventClass.getSuperclass().equals( Event.class ) && Event.class.isAssignableFrom( eventClass.getSuperclass() ) ) return getRegistrationClass( eventClass.getSuperclass().asSubclass( Event.class ) );
			else throw new AccessException( "Unable to find handler list for event: " + eventClass.getName() + "." );
		}
	}
	
	private static Map<Class<? extends Event>, Set<ListenerRegistration>> createRegisteredListeners( final Listener listener, Object owner )
	{
		Map<Class<? extends Event>, Set<ListenerRegistration>> ret = new HashMap<Class<? extends Event>, Set<ListenerRegistration>>();
		List<Method> methods = new ArrayList<Method>();
		Class<?> listenerClass = listener.getClass();
		while( listenerClass != null && !listenerClass.equals( Object.class ) && !listenerClass.equals( Listener.class ) )
		{
			try
			{
				methods.addAll( Arrays.asList( listenerClass.getDeclaredMethods() ) );
			}
			catch( NoClassDefFoundError e )
			{
				LoggerHelper.getLogger().severe( "Program is attempting to register non-existing event " + e.getMessage() + ". Ignoring events registered in " + listenerClass );
				return ret;
			}
			listenerClass = listenerClass.getSuperclass();
		}
		for( final Method method : methods )
		{
			final EventHandler eh = method.getAnnotation( EventHandler.class );
			if( eh == null ) continue;
			final Class<?> checkClass = method.getParameterTypes()[0];
			Class<? extends Event> eventClass;
			if( !Event.class.isAssignableFrom( checkClass ) || method.getParameterTypes().length != 1 )
			{
				LoggerHelper.getLogger().severe( "Wrong method arguments used for event type registered." );
				continue;
			}
			else eventClass = checkClass.asSubclass( Event.class );
			method.setAccessible( true );
			Set<ListenerRegistration> eventSet = ret.get( eventClass );
			if( eventSet == null )
			{
				eventSet = new HashSet<ListenerRegistration>();
				ret.put( eventClass, eventSet );
			}
			eventSet.add( new ListenerRegistration(
				new EventExecutor()
				{
					@Override
					public void execute( Event event ) throws EventException
					{
						try
						{
							if( !checkClass.isAssignableFrom( event.getClass() ) ) throw new EventException( "Wrong event type passed to registered method." );
							method.invoke( listener, event );
						}
						catch( InvocationTargetException e )
						{
							if( e.getCause() instanceof EventException ) throw (EventException)e.getCause();
							else throw new EventException( e.getCause() );
						}
						catch( Throwable t )
						{
							throw new EventException( t );
						}
					}
				}, eh.value(), owner ) );
		}
		return ret;
	}
}