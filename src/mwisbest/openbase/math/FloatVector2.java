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
package mwisbest.openbase.math;

import java.io.Serializable;

public class FloatVector2 implements Serializable
{
	private static final long serialVersionUID = -1887017004059610439L;
	
	protected final float x, y;
	
	public FloatVector2( float x, float y )
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public FloatVector2 add( FloatVector2 vector )
	{
		return add( this, vector );
	}
	
	public static FloatVector2 add( FloatVector2 left, FloatVector2 right )
	{
		return new FloatVector2( left.getX() + right.getX(), left.getY() + right.getY() );
	}
	
	/**
	 * Subtract a vector from the current vector.
	 * 
	 * @param FloatVector2 to subtract from current FloatVector2.
	 * @return new FloatVector2
	 */
	public FloatVector2 subtract( FloatVector2 vector )
	{
		return subtract( this, vector );
	}
	
	public static FloatVector2 subtract( FloatVector2 left, FloatVector2 right )
	{
		return new FloatVector2( left.getX() - right.getX(), left.getY() - right.getY() );
	}
	
	public FloatVector2 multiply( FloatVector2 vector )
	{
		return multiply( this, vector );
	}
	
	public static FloatVector2 multiply( FloatVector2 left, FloatVector2 right )
	{
		return new FloatVector2( left.getX() * right.getX(), left.getY() * right.getY() );
	}
	
	/**
	 * Divide the current vector by a new vector.
	 * 
	 * @param FloatVector2 to divide from current FloatVector2.
	 * @return new FloatVector2
	 */
	public FloatVector2 divide( FloatVector2 vector )
	{
		return divide( this, vector );
	}
	
	public static FloatVector2 divide( FloatVector2 left, FloatVector2 right )
	{
		return new FloatVector2( left.getX() / right.getX(), left.getY() / right.getY() );
	}
}