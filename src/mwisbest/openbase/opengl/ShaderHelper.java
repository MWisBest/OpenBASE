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
package mwisbest.openbase.opengl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import mwisbest.openbase.exception.ShaderCompileException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderHelper
{
	public static int compileShader( String source, int type )
	{
		int shader = GL20.glCreateShader( type );
		GL20.glShaderSource( shader, source );
		GL20.glCompileShader( shader );
		int status = GL20.glGetShader( shader, GL20.GL_COMPILE_STATUS );
		
		if( status != GL11.GL_TRUE )
		{
			String error = GL20.glGetShaderInfoLog( shader, 255 );
			throw new ShaderCompileException( "Compile Error in " + ( ( type == GL20.GL_FRAGMENT_SHADER ) ? "Fragment Shader" : ( type == GL20.GL_VERTEX_SHADER ) ? "Vertex Shader" : "Geometry Shader" ) + ": " + error + "." );
		}
		
		return shader;
	}
	
	public static String readShaderSource( String file ) throws IOException
	{
		try( FileInputStream fis = new FileInputStream( file ); Scanner scanner = new Scanner( fis ); )
		{
			StringBuilder source = new StringBuilder();
		
			while( scanner.hasNextLine() ) source.append( scanner.nextLine() + "\n" );
		
			return source.toString();
		}
	}
}