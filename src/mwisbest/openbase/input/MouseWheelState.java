/*
 * This file is part of OpenBASE.
 *
 * Copyright � 2012, Kyle Repinski
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
package mwisbest.openbase.input;

public enum MouseWheelState
{
	MOVING( true ),
	STABLE( false );
	
	private final boolean state;
	
	private MouseWheelState( final boolean state )
	{
		this.state = state;
	}
	
	public boolean getState()
	{
		return this.state;
	}
	
	public static MouseWheelState getWheelState( boolean state )
	{
		if( state ) return MOVING;
		else if( !state ) return STABLE;
		return null;
	}
}