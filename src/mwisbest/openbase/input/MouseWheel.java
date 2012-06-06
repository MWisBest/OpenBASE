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
package mwisbest.openbase.input;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum for the mouse's scroll wheel, supporting tilt wheel
 * as well, as long as there is a way to support tilt wheel
 * in actual code...
 */
public enum MouseWheel
{
	WHEEL_UNKNOWN( -1 ),
	WHEEL_UP( 0 ),
	WHEEL_DOWN( 1 ),
	WHEEL_LEFT( 2 ),
	WHEEL_RIGHT( 3 );
	
	private final int wheelCode;
	private static final Map<Integer, MouseWheel> lookupWheelCode = new HashMap<Integer, MouseWheel>();
	
	private MouseWheel( final int i )
	{
		wheelCode = i;
	}
	
	public int getWheelCode()
	{
		return wheelCode;
	}
	
	public static MouseWheel getWheel( int wheel )
	{
		if( lookupWheelCode.containsKey( wheel ) ) return lookupWheelCode.get( wheel );
		return WHEEL_UNKNOWN;
	}
	
	static
	{
		for( MouseWheel wheel : values() ) lookupWheelCode.put( wheel.wheelCode, wheel );
	}
}