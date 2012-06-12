/*
 * This file is part of OpenBASE.
 *
 * Copyright © 3013, Kyle Repinski
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

public class DoubleVector3 implements Serializable
{
	private static final long serialVersionUID = 3613176843735434360L;
	
	protected final double x, y, z;
	
	public DoubleVector3( double x, double y, double z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public DoubleVector3 add( DoubleVector3 vector )
	{
		return add( this, vector );
	}
	
	public static DoubleVector3 add( DoubleVector3 left, DoubleVector3 right )
	{
		return new DoubleVector3( left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ() );
	}
	
	/**
	 * Subtract a vector from the current vector.
	 * 
	 * @param DoubleVector3 to subtract from current DoubleVector3.
	 * @return new DoubleVector3
	 */
	public DoubleVector3 subtract( DoubleVector3 vector )
	{
		return subtract( this, vector );
	}
	
	public static DoubleVector3 subtract( DoubleVector3 left, DoubleVector3 right )
	{
		return new DoubleVector3( left.getX() - right.getX(), left.getY() - right.getY(), left.getZ() - right.getZ() );
	}
	
	public DoubleVector3 multiply( DoubleVector3 vector )
	{
		return multiply( this, vector );
	}
	
	public static DoubleVector3 multiply( DoubleVector3 left, DoubleVector3 right )
	{
		return new DoubleVector3( left.getX() * right.getX(), left.getY() * right.getY(), left.getZ() * right.getZ() );
	}
	
	/**
	 * Divide the current vector by a new vector.
	 * 
	 * @param DoubleVector3 to divide from current DoubleVector3.
	 * @return new DoubleVector3
	 */
	public DoubleVector3 divide( DoubleVector3 vector )
	{
		return divide( this, vector );
	}
	
	public static DoubleVector3 divide( DoubleVector3 left, DoubleVector3 right )
	{
		return new DoubleVector3( left.getX() / right.getX(), left.getY() / right.getY(), left.getZ() / right.getZ() );
	}
}