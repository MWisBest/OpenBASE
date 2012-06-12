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

public class FloatVector3 implements Serializable
{
	private static final long serialVersionUID = 945165227460936084L;
	
	protected final float x, y, z;
	
	public FloatVector3( float x, float y, float z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getZ()
	{
		return z;
	}
	
	public FloatVector3 add( FloatVector3 vector )
	{
		return add( this, vector );
	}
	
	public static FloatVector3 add( FloatVector3 left, FloatVector3 right )
	{
		return new FloatVector3( left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ() );
	}
	
	/**
	 * Subtract a vector from the current vector.
	 * 
	 * @param FloatVector3 to subtract from current FloatVector3.
	 * @return new FloatVector3
	 */
	public FloatVector3 subtract( FloatVector3 vector )
	{
		return subtract( this, vector );
	}
	
	public static FloatVector3 subtract( FloatVector3 left, FloatVector3 right )
	{
		return new FloatVector3( left.getX() - right.getX(), left.getY() - right.getY(), left.getZ() - right.getZ() );
	}
	
	public FloatVector3 multiply( FloatVector3 vector )
	{
		return multiply( this, vector );
	}
	
	public static FloatVector3 multiply( FloatVector3 left, FloatVector3 right )
	{
		return new FloatVector3( left.getX() * right.getX(), left.getY() * right.getY(), left.getZ() * right.getZ() );
	}
	
	/**
	 * Divide the current vector by a new vector.
	 * 
	 * @param FloatVector3 to divide from current FloatVector3.
	 * @return new FloatVector3
	 */
	public FloatVector3 divide( FloatVector3 vector )
	{
		return divide( this, vector );
	}
	
	public static FloatVector3 divide( FloatVector3 left, FloatVector3 right )
	{
		return new FloatVector3( left.getX() / right.getX(), left.getY() / right.getY(), left.getZ() / right.getZ() );
	}
}