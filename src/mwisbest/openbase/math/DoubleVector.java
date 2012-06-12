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

public class DoubleVector
{
	public double[] data;
	public int size;
	
	public DoubleVector( int size )
	{
		this.size = size;
		data = new double[size];
		setZero();
	}
	
	public int getSize()
	{
		return size;
	}
	
	public DoubleVector setZero()
	{
		return setZero( this );
	}
	
	public static DoubleVector setZero( DoubleVector vector )
	{
		for( int i = 0; i < vector.size; i++ ) vector.data[i] = 0.0D;
		
		return vector;
	}
	
	public DoubleVector setData( int pos, double data )
	{
		return setData( this, pos, data );
	}
	
	public static DoubleVector setData( DoubleVector vector, int pos, double data )
	{
		if( pos >= vector.size || pos < 0 ) throw new IllegalArgumentException( "Position must be in range 0 to " + ( vector.size - 1 ) + "!" );
		vector.data[pos] = data;
		return vector;
	}
	
	public double getData( int pos )
	{
		return getData( this, pos );
	}
	
	public static double getData( DoubleVector vector, int pos )
	{
		if( pos >= vector.size || pos < 0 ) throw new IllegalArgumentException( "Position must be in range 0 to " + ( vector.size - 1 ) + "!" );
		return vector.data[pos];
	}
}