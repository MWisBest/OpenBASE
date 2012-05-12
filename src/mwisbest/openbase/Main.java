/*
 * This file is part of OpenBASE.
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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map.Entry;

import mwisbest.openbase.opengl.RenderPriority;
import mwisbest.openbase.opengl.UtilsGL;
import mwisbest.openbase.opengl.Widget;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.TextureImpl;

public class Main
{
	private Thread theThread = null;
	private static Main theMain = null;
	private int windowWidth = 640, windowHeight = 360;
	private String windowTitle = "OpenBASE", windowIcon16Loc = "/GLicon16.png",
			windowIcon32Loc = "/GLicon32.png";
	private boolean windowVSync = false, running = false;
	
	public Main()
	{
	}
	
	public Main( int width, int height )
	{
		this.windowWidth = width;
		this.windowHeight = height;
	}
	
	public Main( int width, int height, boolean vSync )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowVSync = vSync;
	}
	
	public Main( int width, int height, String title )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowTitle = title;
	}
	
	public Main( int width, int height, String title, boolean vSync )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowTitle = title;
		this.windowVSync = vSync;
	}
	
	public Main( int width, int height, String icon16loc, String icon32loc )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowIcon16Loc = icon16loc;
		this.windowIcon32Loc = icon32loc;
	}
	
	public Main( int width, int height, String icon16loc, String icon32loc, boolean vSync )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowIcon16Loc = icon16loc;
		this.windowIcon32Loc = icon32loc;
		this.windowVSync = vSync;
	}
	
	public Main( int width, int height, String title, String icon16loc, String icon32loc )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowTitle = title;
		this.windowIcon16Loc = icon16loc;
		this.windowIcon32Loc = icon32loc;
	}
	
	public Main( int width, int height, String title, String icon16loc, String icon32loc, boolean vSync )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowTitle = title;
		this.windowIcon16Loc = icon16loc;
		this.windowIcon32Loc = icon32loc;
		this.windowVSync = vSync;
	}
	
	public void start()
	{
		theThread = new Thread()
		{
			public void run()
			{
				running = true;
				init();
			}
		};
		theThread.start();
	}
	
	public void init()
	{
		try
		{
			Display.setDisplayMode( new DisplayMode( windowWidth, windowHeight ) );
			Display.setTitle( windowTitle );
			Display.setIcon( new ByteBuffer[] { UtilsGL.loadIcon( getClass().getResource( windowIcon16Loc ) ), UtilsGL.loadIcon( getClass().getResource( windowIcon32Loc ) ), } );
			Display.setFullscreen( false );
			Display.create();
			Display.setVSyncEnabled( windowVSync );
			
			GL11.glEnable( GL11.GL_TEXTURE_2D );
			GL11.glDisable( GL11.GL_DEPTH_TEST );
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glLoadIdentity();
			GL11.glOrtho( 0, windowWidth, windowHeight, 0, 1, -1 );
		}
		catch( LWJGLException e ) // handle LWJGL errors
		{
			e.printStackTrace();
			System.exit( 0 );
		}
		catch( IOException e ) // Handle loadIcon errors
		{
			e.printStackTrace();
		}
		
		loadResources();
		
		mainLoop();
	}
	
	public void loadResources()
	{
		ResourceManager.addWidget( "test", new Widget( UtilsGL.loadTexture( "GLicon256.png" ) ) );
		ResourceManager.addFont( "BNE12", UtilsGL.loadFont( "BraveNewEra.ttf", 12.0F, true ) );
		ResourceManager.addFont( "BNE24", UtilsGL.loadFont( "BraveNewEra.ttf", 24.0F, true ) );
	}
	
	public void mainLoop()
	{
		while( this.running )
		{
			render();
			audio();
		}
		AL.destroy();
	}
	
	public void render()
	{
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		GL11.glLoadIdentity();
		HashMap<String, Widget> theWidgets = ResourceManager.getWidgets();
		RenderPriority[] rvalues = RenderPriority.values();
		for( RenderPriority priority : rvalues )
		{
			for( Entry<String, Widget> widget : theWidgets.entrySet() )
			{
				if( widget.getValue().getRenderPriority() == priority && widget.getValue().getVisible() )
				{
					widget.getValue().render();
				}
			}
		}
		
		TextureImpl.bindNone();
		ResourceManager.getFont( "BNE24" ).drawString( 400, 300, "Hello!" );
		
		Display.update();
		if( Display.isCloseRequested() )
		{
			Display.destroy();
			this.running = false;
		}
	}
	
	public void audio()
	{
		SoundStore.get().poll( 0 );
	}
	
	public static void main( String[] argv )
	{
		theMain = new Main( 640, 360, false );
		theMain.start();
	}
}