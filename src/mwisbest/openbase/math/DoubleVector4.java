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

public class DoubleVector4 implements Serializable
{
	private static final long serialVersionUID = 6401623508012303074L;
	
	protected final double x, y, z, w;
	
	public DoubleVector4( double x, double y, double z, double w )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
	public double getZ()
	{
		return this.z;
	}
	
	public double getW()
	{
		return this.w;
	}
	
	public DoubleVector4 add( DoubleVector4 vector )
	{
		return add( this, vector );
	}
	
	public static DoubleVector4 add( DoubleVector4 left, DoubleVector4 right )
	{
		return new DoubleVector4( left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ(), left.getW() + right.getW() );
	}
	
	/**
	 * Subtract a vector from the current vector.
	 * 
	 * @param DoubleVector4 to subtract from current DoubleVector4.
	 * @return new DoubleVector4
	 */
	public DoubleVector4 subtract( DoubleVector4 vector )
	{
		return subtract( this, vector );
	}
	
	public static DoubleVector4 subtract( DoubleVector4 left, DoubleVector4 right )
	{
		return new DoubleVector4( left.getX() - right.getX(), left.getY() - right.getY(), left.getZ() - right.getZ(), left.getW() - right.getW() );
	}
	
	public DoubleVector4 multiply( DoubleVector4 vector )
	{
		return multiply( this, vector );
	}
	
	public static DoubleVector4 multiply( DoubleVector4 left, DoubleVector4 right )
	{
		return new DoubleVector4( left.getX() * right.getX(), left.getY() * right.getY(), left.getZ() * right.getZ(), left.getW() * right.getW() );
	}
	
	/**
	 * Divide the current vector by a new vector.
	 * 
	 * @param DoubleVector4 to divide from current DoubleVector4.
	 * @return new DoubleVector4
	 */
	public DoubleVector4 divide( DoubleVector4 vector )
	{
		return divide( this, vector );
	}
	
	public static DoubleVector4 divide( DoubleVector4 left, DoubleVector4 right )
	{
		return new DoubleVector4( left.getX() / right.getX(), left.getY() / right.getY(), left.getZ() / right.getZ(), left.getW() / right.getW() );
	}
}