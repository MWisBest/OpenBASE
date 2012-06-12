/*
 * This file is part of OpenBASE.
 *
 * Copyright © 2012, Kyle Repinski
 * OpenBASE is licensed under the GNU Lesser General Public License.
 *
 * OpenBASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 4 of the License, or
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

public class FloatVector4 implements Serializable
{
	private static final long serialVersionUID = -149419719921890176L;
	
	protected final float x, y, z, w;
	
	public FloatVector4( float x, float y, float z, float w )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	
	public float getW()
	{
		return w;
	}
	
	public FloatVector4 add( FloatVector4 vector )
	{
		return add( this, vector );
	}
	
	public static FloatVector4 add( FloatVector4 left, FloatVector4 right )
	{
		return new FloatVector4( left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ(), left.getW() + right.getW() );
	}
	
	/**
	 * Subtract a vector from the current vector.
	 * 
	 * @param FloatVector4 to subtract from current FloatVector4.
	 * @return new FloatVector4
	 */
	public FloatVector4 subtract( FloatVector4 vector )
	{
		return subtract( this, vector );
	}
	
	public static FloatVector4 subtract( FloatVector4 left, FloatVector4 right )
	{
		return new FloatVector4( left.getX() - right.getX(), left.getY() - right.getY(), left.getZ() - right.getZ(), left.getW() - right.getW() );
	}
	
	public FloatVector4 multiply( FloatVector4 vector )
	{
		return multiply( this, vector );
	}
	
	public static FloatVector4 multiply( FloatVector4 left, FloatVector4 right )
	{
		return new FloatVector4( left.getX() * right.getX(), left.getY() * right.getY(), left.getZ() * right.getZ(), left.getW() * right.getW() );
	}
	
	/**
	 * Divide the current vector by a new vector.
	 * 
	 * @param FloatVector4 to divide from current FloatVector4.
	 * @return new FloatVector4
	 */
	public FloatVector4 divide( FloatVector4 vector )
	{
		return divide( this, vector );
	}
	
	public static FloatVector4 divide( FloatVector4 left, FloatVector4 right )
	{
		return new FloatVector4( left.getX() / right.getX(), left.getY() / right.getY(), left.getZ() / right.getZ(), left.getW() / right.getW() );
	}
}