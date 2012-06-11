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

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class FloatMatrix
{
	public float[] data;
	public int size;
	
	/**
	 * Creates a new FloatMatrix of the specified size,
	 * initialized to the identify matrix.
	 * 
	 * @param size
	 */
	public FloatMatrix( int size )
	{
		this.size = size;
		data = new float[size * size];
		setIdentity();
	}
	
	public int getSize()
	{
		return size;
	}
	
	public FloatMatrix setIdentity()
	{
		return setIdentity( this );
	}
	
	public static FloatMatrix setIdentity( FloatMatrix matrix )
	{
		for( int x = 0; x < matrix.size; x++ )
		{
			for( int y = 0; y < matrix.size; y++ )
			{
				if( x == y ) matrix.data[index( x, y, matrix.size )] = 1.0F;
				else matrix.data[index( x, y, matrix.size )] = 0.0F;
			}
		}
		
		return matrix;
	}
	
	public FloatMatrix setZero()
	{
		return setZero( this );
	}
	
	public static FloatMatrix setZero( FloatMatrix matrix )
	{
		for( int x = 0; x < matrix.size; x++ )
		{
			for( int y = 0; y < matrix.size; y++ )
			{
				matrix.data[index( x, y, matrix.size )] = 0.0F;
			}
		}
		
		return matrix;
	}
	
	public static FloatMatrix add( FloatMatrix left, FloatMatrix right )
	{
		if( left.size != right.size ) throw new IllegalArgumentException( "Matrix dimensions must be equal!" );
		FloatMatrix dest = new FloatMatrix( left.size );
		
		for( int x = 0; x < dest.size; x++ )
		{
			for( int y = 0; y < dest.size; y++ )
			{
				dest.data[index( x, y, dest.size )] = left.data[index( x, y, dest.size )] + right.data[index( x, y, dest.size )];
			}
		}
		
		return dest;
	}
	
	public static FloatMatrix subtract( FloatMatrix left, FloatMatrix right )
	{
		if( left.size != right.size ) throw new IllegalArgumentException( "Matrix dimensions must be equal!" );
		FloatMatrix dest = new FloatMatrix( left.size );
		
		for( int x = 0; x < dest.size; x++ )
		{
			for( int y = 0; y < dest.size; y++ )
			{
				dest.data[index( x, y, dest.size )] = left.data[index( x, y, dest.size )] - right.data[index( x, y, dest.size )];
			}
		}
		
		return dest;
	}
	
	public static FloatMatrix multiply( FloatMatrix left, FloatMatrix right )
	{
		if( left.size != right.size ) throw new IllegalArgumentException( "Matrix dimensions must be equal!" );
		FloatMatrix dest = new FloatMatrix( left.size );
		
		for( int i = 0; i < dest.size; i++ )
		{
			for( int j = 0; j < dest.size; j++ )
			{
				dest.set( i, j, 0 );
				for( int k = 0; k < dest.size; k++ )
				{
					float r = left.get( i, k ) * right.get( k, j );
					dest.set( i, j, dest.get( i, j ) + r );

				}
			}
		}
		return dest;
	}
	
	public FloatBuffer asFloatBuffer()
	{
		return asFloatBuffer( this );
	}
	
	public static FloatBuffer asFloatBuffer( FloatMatrix matrix )
	{
		FloatBuffer fb = BufferUtils.createFloatBuffer( matrix.size * 4 );
		
		for( int i = 0; i < matrix.size; i++ )
		{
			for( int j = 0; j < matrix.size; j++ )
			{
				fb.put( matrix.data[index( i, j, matrix.size )] );
			}
		}
		
		fb.flip();
		
		return fb;
	}
	
	public static FloatMatrix glOrtho( float left, float right, float bottom, float top, float near, float far )
	{
		FloatMatrix orthoMatrix = new FloatMatrix( 4 );
		
		orthoMatrix.set( 0, 0, 2.0F / ( right - left ) );
		orthoMatrix.set( 1, 1, 2.0F / ( top - bottom ) );
		orthoMatrix.set( 2, 2, -2.0F / ( far - near ) );
		orthoMatrix.set( 3, 3, 1.0F );
		orthoMatrix.set( 3, 0, ( -( right + left ) / ( right - left ) ) );
		orthoMatrix.set( 3, 1, ( -( top + bottom ) / ( top - bottom ) ) );
		orthoMatrix.set( 3, 2, ( -( far + near ) / ( far - near ) ) );
		
		return orthoMatrix;
	}
	
	public static int index( int x, int y, int size )
	{
		return x * size + y;
	}
	
	public float get( int row, int column )
	{
		if( row < 0 || row >= size ) throw new IllegalArgumentException( "Row must be between 0 and " + ( size - 1 ) + "." );
		if( column < 0 || column >= size ) throw new IllegalArgumentException( "Column must be between 0 and " + ( size - 1 ) + "." );
		return data[index( row, column, size )];
	}
	
	public void set( int row, int column, float value )
	{
		if( row < 0 || row >= size ) throw new IllegalArgumentException( "Row must be between 0 and " + ( size - 1 ) + "." );
		if( column < 0 || column >= size ) throw new IllegalArgumentException( "Column must be between 0 and " + ( size - 1 ) + "." );
		data[index( row, column, size )] = value;
	}
}