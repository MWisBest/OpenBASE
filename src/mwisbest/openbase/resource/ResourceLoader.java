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
package mwisbest.openbase.resource;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import mwisbest.openbase.opengl.Texture;
import mwisbest.openbase.opengl.TextureLoader;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class ResourceLoader
{
	/**
	 * Loads a PNG image to a Texture
	 * 
	 * @param pathToTexture
	 * @return Texture
	 */
	public static Texture loadTexture( String pathToTexture )
	{
		return loadTexture( pathToTexture, "PNG" );
	}
	
	/**
	 * Loads a specific image type to a Texture
	 * 
	 * @param pathToTexture
	 * @param textureType
	 * @return Texture
	 */
	public static Texture loadTexture( String pathToTexture, String textureType )
	{
		try
		{
			return TextureLoader.getTexture( textureType, org.newdawn.slick.util.ResourceLoader.getResourceAsStream( pathToTexture ) );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Deprecated
	public static TrueTypeFont loadFont( String pathToFont, float size, boolean antiAlias )
	{
		try( InputStream inputstream = org.newdawn.slick.util.ResourceLoader.getResourceAsStream( pathToFont ) )
		{
			java.awt.Font awtFont = Font.createFont( java.awt.Font.TRUETYPE_FONT, inputstream );
			awtFont = awtFont.deriveFont( size );
			TrueTypeFont font = new TrueTypeFont( awtFont, antiAlias );
			return font;
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static UnicodeFont loadFont( String pathToFont, int size )
	{
		return loadFont( pathToFont, size, false, false, Color.white );
	}
	
	public static UnicodeFont loadFont( String pathToFont, int size, boolean bold, boolean italic )
	{
		return loadFont( pathToFont, size, bold, italic, Color.white );
	}
	
	public static UnicodeFont loadFont( String pathToFont, int size, Color color )
	{
		return loadFont( pathToFont, size, false, false, color );
	}
	
	public static UnicodeFont loadFont( String pathToFont, int size, boolean bold, boolean italic, Color color )
	{
		try
		{
			UnicodeFont font = new UnicodeFont( pathToFont, size, bold, italic );
			font.getEffects().add( new ColorEffect( color ) );
			font.addAsciiGlyphs();
			font.loadGlyphs();
			return font;
		}
		catch( SlickException e )
		{
			e.printStackTrace();
		}
		return null;
	}
}