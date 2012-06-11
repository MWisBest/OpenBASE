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
 * An enum for mouse buttons, supporting up to 17 buttons.
 * 
 * 17 was the maximum amount of buttons on the mouses on NewEgg. :)
 */
public enum MouseButton
{
	BUTTON_UNKNOWN( -1 ),
	BUTTON_LEFT( 0 ),
	BUTTON_RIGHT( 1 ),
	BUTTON_MIDDLE( 2 ),
	BUTTON_FOUR( 3 ),
	BUTTON_FIVE( 4 ),
	BUTTON_SIX( 5 ),
	BUTTON_SEVEN( 6 ),
	BUTTON_EIGHT( 7 ),
	BUTTON_NINE( 8 ),
	BUTTON_TEN( 9 ),
	BUTTON_ELEVEN( 10 ),
	BUTTON_TWELVE( 11 ),
	BUTTON_THIRTEEN( 12 ),
	BUTTON_FOURTEEN( 13 ),
	BUTTON_FIFTEEN( 14 ),
	BUTTON_SIXTEEN( 15 ),
	BUTTON_SEVENTEEN( 16 ),
	SCROLL_UP( -3 ),
	SCROLL_DOWN( -2 );
	
	private final int buttonCode;
	private static final Map<Integer, MouseButton> lookupButtonCode = new HashMap<>();
	
	private MouseButton( final int buttonCode )
	{
		this.buttonCode = buttonCode;
	}
	
	public int getButtonCode()
	{
		return buttonCode;
	}
	
	public static MouseButton getButton( int button )
	{
		if( lookupButtonCode.containsKey( button ) ) return lookupButtonCode.get( button );
		return BUTTON_UNKNOWN;
	}
	
	static
	{
		for( MouseButton button : values() ) lookupButtonCode.put( button.buttonCode, button );
	}
}