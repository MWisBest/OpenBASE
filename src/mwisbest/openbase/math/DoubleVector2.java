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

public class DoubleVector2 implements Serializable
{
	private static final long serialVersionUID = -7722572180853637834L;
	
	protected final double x, y;
	
	public DoubleVector2( double x, double y )
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
	public DoubleVector2 add( DoubleVector2 vector )
	{
		return add( this, vector );
	}
	
	public static DoubleVector2 add( DoubleVector2 left, DoubleVector2 right )
	{
		return new DoubleVector2( left.getX() + right.getX(), left.getY() + right.getY() );
	}
	
	/**
	 * Subtract a vector from the current vector.
	 * 
	 * @param DoubleVector2 to subtract from current DoubleVector2.
	 * @return new DoubleVector2
	 */
	public DoubleVector2 subtract( DoubleVector2 vector )
	{
		return subtract( this, vector );
	}
	
	public static DoubleVector2 subtract( DoubleVector2 left, DoubleVector2 right )
	{
		return new DoubleVector2( left.getX() - right.getX(), left.getY() - right.getY() );
	}
	
	public DoubleVector2 multiply( DoubleVector2 vector )
	{
		return multiply( this, vector );
	}
	
	public static DoubleVector2 multiply( DoubleVector2 left, DoubleVector2 right )
	{
		return new DoubleVector2( left.getX() * right.getX(), left.getY() * right.getY() );
	}
	
	/**
	 * Divide the current vector by a new vector.
	 * 
	 * @param DoubleVector2 to divide from current DoubleVector2.
	 * @return new DoubleVector2
	 */
	public DoubleVector2 divide( DoubleVector2 vector )
	{
		return divide( this, vector );
	}
	
	public static DoubleVector2 divide( DoubleVector2 left, DoubleVector2 right )
	{
		return new DoubleVector2( left.getX() / right.getX(), left.getY() / right.getY() );
	}
}