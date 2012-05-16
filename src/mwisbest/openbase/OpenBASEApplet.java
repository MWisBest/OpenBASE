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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public abstract class OpenBASEApplet extends Applet
{
	private int canvasWidth = 640;
	private int canvasHeight = 360;
	@SuppressWarnings( "unused" )
	private String canvasTitle = "OpenBASE";
	@SuppressWarnings( "unused" )
	private String canvasIcon16Loc = "GLicon16.png";
	@SuppressWarnings( "unused" )
	private String canvasIcon32Loc = "GLicon32.png";
	@SuppressWarnings( "unused" )
	private boolean canvasVSync = false;
	private Canvas displayParent = null;
	private Thread theThread = null;
	private boolean running = false;
	
	public OpenBASEApplet()
	{
		this( 640, 360, "OpenBASE", "GLicon16.png", "GLicon32.png", false );
	}
	
	public OpenBASEApplet( int width, int height )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", false );
	}
	
	public OpenBASEApplet( int width, int height, boolean vSync )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", vSync );
	}
	
	public OpenBASEApplet( int width, int height, String title )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", false );
	}
	
	public OpenBASEApplet( int width, int height, String title, boolean vSync )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", vSync );
	}
	
	public OpenBASEApplet( int width, int height, String icon16Loc, String icon32Loc )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, false );
	}
	
	public OpenBASEApplet( int width, int height, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, vSync );
	}
	
	public OpenBASEApplet( int width, int height, String title, String icon16Loc, String icon32Loc )
	{
		this( width, height, title, icon16Loc, icon32Loc, false );
	}
	
	public OpenBASEApplet( int width, int height, String title, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasTitle = title;
		this.canvasIcon16Loc = icon16Loc;
		this.canvasIcon32Loc = icon32Loc;
		this.canvasVSync = vSync;
	}
	
	public void startLWJGL()
	{
		theThread = new Thread()
			{
				@Override
				public void run()
				{
					running = true;
					try
					{
						Display.setParent( displayParent );
						Display.create();
					}
					catch( LWJGLException e )
					{
						e.printStackTrace();
					}
					mainLoop();
				}
			};
		theThread.start();
	}
	
	public void stopLWJGL()
	{
		running = false;
		try
		{
			theThread.join();
		}
		catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start()
	{
	}
	
	@Override
	public void stop()
	{
	}
	
	@Override
	public void destroy()
	{
		remove( displayParent );
		super.destroy();
	}
	
	public void mainLoop()
	{
		while( running )
		{
			render();
			audio();
		}
		
		Display.destroy();
	}
	
	public void audio()
	{
		customAudio();
	}
	
	public void render()
	{
		Display.sync( 60 );
		customRender();
		Display.update();
	}
	
	@Override
	public void init()
	{
		setLayout( new BorderLayout() );
		try
		{
			displayParent = new Canvas()
				{
					@Override
					public final void addNotify()
					{
						super.addNotify();
						startLWJGL();
					}
					
					@Override
					public final void removeNotify()
					{
						stopLWJGL();
						super.removeNotify();
					}
				};
			displayParent.setSize( this.canvasWidth, this.canvasHeight );
			add( displayParent );
			displayParent.setFocusable( true );
			displayParent.requestFocus();
			displayParent.setIgnoreRepaint( true );
			setVisible( true );
		}
		catch( Exception e )
		{
			System.err.println( e );
			throw new RuntimeException( "Unable to create the display!" );
		}
	}
	
	public abstract void loadResources();
	
	public abstract void customRender();
	
	public abstract void customAudio();
}