/*
 * This file is part of OpenBASE.
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
package mwisbest.openbase.opengl;

public enum RenderPriority
{
	/**
	 * Will render before (behind) all other Widgets.
	 */
	HIGHEST( 0 ),
	/**
	 * Will render before (behind) most Widgets.
	 */
	HIGH( 1 ),
	/**
	 * Will render at the same time as most Widgets.
	 * 
	 * This is the default RenderPriority.
	 */
	NORMAL( 2 ),
	/**
	 * Will render after (in front of) most Widgets.
	 */
	LOW( 3 ),
	/**
	 * Will render after (in front of) all other Widgets.
	 */
	LOWEST( 4 ), ;
	
	private final int id;
	
	RenderPriority( int id )
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}
}