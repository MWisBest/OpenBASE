/*
 * This file is part of OpenBASE.
 *
 * Copyright � 2012, Kyle Repinski
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
package mwisbest.openbase.exception;

public class OpenBASEException extends Exception
{
	private static final long serialVersionUID = 6103548155941293316L;
	
	public OpenBASEException()
	{
		super();
	}
	
	public OpenBASEException( String message )
	{
		super( message );
	}
	
	public OpenBASEException( String message, Throwable cause )
	{
		super( message, cause );
	}
	
	public OpenBASEException( Throwable cause )
	{
		super( cause );
	}
}