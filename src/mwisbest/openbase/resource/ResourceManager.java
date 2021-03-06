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
package mwisbest.openbase.resource;

import java.util.HashMap;
import java.util.Map;

import mwisbest.openbase.gui.Widget;
import mwisbest.openbase.opengl.Texture;

import org.newdawn.slick.Font;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

public class ResourceManager
{
	private static final Map<String, Font> fonts = new HashMap<>();
	private static final Map<String, String> parameters = new HashMap<>();
	private static final Map<String, Music> musics = new HashMap<>();
	private static final Map<String, Sound> sounds = new HashMap<>();
	private static final Map<String, Texture> textures = new HashMap<>();
	private static final Map<String, Widget> widgets = new HashMap<>();
	
	public static void addFont( String key, Font font )
	{
		if( hasFont( key ) ) throw new IllegalArgumentException( "Font already exists for key: " + key );
		fonts.put( key, font );
	}
	
	public static void addMusic( String key, Music music )
	{
		if( hasMusic( key ) ) throw new IllegalArgumentException( "Music already exists for key: " + key );
		musics.put( key, music );
	}
	
	public static void addParameter( String key, String value )
	{
		if( hasParameter( key ) ) throw new IllegalArgumentException( "Parameter already exists for key: " + key );
		parameters.put( key, value );
	}
	
	public static void addSound( String key, Sound sound )
	{
		if( hasSound( key ) ) throw new IllegalArgumentException( "Sound already exists for key: " + key );
		sounds.put( key, sound );
	}
	
	public static void addTexture( String key, Texture texture )
	{
		if( hasTexture( key ) ) throw new IllegalArgumentException( "Texture already exists for key: " + key );
		textures.put( key, texture );
	}
	
	public static void addWidget( String key, Widget widget )
	{
		if( hasWidget( key ) ) throw new IllegalArgumentException( "Widget already exists for key: " + key );
		widgets.put( key, widget );
	}
	
	public static Map<String, Font> getFonts()
	{
		return fonts;
	}
	
	public static Map<String, Music> getMusics()
	{
		return musics;
	}
	
	public static Map<String, String> getParameters()
	{
		return parameters;
	}
	
	public static Map<String, Sound> getSounds()
	{
		return sounds;
	}
	
	public static Map<String, Texture> getTextures()
	{
		return textures;
	}
	
	public static Map<String, Widget> getWidgets()
	{
		return widgets;
	}
	
	public static Font getFont( String key )
	{
		Font font = fonts.get( key );
		if( font == null ) throw new IllegalArgumentException( "No Font exists for key: " + key );
		return font;
	}
	
	public static Music getMusic( String key )
	{
		Music music = musics.get( key );
		if( music == null ) throw new IllegalArgumentException( "No Music exists for key: " + key );
		return music;
	}
	
	public static String getParameter( String key )
	{
		String val = parameters.get( key );
		if( val == null ) throw new IllegalArgumentException( "No Parameter exists for key: " + key );
		return val;
	}
	
	public static Sound getSound( String key )
	{
		Sound sound = sounds.get( key );
		if( sound == null ) throw new IllegalArgumentException( "No Sound exists for key: " + key );
		return sound;
	}
	
	public static Texture getTexture( String key )
	{
		Texture texture = textures.get( key );
		if( texture == null ) throw new IllegalArgumentException( "No Texture exists for key: " + key );
		return texture;
	}
	
	public static Widget getWidget( String key )
	{
		Widget widget = widgets.get( key );
		if( widget == null ) throw new IllegalArgumentException( "No Widget exists for key: " + key );
		return widget;
	}
	
	public static double getDouble( String key )
	{
		return Double.parseDouble( getParameter( key ) );
	}
	
	public static int getInt( String key )
	{
		return Integer.parseInt( getParameter( key ) );
	}
	
	public static float getFloat( String key )
	{
		return Float.parseFloat( getParameter( key ) );
	}
	
	public static boolean hasFont( String key )
	{
		return fonts.containsKey( key );
	}
	
	public static boolean hasMusic( String key )
	{
		return musics.containsKey( key );
	}
	
	public static boolean hasParameter( String key )
	{
		return parameters.containsKey( key );
	}
	
	public static boolean hasSound( String key )
	{
		return sounds.containsKey( key );
	}
	
	public static boolean hasTexture( String key )
	{
		return textures.containsKey( key );
	}
	
	public static boolean hasWidget( String key )
	{
		return widgets.containsKey( key );
	}
	
	public static void removeFont( String key )
	{
		if( !hasFont( key ) ) throw new IllegalArgumentException( "Font doesn't exist for key: " + key );
		fonts.remove( key );
	}
	
	public static void removeMusic( String key )
	{
		if( !hasMusic( key ) ) throw new IllegalArgumentException( "Music doesn't exist for key: " + key );
		musics.remove( key );
	}
	
	public static void removeParameter( String key )
	{
		if( !hasParameter( key ) ) throw new IllegalArgumentException( "Parameter doesn't exist for key: " + key );
		parameters.remove( key );
	}
	
	public static void removeSound( String key )
	{
		if( !hasSound( key ) ) throw new IllegalArgumentException( "Sound doesn't exist for key: " + key );
		sounds.remove( key );
	}
	
	public static void removeTexture( String key )
	{
		if( !hasTexture( key ) ) throw new IllegalArgumentException( "Texture doesn't exist for key: " + key );
		textures.remove( key );
	}
	
	public static void removeWidget( String key )
	{
		if( !hasWidget( key ) ) throw new IllegalArgumentException( "Widget doesn't exist for key: " + key );
		widgets.remove( key );
	}
	
	public static void replaceFont( String key, Font font )
	{
		if( !hasFont( key ) ) throw new IllegalArgumentException( "Font doesn't exist for key: " + key );
		fonts.remove( key );
		fonts.put( key, font );
	}
	
	public static void replaceMusic( String key, Music music )
	{
		if( !hasMusic( key ) ) throw new IllegalArgumentException( "Music doesn't exist for key: " + key );
		musics.remove( key );
		musics.put( key, music );
	}
	
	public static void replaceParameter( String key, String value )
	{
		if( !hasParameter( key ) ) throw new IllegalArgumentException( "Parameter doesn't exist for key: " + key );
		parameters.remove( key );
		parameters.put( key, value );
	}
	
	public static void replaceSound( String key, Sound sound )
	{
		if( !hasSound( key ) ) throw new IllegalArgumentException( "Sound doesn't exist for key: " + key );
		sounds.remove( key );
		sounds.put( key, sound );
	}
	
	public static void replaceTexture( String key, Texture texture )
	{
		if( !hasTexture( key ) ) throw new IllegalArgumentException( "Texture doesn't exist for key: " + key );
		textures.remove( key );
		textures.put( key, texture );
	}
	
	public static void replaceWidget( String key, Widget widget )
	{
		if( !hasWidget( key ) ) throw new IllegalArgumentException( "Widget doesn't exist for key: " + key );
		widgets.remove( key );
		widgets.put( key, widget );
	}
}