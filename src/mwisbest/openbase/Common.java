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
package mwisbest.openbase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import mwisbest.openbase.event.Event;
import mwisbest.openbase.event.EventManager;
import mwisbest.openbase.event.gui.ButtonClickEvent;
import mwisbest.openbase.event.gui.CheckboxClickEvent;
import mwisbest.openbase.event.input.KeyboardEvent;
import mwisbest.openbase.event.input.MouseEvent;
import mwisbest.openbase.gui.Button;
import mwisbest.openbase.gui.Checkbox;
import mwisbest.openbase.gui.RenderPriority;
import mwisbest.openbase.gui.Widget;
import mwisbest.openbase.gui.WidgetType;
import mwisbest.openbase.input.KeyboardKey;
import mwisbest.openbase.input.KeyboardKeyState;
import mwisbest.openbase.input.MouseButton;
import mwisbest.openbase.input.MouseButtonState;
import mwisbest.openbase.resource.ResourceManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

/**
 * Common code that OpenBASE and OpenBASEApplet share.
 * 
 * This was put in a separate class to make it easier to manage both Application and Applet code.
 */
public class Common
{
	/**
	 * The currently bound texture's ID. This is updated via the render loop after all rendering
	 * is done (including the customRender method) and by UtilsGL.drawTexture and widget's render methods.
	 * 
	 * It allows skipping of unnecessary texture binds to improve performance. You should use it too
	 * with your customRender methods!
	 */
	public static int currentTextureID = -1;
	
	private Common()
	{
		// Don't want multiple instances of this, so don't allow it!
	}
	
	/**
	 * Common code for rendering the Widgets to the screen. After this, customRender() is used from OpenBASE or OpenBASEApplet (whatever is calling this method).
	 */
	protected static void render( int framerateLimit )
	{
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		GL11.glLoadIdentity();
		Map<String, Widget> theWidgets = ResourceManager.getWidgets();
		for( RenderPriority priority : RenderPriority.values() )
		{
			for( Entry<String, Widget> widget : theWidgets.entrySet() )
			{
				if( widget.getValue().getRenderPriority() == priority && widget.getValue().getVisible() ) widget.getValue().render();
			}
		}
		if( framerateLimit > 0 ) Display.sync( framerateLimit );
	}
	
	/**
	 * Common code for the audio system. After this, customAudio() is used from OpenBASE or OpenBASEApplet (whatever is calling this method).
	 */
	protected static void audio()
	{
		SoundStore.get().poll( 0 );
	}
	
	/**
	 * Common code for input. This notifies Mouse and Keyboard events. After this, customInput() is used from OpenBASE or OpenBASEApplet (whatever is calling this method).
	 */
	protected static void input()
	{
		while( Mouse.next() )
		{
			Event mouseEvent = new MouseEvent( MouseButton.getButton( Mouse.getEventButton() ), MouseButtonState.getButtonState( Mouse.getEventButtonState() ), Mouse.getEventX(), Display.getHeight() - Mouse.getEventY(), Mouse.getEventDWheel() );
			EventManager.callEvent( mouseEvent );
			if( MouseButton.getButton( Mouse.getEventButton() ) == MouseButton.BUTTON_LEFT && MouseButtonState.getButtonState( Mouse.getEventButtonState() ) == MouseButtonState.PRESSED )
			{
				for( Entry<String, Widget> widget : ResourceManager.getWidgets().entrySet() )
				{
					if( widget.getValue().getWidgetType() == WidgetType.BUTTON && widget.getValue().getVisible() )
					{
						Button theWidget = (Button)widget.getValue();
						if( theWidget.isInside( Mouse.getEventX(), Display.getHeight() - Mouse.getEventY() ) )
						{
							Event buttonClickEvent = new ButtonClickEvent( theWidget );
							EventManager.callEvent( buttonClickEvent );
						}
					}
					else if( widget.getValue().getWidgetType() == WidgetType.CHECKBOX && widget.getValue().getVisible() )
					{
						Checkbox theWidget = (Checkbox)widget.getValue();
						if( theWidget.isInside( Mouse.getEventX(), Display.getHeight() - Mouse.getEventY() ) )
						{
							Event checkboxClickEvent = new CheckboxClickEvent( theWidget );
							EventManager.callEvent( checkboxClickEvent );
						}
					}
				}
			}
		}
		while( Keyboard.next() )
		{
			Event keyboardEvent = new KeyboardEvent( KeyboardKey.getKey( Keyboard.getEventKey() ), KeyboardKeyState.getKeyState( Keyboard.getEventKeyState() ), Keyboard.isRepeatEvent() );
			EventManager.callEvent( keyboardEvent );
		}
	}
	
	
	/**
	 * Method for extracting files from a jar.
	 * 
	 * @param loc File's location
	 * @param regex File's name
	 * @param folder Folder to extract file to
	 * @return file successfully found
	 */
	public static boolean extractFile( String loc, String regex, File folder )
	{
		boolean found = false;
		
		try( JarFile jar = new JarFile( new File( loc ) ) )
		{
			for( Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements(); )
			{
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				
				if( name.matches( regex ) )
				{
					try
					{
						InputStream is = jar.getInputStream( entry );
						FileOutputStream fos = new FileOutputStream( folder );
						
						while( is.available() > 0 ) fos.write( is.read() );
						
						fos.close();
						is.close();
						found = true;
					}
					catch( Exception e )
					{
					}
				}
			}
		}
		catch( Exception e )
		{
		}
		
		return found;
	}
	
	/**
	 * Used to extract the LWJGL native files from the jar if running from a jar.
	 * 
	 * @param folder folder to extract to
	 * @param forceUpdate extract even if file exists
	 */
	protected static void extractNatives( File folder, boolean forceUpdate )
	{
		boolean inJar = false;
		CodeSource cs = null;
		
		try
		{
			cs = Common.class.getProtectionDomain().getCodeSource();
			inJar = cs.getLocation().toURI().getPath().endsWith( ".jar" );
		}
		catch( URISyntaxException e )
		{
			e.printStackTrace();
		}
		
		if( inJar )
		{
			String loc = null;
			
			try
			{
				loc = cs.getLocation().toURI().getPath();
			}
			catch( URISyntaxException e )
			{
				e.printStackTrace();
			}
			
			String[] files = { "jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw_64.dll", "jinput-raw.dll", "libjinput-linux.so", "libjinput-linux64.so", "libjinput-osx.jnilib", "liblwjgl.jnilib", "liblwjgl.so", "liblwjgl64.so", "libopenal.so", "libopenal64.so", "lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll" };
			
			for( String f : files )
			{
				File tmp = new File( folder, f );
				if( !tmp.exists() || forceUpdate ) extractFile( loc, f, folder );
			}
			
			System.setProperty( "org.lwjgl.librarypath", folder.getAbsolutePath() );
			System.setProperty( "net.java.games.input.librarypath", folder.getAbsolutePath() );
		}
	}
}