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

public class FloatVector
{
	public float[] data;
	public int size;
	
	public FloatVector( int size )
	{
		this.size = size;
		data = new float[size];
		setZero();
	}
	
	public int getSize()
	{
		return size;
	}
	
	public FloatVector setZero()
	{
		return setZero( this );
	}
	
	public static FloatVector setZero( FloatVector vector )
	{
		for( int i = 0; i < vector.size; i++ ) vector.data[i] = 0.0F;
		
		return vector;
	}
	
	public FloatVector setData( int pos, float data )
	{
		return setData( this, pos, data );
	}
	
	public static FloatVector setData( FloatVector vector, int pos, float data )
	{
		if( pos >= vector.size || pos < 0 ) throw new IllegalArgumentException( "Position must be in range 0 to " + ( vector.size - 1 ) + "!" );
		vector.data[pos] = data;
		return vector;
	}
	
	public float getData( int pos )
	{
		return getData( this, pos );
	}
	
	public static float getData( FloatVector vector, int pos )
	{
		if( pos >= vector.size || pos < 0 ) throw new IllegalArgumentException( "Position must be in range 0 to " + ( vector.size - 1 ) + "!" );
		return vector.data[pos];
	}
}